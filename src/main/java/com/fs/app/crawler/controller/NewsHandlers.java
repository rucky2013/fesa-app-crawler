package com.fs.app.crawler.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fs.app.crawler.api.controller.BaseHandlers;
import com.fs.commons.crawler.service.INewsService;

@Controller
@RequestMapping("/news")
public class NewsHandlers extends BaseHandlers{

	@Autowired
	private INewsService newsService;
	
	@ResponseBody
	@RequestMapping(value = "/pushNewsBywilddog", method = RequestMethod.POST)
	public void pushNewsBywilddog(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("======pushNewsBywilddog========");
		Map<String, String[]> params = request.getParameterMap();
		newsService.pushNewsBywilddog(params);
		WriteJson(response,"成功");
	}

	@ResponseBody
	@RequestMapping(value = "/pushNewsByNative", method = RequestMethod.POST)
	public void pushNewsByNative(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("======pushNewsByNative========");
		Map<String, String[]> params = request.getParameterMap();
		newsService.pushNewsByNative(params);
		WriteJson(response,"成功");
	}
}
