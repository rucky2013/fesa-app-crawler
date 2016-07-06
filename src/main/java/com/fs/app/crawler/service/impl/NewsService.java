package com.fs.app.crawler.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.fs.app.crawler.pojo.NewsPojo;
import com.fs.app.crawler.repository.INewsRepository;
import com.fs.app.crawler.service.INewsService;
import com.wilddog.client.Wilddog;

@Service
public class NewsService implements INewsService {
	@Autowired
	private INewsRepository newsRepository;

	@Override
	public String pushNewsBywilddog(Map<String, String[]> pdata) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String[] obj = pdata.get("data");
			String[] obj_url = pdata.get("url");
			System.out.println("开始判断参数");
			if (obj != null && obj.length > 0) {
				NewsPojo pojo = JSONObject.parseObject(obj[0], NewsPojo.class);
				String stime = pojo.getTime().replace("来源:", "").trim();
				pojo.setTime(stime);
				pojo.setTimestamp(sdf.parse(stime).getTime());
				pojo.setUrl(obj_url[0]);
				System.out.println("开始调用野狗API");
				Wilddog ref = new Wilddog("https://201605111151fei.wilddogio.com");
				ref.child("news").push().setValue(pojo);
				Thread.sleep(100);
				System.out.println("调用野狗API结束");
			} else
				return "";
		} catch (Exception ex) {
			System.out.println("错误：" + ex.getMessage());
		}
		return "success";
	}

	@Override
	public String pushNewsByNative(Map<String, String[]> pdata) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String[] obj = pdata.get("data");
			String[] obj_url = pdata.get("url");
			if (obj != null && obj.length > 0) {
				NewsPojo pojo = JSONObject.parseObject(obj[0], NewsPojo.class);
				String stime = pojo.getTime().replace("来源:", "").trim();
				pojo.setTime(stime);
				pojo.setTimestamp(sdf.parse(stime).getTime());
				pojo.setUrl(obj_url[0]);
				System.out.println("实体信息:"+pojo.toString());
				newsRepository.saveNews(pojo);
			} else
				return "";
		} catch (Exception ex) {
			System.out.println("错误：" + ex.getMessage());
		}
		return "success";
	}

	@Override
	public List<NewsPojo> getNewsForPage(Long timestamp, String type) {
		List<NewsPojo> list_news = newsRepository.getNewsForPage(timestamp, type);
		return list_news;
	}

	@Override
	public NewsPojo getNewsById(int id) {
		NewsPojo pojo = newsRepository.getNewsById(id);
		return pojo;
	}
}
