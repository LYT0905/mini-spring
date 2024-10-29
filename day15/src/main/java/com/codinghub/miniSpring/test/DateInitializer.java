package com.codinghub.miniSpring.test;

import com.codinghub.miniSpring.web.bind.WebDataBinder;
import com.codinghub.miniSpring.web.bind.support.WebBindingInitializer;

import java.util.Date;

public class DateInitializer implements WebBindingInitializer {
	@Override
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class,"yyyy-MM-dd", false));
	}
}
