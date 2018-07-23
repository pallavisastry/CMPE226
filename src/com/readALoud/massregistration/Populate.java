package com.readALoud.massregistration;

import com.readALoud.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.readALoud.entities.Author;
import com.readALoud.entities.User;
import com.readALoud.entities.Reader;

public class Populate {

	public static void main(String[] args) {
		Populate p = new Populate();
		p.bulkGenerate(50);
	}

	public void bulkGenerate(int count) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			GenerateUsers gen = new GenerateUsers();
			Transaction tx = session.beginTransaction();
			int userCount = count/3;
			for (int n = 0; n < userCount; n++) {
				User p = gen.createUser();
				session.save(p);
				if (n % 100 == 0) {
					session.flush();
					session.clear();
				}
			}
			for (int n = 0; n < userCount; n++) {
				Reader p = gen.createReader();
				session.save(p);
				if (n % 100 == 0) {
					session.flush();
					session.clear();
				}
			}
			for (int n = 0; n < userCount; n++) {
				Author p = gen.createAuthor();
				session.save(p);
				if (n % 100 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} finally {
		}
	}
}
