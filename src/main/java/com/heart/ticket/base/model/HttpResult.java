package com.heart.ticket.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * About:
 * Other:
 * Created: wfli on 2023/8/15 11:02.
 * Editored:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult {

    private int httpStatus;

    private String httpResult;
}
