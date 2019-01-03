package com.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: @Litonghui
 * Date: 2018/12/26 3:43
 * Content: 错误页面控制器
 */
@Controller
@RequestMapping(value="/error")
public class GlobalErrorController  {
	@RequestMapping(value="notallow")
	public ModelAndView notAllow(){
		return new ModelAndView("/error/notallow");
	}
	
	@RequestMapping(value="/{code}")
	public ModelAndView index(@PathVariable String code){
		return new ModelAndView("/error/exception");
	}
}
