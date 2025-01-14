package com.heart.ticket.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.heart.ticket.base.model.hexiao.SnInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wfli
 * @since 2024/6/28 14:20
 */
@Slf4j
public class HexiaoSnImportListener extends AnalysisEventListener<SnInfo> {

    private List<String> curls = new ArrayList<>();

    @Override
    public void invoke(SnInfo snInfo, AnalysisContext analysisContext) {
        String curl = "curl -X POST 'https:///hexiao/ums.login/Login/createThirdSn' -H 'Content-Type: application/json' -d '{" +
                "\"sn\":\"" + snInfo.getSn().trim() + "\"," +
                "\"instNo\":\"" + snInfo.getInstNo().trim() + "\"," +
                "\"projectName\":\"" + snInfo.getProjectName().trim() + "\"," +
                "\"merchantName\":\"" + snInfo.getMerchantName().trim() + "\"," +
                "\"merchantNo\":\"" + snInfo.getMerchantNo().trim().replace("'", "") + "\"}'";
        curls.add(curl);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析完成!:{}", curls);
    }

    public List<String> getCurls() {
        return curls;
    }
}
