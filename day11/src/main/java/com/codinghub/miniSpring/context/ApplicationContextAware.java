package com.codinghub.miniSpring.context;

import com.codinghub.miniSpring.beans.BeansException;

/**
 * 应用程序上下文感知
 */
public interface ApplicationContextAware {
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}