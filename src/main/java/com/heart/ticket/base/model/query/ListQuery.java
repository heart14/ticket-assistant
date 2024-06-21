package com.heart.ticket.base.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wfli
 * @since 2024/3/14 09:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListQuery {

    private List<String> codes;

    private String idempotent;
}
