package com.fs.app.crawler.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fs.app.crawler.pojo.NewsPojo;
import com.fs.app.crawler.repository.INewsRepository;

@Repository
@Transactional
public class NewsRepository implements INewsRepository{
	@Autowired
	private SessionFactory sessionFactory;

	public boolean saveNews(NewsPojo pojo) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.save(pojo);
			session.flush();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return false;
	}

	public List<NewsPojo> getNewsForPage(Long timestamp, String type) {
		String sql = "";
		if (timestamp.longValue() == 0L) {
			sql = "from NewsPojo order by timestamp desc";
		} else if (type.equals("loadmore")) {
			sql = "from NewsPojo where timestamp<" + timestamp
					+ " order by timestamp desc";
		} else if (type.equals("loadnew")) {
			sql = "from NewsPojo where timestamp>" + timestamp
					+ " order by timestamp desc";
		}
		if (sql == "") {
			return new ArrayList();
		}
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql).setMaxResults(10);
		return query.list();
	}

	public NewsPojo getNewsById(int id) {
		try {
			String sql = "from NewsPojo where id=" + id;
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery(sql);
			List<NewsPojo> result = query.list();
			if (result.size() > 0) {
				return (NewsPojo) result.get(0);
			}
			return null;
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return null;
	}
}
