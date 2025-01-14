package com.heart.ticket.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.heart.ticket.base.enums.RespCode;
import com.heart.ticket.base.exceptions.SysException;
import com.heart.ticket.base.model.SysResponse;
import com.heart.ticket.base.model.hexiao.SnInfo;
import com.heart.ticket.listener.HexiaoSnImportListener;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author wfli
 * @since 2024/6/28 14:11
 */
@RestController
@RequestMapping("/importSn")
@Slf4j
public class HexiaoSnImportController {

    /**
     * 使用方法：
     * 启动应用，postman请求接口，参数传递excel文件路径
     * 执行完毕得到控制台打印内容
     * 到测试环境服务器vim xx.sh
     * 把打印内容粘贴，保存，执行脚本即可
     */
    @PostMapping("/filePath")
    public SysResponse build(String filePath) {

        HexiaoSnImportListener listener = new HexiaoSnImportListener();
        EasyExcel.read(filePath, SnInfo.class, listener).sheet().doRead();
        List<String> curls = listener.getCurls();

        List<Map<String, String>> results = ssh(curls);

        return SysResponse.success(results);
    }

    private List<Map<String, String>> ssh(List<String> curls) throws SysException {

        String host = "";
        int port = 22;
        String user = "";
        String password = "!!";

        Session session = null;
        List<Map<String, String>> results = new ArrayList<>();
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);

            // 配置
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            prop.put("PreferredAuthentications", "password");

            session.setConfig(prop);
            session.connect(15000);
            System.out.println("ssh connecting " + host + ":" + port + " by " + user);

            testConnection(session);

            System.out.println("开始执行命令");
            for (int i = 0; i < curls.size(); i++) {
                String command = curls.get(i);
                System.out.println(" > " + command);
                // 执行命令
                String echo = executeCommand(session, command);
                System.out.println(" > " + echo);

                Map<String, String> map = new HashMap<>();
                map.put("index", String.valueOf(i + 1));
                map.put("command", command);
                map.put("echo", echo);
                results.add(map);
            }
            System.out.println("命令执行完毕");
        } catch (Exception e) {
            if (e instanceof SysException) {
                throw (SysException) e;
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
                System.out.println("ssh connection stop.");
            }
        }
        return results;
    }

    private String executeCommand(Session session, String command) throws JSchException {
        InputStream in = null;
        BufferedReader reader = null;
        ChannelExec channelExec = null;
        try {
            channelExec = (ChannelExec) session.openChannel("exec");
            in = channelExec.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));

            channelExec.setCommand(command);
            channelExec.setErrStream(System.err);
            channelExec.connect();

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (channelExec != null) {
                    channelExec.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void testConnection(Session session) throws SysException {
        String test;
        try {
            test = executeCommand(session, "curl -X GET 'https:///hexiao/ums.meituan/heartbeat'");
        } catch (Exception e) {
            log.error("ssh execute fail");
            throw new SysException(RespCode.BIZ_SSH_EXCEPTION.getCode(), RespCode.BIZ_SSH_EXCEPTION.getMsg());
        }
        if (test == null) {
            log.error("ssh result null");
            throw new SysException(RespCode.BIZ_SSH_EXCEPTION.getCode(), RespCode.BIZ_SSH_EXCEPTION.getMsg());
        }
        JSONObject parseObj;
        try {
            parseObj = JSONUtil.parseObj(test);
        } catch (Exception e) {
            log.error("ssh resp to json fail");
            throw new SysException(RespCode.BIZ_SSH_EXCEPTION.getCode(), RespCode.BIZ_SSH_EXCEPTION.getMsg());
        }
        if (!"OK".equals(parseObj.get("data"))) {
            log.error("ssh resp msg error");
            throw new SysException(RespCode.BIZ_SSH_EXCEPTION.getCode(), RespCode.BIZ_SSH_EXCEPTION.getMsg());
        }
        System.out.println("ssh connection test SUCCESS!");
    }
}
