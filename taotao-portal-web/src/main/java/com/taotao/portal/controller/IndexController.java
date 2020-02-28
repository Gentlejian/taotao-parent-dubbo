package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangjianfeng
 * @date 2020/2/28
 * 首页展示Controller
 * @version 1.0
 */
@Controller
public class IndexController {

	/**  展示首页  */
	@RequestMapping("/index")
	public String showIndex() {
		return "index";
	}
}
