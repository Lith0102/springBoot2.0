package com.lth.system.controller;

import com.core.Authorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: @Litonghui
 * Date: 2018/12/26 15:24
 * Content:
 */
@Controller
@RequestMapping(value = "/admin/test")
public class TestController {

    @RequestMapping(value="/testFace")
    @Authorize(setting="测试-测试页面")
    public ModelAndView testFace() {
    	return new ModelAndView("admin/test/test");
    }

}
