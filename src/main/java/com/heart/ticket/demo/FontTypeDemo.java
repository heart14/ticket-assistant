package com.heart.ticket.demo;

import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wfli
 * @since 2024/1/29 15:52
 */
public class FontTypeDemo {

    /**
     * 普通发送执行流程
     * 1.前置参数校验
     * 2.组装参数
     * 3.后置参数校验
     * 4.发送消息至MQ
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate(){
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList("preParamCheckAction","assembleAction",
                "afterParamCheckAction","sendMqAction"));
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     * 目前只有 普通发送的流程
     * 后续扩展则加BusinessCode和ProcessTemplate
     * @return
     */
    @Bean
    public ProcessController processController(ProcessTemplate commonSendTemplate){
        ProcessController processController = new ProcessController();
        Map<String,ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(),commonSendTemplate);
        processController.setTemplateConfig(templateConfig);
        return processController;
    }


    class ProcessTemplate{
        private List<String> processList;

        public List<String> getProcessList() {
            return processList;
        }

        public void setProcessList(List<String> processList) {
            this.processList = processList;
        }
    }
    class ProcessController{
        private Map<String,ProcessTemplate> templateConfig;

        public Map<String, ProcessTemplate> getTemplateConfig() {
            return templateConfig;
        }

        public void setTemplateConfig(Map<String, ProcessTemplate> templateConfig) {
            this.templateConfig = templateConfig;
        }
    }
    enum BusinessCode{
        COMMON_SEND("1");

        BusinessCode(String code) {
            this.code = code;
        }

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
