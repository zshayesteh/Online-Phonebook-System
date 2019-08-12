package test.managers;

import static org.junit.Assert.*;
import org.junit.Test;

import ir.maktabsharif.model.entity.Contact;
import ir.maktabsharif.model.manager.ContactManager;

public class ContactManagerTest {

	@Test
	public void test1() {
		assertNotNull(ContactManager.getInstance());
	}

	@Test
	public void test2(){
		Contact c=new Contact();
		try{
			ContactManager.getInstance().add(c);
			fail();
		}catch(Exception e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void test3() {
		Contact c=new Contact("kjkj","jkjkj","jhjh","jhjhj","kjkj");
		try{
			ContactManager.getInstance().add(c);
			fail();
		}catch(Exception e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void test4() {
		Contact c=new Contact("kjkj","jkjkj","9898k","jhjhj","kjkj");
		try{
			ContactManager.getInstance().add(c);
			fail();
		}
		catch(Exception e){
			assertTrue(true);;
		}
	}
	@Test
	public void test5(){
		Contact c=new Contact("kjkj","jkjkj","9898","0912","kjkj");
		try{
			ContactManager.getInstance().add(c);
			fail();
		}
		catch(Exception e){
			assertTrue(true);
		}
	}
	@Test
	public void test6(){
		Contact c=new Contact("new","nbnbn","988898","09128888888","hghg@yahoo.com");
		try{
			assertTrue(ContactManager.getInstance().add(c));
		}
		catch(Exception e){
			fail();
		}
	}
	@Test
	public void test7(){//id each time should be changed
		assertTrue(ContactManager.getInstance().delete(3));
	}
	@Test
	public void test8(){
		assertNull(ContactManager.getInstance().get(33));
	}

	@Test
	public void test9() {//each time should be changed
		Contact c=new Contact("cccc","cccc","988898","09128888888",null);
		try{
			assertTrue(ContactManager.getInstance().add(c));
		}catch(Exception e)
		{
			fail();
		}
	}
	@Test
	public void test10() {
		Contact c=new Contact(null,"nbnbn","988898","09128888888",null);
		try{
			ContactManager.getInstance().add(c);
			fail();
		}catch(Exception e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void test11() {
		Contact c=new Contact("new","nbnbn","988898","09128888888",null);
		try{
			assertFalse(ContactManager.getInstance().add(c));
		}catch(Exception e)
		{
			fail();
		}
	}
	@Test
	public void test12(){
		Contact c=new Contact("new1","nbnbn","988898","09128888888",null);
		try{
			ContactManager.getInstance().add(c);
			fail();
		}catch(Exception e)
		{
			assertTrue(true);
		}
	}
	@Test
	public void test13(){
		Contact c=new Contact("new","nbnbn1","988898","09128888888",null);
		try{
			ContactManager.getInstance().add(c);
			fail();
		}catch(Exception e)
		{
			assertTrue(true);
		}
	}
	@Test
	public void test14(){
		try {
			Contact u=ContactManager.getInstance().get(7);
			u.setName("xxx");
			assertTrue(ContactManager.getInstance().update(u));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test15(){
		try {
			Contact u=ContactManager.getInstance().get(2);
			u.setName("bbb1");
			ContactManager.getInstance().update(u);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	@Test
	public void test16(){
		try {
			Contact u=ContactManager.getInstance().get(7);
			u.setSurname(null);
			assertTrue(ContactManager.getInstance().update(u));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test17(){
		try {
			Contact u=ContactManager.getInstance().get(2);
			u.setHomeNumber("bbb");
			ContactManager.getInstance().update(u);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	@Test
	public void test18(){
		try {
			Contact u=ContactManager.getInstance().get(2);
			u.setMobile("08119999999");
			ContactManager.getInstance().update(u);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	@Test
	public void test19(){
		try {
			Contact u=ContactManager.getInstance().get(2);
			u.setEmail("a1@m.c");
			ContactManager.getInstance().update(u);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	@Test
	public void test20(){//id each time should be changed
		try {
			assertTrue(ContactManager.getInstance().delete(ContactManager.getInstance().get(8)));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test21(){
		Contact u=null;
		try {
			ContactManager.getInstance().update(u);
			fail();
		} catch (Exception e) {
			assertNull(null);
		}
	}
	@Test
	public void test22(){
		Contact u=null;
		try {
			ContactManager.getInstance().delete(u);
			fail();
		} catch (Exception e) {
			assertNull(null);
		}
	}
	@Test
	public void test23(){
		assertNotNull(ContactManager.getInstance().get(7));
	}
	@Test
	public void test24(){//each time should be changed
		try {
			assertTrue(ContactManager.getInstance().delete(ContactManager.getInstance().get(5)));
		} catch (Exception e) {
			fail();
		}
	}
	
}
