package com.codinghub.miniSpring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 视图对象
 */
public interface View {
	/**
	 * 获取内容类型
	 * @return 内容类型
	 */
	default String getContentType() {
		return null;
	}

	/**
	 * 设置内容类型
	 * @param contentType 内容类型
	 */
	void setContentType(String contentType);

	/**
	 * 设置Url
	 * @param url Url
	 */
	void setUrl(String url);

	/**
	 * 获取Url
	 * @return Url
	 */
	String getUrl();

	/**
	 * 渲染视图
	 * @param model 模型
	 * @param request 请求
	 * @param response 响应
	 * @throws Exception 异常
	 */
	void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception;


	/**
	 * 设置请求上下文属性
	 * @param requestContextAttribute 请求上下文属性
	 */
	void setRequestContextAttribute(String requestContextAttribute);

	/**
	 * 获取请求上下文属性
	 * @return 请求上下文属性
	 */
	String getRequestContextAttribute();

}