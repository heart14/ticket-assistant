package com.heart.ticket.base.model.hexiao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/6/28 14:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnInfo {

    private String sn;

    private String instNo;

    private String projectName;

    private String merchantName;

    private String merchantNo;
}
