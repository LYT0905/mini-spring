package com.codinghub.miniSpring.http.converter;

import com.codinghub.miniSpring.utils.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 莱特0905
 * @Description: Http发送的信息转换接口默认实现类
 * @Date: 2024/10/02 18:50:23
 */
public class DefaultHttpMessageConverter implements HttpMessageConverter {
    // 响应浏览器的内容
    String defaultContentType = "text/json;charset=UTF-8";
    // 响应的编码格式
    String defaultCharacterEncoding = "UTF-8";
    // 对象映射
    ObjectMapper objectMapper;

    public DefaultHttpMessageConverter() {
    }

    /**
     * 修改响应数据的格式
     * @param obj 响应的数据
     * @param response 响应
     * @throws IOException IO异常
     */
    @Override
    public void write(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType(defaultContentType);
        response.setCharacterEncoding(defaultCharacterEncoding);

        writeInternal(obj, response);

        response.flushBuffer();
    }

    /**
     * 内部的写入方法
     * @param obj 对象
     * @param response 响应
     * @throws IOException IO异常
     */
    private void writeInternal(Object obj, HttpServletResponse response) throws IOException{
        String sJsonStr = this.objectMapper.writeValuesAsString(obj);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(sJsonStr);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
