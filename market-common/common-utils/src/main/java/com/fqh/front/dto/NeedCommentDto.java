package com.fqh.front.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/23 18:01:54
 */
@Data
public class NeedCommentDto {

    private Long goodsId;
    private List<Long> goodsIdList;
}
