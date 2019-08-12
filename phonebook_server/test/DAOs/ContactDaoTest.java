package test.DAOs;

import static org.junit.Assert.*;
import org.junit.Test;

import ir.maktabsharif.model.dao.ContactDAO;
import ir.maktabsharif.model.entity.Contact;

public class ContactDaoTest {

	@Test
	public void test1() {
		assertNotNull(ContactDAO.getInstance());
	}

	@Test
	public void test2(){
		Contact u=new Contact();
		assertFalse(ContactDAO.getInstance().add(u));
	}
	@Test
	public void test3() {
		Contact c=new Contact();
		c.setName("newww");
		c.setSurname("fff");
		assertTrue(ContactDAO.getInstance().add(c));
	}

	@Test
	public void test4() {
		Contact c=new Contact();
		c.setName("aaaa");
		assertTrue(ContactDAO.getInstance().add(c));
	}

	@Test
	public void test5() {
		Contact c=new Contact();
		c.setName("aaa");
		assertFalse(ContactDAO.getInstance().add(c));
	}

	@Test
	public void test6() {
		assertNull(ContactDAO.getInstance().getById(47));
	}

	@Test
	public void test7() {
		Contact c=new Contact();
		c=ContactDAO.getInstance().getById(9);
		c.setHomeNumber("jhjhj");
		assertFalse(ContactDAO.getInstance().update(c));
	}

	@Test
	public void test8() {
		Contact c=new Contact();
		c=null;
		assertFalse(ContactDAO.getInstance().delete(c));
	}

	@Test
	public void test9() {
		Contact c=new Contact("kjkj","jkjkj","jhjh","jhjhj","kjkj");
		assertFalse(ContactDAO.getInstance().add(c));
	}
	
	@Test
	public void test10(){
		Contact u=null;
		assertFalse(ContactDAO.getInstance().update(u));
	}
	@Test
	public void test11(){
		Contact u=null;
		assertFalse(ContactDAO.getInstance().delete(u));
	}
	@Test
	public void test12(){
		assertFalse(ContactDAO.getInstance().delete(ContactDAO.getInstance().getById(55)));
	}
	@Test
	public void test13(){
		assertNotNull(ContactDAO.getInstance().getById(6));
	}

	@Test
	public void test14() {
		Contact c=null;
		assertFalse(ContactDAO.getInstance().add(c));
	}

	@Test
	public void test15() {
		Contact c=new Contact();
		c.setSurname("ddd");
		assertFalse(ContactDAO.getInstance().add(c));
	}

}
