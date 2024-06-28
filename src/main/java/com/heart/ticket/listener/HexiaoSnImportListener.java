package com.heart.ticket.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.heart.ticket.base.model.hexiao.SnInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wfli
 * @since 2024/6/28 14:20
 */
@Slf4j
public class HexiaoSnImportListener extends AnalysisEventListener<SnInfo> {

    @Override
    public void invoke(SnInfo snInfo, AnalysisContext analysisContext) {
        System.out.println("curl -X POST 'https://ecard.chinaums.com/hexiao/ums.login/Login/createThirdSn' -H 'Content-Type: application/json' -d '{" +
                "\"sn\":\"" + snInfo.getSn().trim() + "\"," +
                "\"instNo\":\"" + snInfo.getInstNo().trim() + "\"," +
                "\"projectName\":\"" + snInfo.getProjectName().trim() + "\"," +
                "\"merchantName\":\"" + snInfo.getMerchantName().trim() + "\"," +
                "\"merchantNo\":\"" + snInfo.getMerchantNo().trim().replace("'", "") + "\"}'");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("curl -X GET 'https://ecard.chinaums.com/hexiao/ums.meituan/heartbeat'");
        log.info("解析完成!");
    }
}
