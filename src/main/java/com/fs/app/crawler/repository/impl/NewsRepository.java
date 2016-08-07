package com.fs.app.crawler.repository.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fs.commons.app.pojo.NewsPojo;
import com.fs.commons.crawler.repository.INewsRepository;

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

	public List<NewsPojo> getNewsForPage(int pid,Long timstamp, String type) {
		String sql = "";
		if (pid>0&&timstamp>0) {
			if(type.equals("loadmore")){
				sql = "from NewsPojo where timestamp<="+timstamp+" and id !="+pid+" order by timestamp desc";
			}else if(type.equals("loadnew")){
				sql = "from NewsPojo where timestamp>="+timstamp+" and id !="+pid+" order by timestamp desc";
			}else{
				sql = "from NewsPojo order by timestamp desc";
			}
		}else{
			sql="from NewsPojo order by timestamp desc";
		}
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		query.setFirstResult(0);
		query.setMaxResults(10);
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
