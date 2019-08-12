package ir.maktabsharif.model.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import ir.maktabsharif.model.entity.Role;
import ir.maktabsharif.model.entity.User;

public class RoleDAO {
	public static RoleDAO roleDAOInstance = new RoleDAO();

	private RoleDAO() {

	}

	public static RoleDAO getInstance() {
		return roleDAOInstance;
	}

	private static final SessionFactory mySessionFactory;

	static {
		try {
			mySessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static Session getSession() throws HibernateException {
		return mySessionFactory.openSession();
	}

	public boolean add(Role e) {
		e.setName(e.getName().toLowerCase());
		Session session = getSession();
		try {
			session.beginTransaction();
			session.save(e);
			session.getTransaction().commit();
		} catch (HibernateException x) {
			session.close();
			return false;
		}
		session.close();
		return true;
	}

	public boolean delete(Role e) {
		if (e.equals(Role.mainAdminRole()) || e.equals(Role.guestRole()) || e.equals(Role.simpleAdminRole())
				|| e.equals(Role.simpleUserRole())) {
			return false;
		}
		Session session = getSession();
		try {
			session.beginTransaction();
			session.remove(e);
			session.getTransaction().commit();
		} catch (HibernateException x) {
			session.close();
			return false;
		}
		session.close();
		return true;
	}

	public boolean update(Role e) {
		return false;
	}

	public List<Role> getAll() {
		initializer();
		List<Role> roles = null;
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("from Role");
			roles = q.list();
			tx.commit();
		} catch (HibernateException e) {
			session.close();
		}
		session.close();
		return roles;
	}

	public Role getByName(String name) {
		initializer();
		Role role = null;
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			role = session.get(Role.class, name);
			tx.commit();
		} catch (HibernateException e) {
			session.close();
			return null;
		}
		session.close();
		return role;
	}
	public void initializer() {//initialing all the fixed roles of the system
		initialMainAdmin();
		initialGuest();
		initialSimpleAdmin();
		initialSimpleUser();
	}

	public void initialMainAdmin() {//Initialing the main admin role and user if not exist
		User mainAdmin = new User("mainAdmin", "mainAdmin", Role.mainAdminRole());
		Session session = getSession();
		try {
			session.beginTransaction();
			if (session.createQuery("from Role where name='mainAdmin'").uniqueResult() == null)
				session.save(Role.mainAdminRole());
		} catch (HibernateException x) {
			session.save(Role.mainAdminRole());
		}
		try {
			session.getTransaction().commit();
		} catch (HibernateException x) {
			x.getMessage();
		}
		session.close();

		session = getSession();
		if (session.createQuery("from User where role='mainAdmin'").uniqueResult() == null) {
			try {
				session.beginTransaction();
				session.save(mainAdmin);
				session.getTransaction().commit();
			} catch (HibernateException x) {
				x.getMessage();
			}
		}
		session.close();

	}

	public void initialSimpleAdmin() {//Initialing the simple admin role if not exist
		Session session = getSession();
		try {
			session.beginTransaction();
			if (session.createQuery("from Role where name='simpleAdmin'").uniqueResult() == null)
				session.save(Role.simpleAdminRole());
			session.getTransaction().commit();
		} catch (HibernateException x) {
			x.getMessage();
		}
		session.close();
	}

	public void initialSimpleUser() {//Initialing the simple user role if not exist
		Session session = getSession();
		try {
			session.beginTransaction();
			if (session.createQuery("from Role where name='simpleUser'").uniqueResult() == null)
				session.save(Role.simpleUserRole());
			session.getTransaction().commit();
		} catch (HibernateException x) {
			x.getMessage();
		}
		session.close();
	}

	public void initialGuest() {//initialing the guest role and guest user if not exist

		User guest = new User("guest", "guest", Role.guestRole());
		Session session = getSession();
		try {
			session.beginTransaction();
			if (session.createQuery("from Role where name='guest'").uniqueResult() == null)
				session.save(Role.guestRole());
			session.getTransaction().commit();
		} catch (HibernateException x) {
			x.getMessage();
		}
		session.close();

		session = getSession();
		if (session.createQuery("from User where role='guest'").uniqueResult() == null) {
			try {
				session.beginTransaction();
				session.save(guest);
				session.getTransaction().commit();
			} catch (HibernateException x) {
				x.getMessage();
			}
		}
		session.close();

	}
}
