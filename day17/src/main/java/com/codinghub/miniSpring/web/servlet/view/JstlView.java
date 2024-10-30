package com.codinghub.miniSpring.web.servlet.view;

import com.codinghub.miniSpring.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 莱特0905
 * @Description: 用于处理视图响应
 * @Date: 2024/10/02 19:25:33
 */
public class JstlView implements View {
    // 默认内容类型
    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=ISO-8859-1";
    // 内容类型
    private String contentType = DEFAULT_CONTENT_TYPE;
    // 请求上下文属性
    private String requestContextAttribute;
    // bean名字
    private String beanName;
    // Url
    private String url;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (Map.Entry<String, ?> entry : model.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        request.getRequestDispatcher(getUrl()).forward(request, response);
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void setRequestContextAttribute(String requestContextAttribute) {
        this.requestContextAttribute = requestContextAttribute;
    }

    @Override
    public String getRequestContextAttribute() {
        return this.requestContextAttribute;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }
}
