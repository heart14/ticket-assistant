package com.heart.ticket.controller;

import com.heart.ticket.model.SysResponse;
import com.heart.ticket.model.TicketAssistantQueryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * About:
 * Other:
 * Created: wfli on 2023/5/16 15:19.
 * Editored:
 */
@RestController
@RequestMapping("/ticket")
public class TicketAssistantController {

    public static final Logger log = LoggerFactory.getLogger(TicketAssistantController.class);

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public SysResponse setAssistant(@RequestBody TicketAssistantQueryRequest queryRequest) {
        log.info("setAssistant: {}",queryRequest);
        return SysResponse.success(queryRequest);
    }
}
