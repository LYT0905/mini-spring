package com.codinghub.miniSpring.test;

import com.codinghub.miniSpring.web.WebBindingInitializer;
import com.codinghub.miniSpring.web.WebDataBinder;



import java.util.Date;

public class DateInitializer implements WebBindingInitializer {
	@Override
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class,"yyyy-MM-dd", false));
	}
}
