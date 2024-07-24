package com.heart.ticket.base.model.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/7/24 16:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddParam {

    private String key;
    private String value;
}
