package com.codinghub.miniSpring.http.converter;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 莱特0905
 * @Description: Http发送的信息转换接口
 * @Date: 2024/10/02 18:49:00
 */
public interface HttpMessageConverter {
    /**
     * 修改响应数据的格式
     * @param obj 响应的数据
     * @param response 响应
     * @throws IOException IO异常
     */
    void write(Object obj, HttpServletResponse response) throws IOException;
}
