package com.fqh.commonutils;

import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/13 12:39:32
 * jwtUtils
 */
public class JwtUtils {

    private static final long EXPIRE_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 24;

    //创建token
    public static String createToken(Long uid, String nikename) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("uid", String.valueOf(uid));
                put("nikename", nikename);
                put("expire_time", EXPIRE_TIME);
            }
        };
        String token = JWTUtil.createToken(map, "1234".getBytes());
        return token;
    }
    //解析token
    public static Map<String, Object> parseToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        if (token == null) {
            return map;
        }
        final JWT jwt = JWTUtil.parseToken(token);

        map.put("header", jwt.getHeader(JWTHeader.TYPE));
        map.put("uid", jwt.getPayload("uid"));
        map.put("nikename", jwt.getPayload("nikename"));
        return map;
    }
    //验证token
    public static boolean verifyToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        boolean isValid = JWTUtil.verify(token, "1234".getBytes());
        return isValid;
    }
}
