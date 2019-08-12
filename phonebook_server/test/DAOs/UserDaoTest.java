package test.DAOs;

import static org.junit.Assert.*;

import org.junit.Test;

import ir.maktabsharif.model.dao.UserDAO;
import ir.maktabsharif.model.entity.Role;
import ir.maktabsharif.model.entity.User;
import ir.maktabsharif.model.manager.RoleManager;

public class UserDaoTest {

	@Test
	public void test1() {
		assertNotNull(UserDAO.getInstance());
	}

	@Test
	public void test2(){
		User u=new User();
		assertFalse(UserDAO.getInstance().add(u));
	}

	@Test
	public void test3() {
		Role r=RoleManager.getInstance().getByName("simpleUser");
		User u=new User("oo","oo",r);
		assertTrue(UserDAO.getInstance().add(u));
	}

	@Test
	public void test4() {
		Role r=RoleManager.getInstance().getByName("simpleUser");
		User u=new User("oo","oo",r);
		assertFalse(UserDAO.getInstance().add(u));
	}

	@Test
	public void test5(){
		assertTrue(UserDAO.getInstance().delete(UserDAO.getInstance().getByUserName("guest")));
	}
	@Test
	public void test6(){
		Role r=RoleManager.getInstance().getByName("simpleUser");
		User u=new User("test","test",r);
		assertFalse(UserDAO.getInstance().add(u));
	}
	@Test
	public void test7(){
		User u=new User();
		u=UserDAO.getInstance().getByUserName("ggg");
		u.setPassword("ggg");
		assertTrue(UserDAO.getInstance().update(u));
	}
	@Test
	public void test8(){
		User u=null;
		assertFalse(UserDAO.getInstance().update(u));
	}
	@Test
	public void test9(){
		User u=null;
		assertFalse(UserDAO.getInstance().delete(u));
	}
	@Test
	public void test10(){
		assertTrue(UserDAO.getInstance().delete(UserDAO.getInstance().getByUserName("oo")));
	}
	@Test
	public void test11(){
		assertNotNull(UserDAO.getInstance().getByUserName("test"));
	}
	@Test
	public void test12(){
		User u=new User();
		u=UserDAO.getInstance().getByUserName("test");
		assertEquals(u.getUserName(),"test");
	}
	@Test
	public void test13(){
		assertNull(UserDAO.getInstance().getByUserName("haha"));
	}
}
