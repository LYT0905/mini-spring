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
	 * 渲染视图
	 * @param model 模型
	 * @param request 请求
	 * @param response 响应
	 * @throws Exception 异常
	 */
	void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}