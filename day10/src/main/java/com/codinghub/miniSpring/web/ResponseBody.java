package com.codinghub.miniSpring.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 莱特0905
 * @Description: 请求响应（作为JSON响应）
 * @Date: 2024/10/02 19:13:23
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBody {
}
