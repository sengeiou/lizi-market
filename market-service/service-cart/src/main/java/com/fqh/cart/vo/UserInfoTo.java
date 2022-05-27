package com.fqh.cart.vo;

import lombok.Data;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/20 13:11:03
 */
@Data
public class UserInfoTo {

    private Long userId;
    private String userKey; //临时key
    private boolean tempUser = false;
}
