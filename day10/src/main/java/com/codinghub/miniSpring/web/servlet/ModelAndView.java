package com.codinghub.miniSpring.web.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * 模型和视图
 */
public class ModelAndView {
	/**
	 * 视图
	 */
	private Object view;

	/**
	 * 模型
	 */
	private Map<String, Object> model = new HashMap<>();

	public ModelAndView() {
	}

	public ModelAndView(String viewName) {
		this.view = viewName;
	}

	public ModelAndView(View view) {
		this.view = view;
	}

	public ModelAndView(String viewName, Map<String, ?> modelData) {
		this.view = viewName;
		if (modelData != null) {
			addAllAttributes(modelData);
		}
	}

	public ModelAndView(View view, Map<String, ?> model) {
		this.view = view;
		if (model != null) {
			addAllAttributes(model);
		}
	}

	public ModelAndView(String viewName, String modelName, Object modelObject) {
		this.view = viewName;
		addObject(modelName, modelObject);
	}

	public ModelAndView(View view, String modelName, Object modelObject) {
		this.view = view;
		addObject(modelName, modelObject);
	}



	/**
	 * 设置视图名字
	 * @param viewName 视图名字
	 */
	public void setViewName(String viewName) {
		this.view = viewName;
	}

	/**
	 * 获取视图名字
	 * @return 视图名字
	 */
	public String getViewName() {
		return (this.view instanceof String ? (String) this.view : null);
	}

	/**
	 * 设置视图
	 * @param view 视图
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * 获取视图
	 * @return 视图
	 */
	public View getView() {
		return (this.view instanceof View ? (View) this.view : null);
	}

	/**
	 * 是否存在视图
	 * @return 存在返回true，否则返回false
	 */
	public boolean hasView() {
		return (this.view != null);
	}

	/**
	 * 是否是引用类型
	 * @return 是返回true，否则返回false
	 */
	public boolean isReference() {
		return (this.view instanceof String);
	}

	/**
	 * 获取模型
	 * @return 模型
	 */
	public Map<String, Object> getModel() {
		return this.model;
	}

	/**
	 * 添加所有属性
	 * @param modelData 模型数据
	 */
	private void addAllAttributes(Map<String, ?> modelData) {
		if (modelData != null) {
			model.putAll(modelData);
		}
	}

	/**
	 * 添加属性
	 * @param attributeName 属性名
	 * @param attributeValue 属性值
	 */
	public void addAttribute(String attributeName, Object attributeValue) {
		model.put(attributeName, attributeValue);
	}

	/**
	 * 添加对象
	 * @param attributeName 属性名
	 * @param attributeValue 属性值
	 * @return 模型和视图对象
	 */
	public ModelAndView addObject(String attributeName, Object attributeValue) {
		addAttribute(attributeName, attributeValue);
		return this;
	}

}
