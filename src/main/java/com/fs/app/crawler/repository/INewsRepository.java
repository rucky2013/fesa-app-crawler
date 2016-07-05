package com.fs.app.crawler.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.fs.app.crawler.pojo.NewsPojo;

public interface INewsRepository {
	
	public abstract boolean saveNews(NewsPojo paramNewsPojo);

	public abstract List<NewsPojo> getNewsForPage(Long paramLong,
			String paramString);

	public abstract NewsPojo getNewsById(int paramInt);
}
