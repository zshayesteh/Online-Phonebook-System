package test.managers;

import static org.junit.Assert.*;
import org.junit.Test;

import ir.maktabsharif.model.entity.Role;
import ir.maktabsharif.model.entity.User;
import ir.maktabsharif.model.manager.RoleManager;
import ir.maktabsharif.model.manager.UserManager;

public class UserManagerTest {

	@Test
	public void test1() {
		assertNotNull(UserManager.getInstance());
	}

	@Test
	public void test2(){
		User u=new User();
		try{
			UserManager.getInstance().add(u);
			fail();
		}catch(Exception e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void test3() {
		User u=new User("ooo","ooo",null);
		try{
			assertTrue(UserManager.getInstance().add(u));
		}catch(Exception e)
		{
			fail();
		}
	}

	@Test
	public void test4() {
		User u=new User("ooo","ooo",null);
		try{
			assertFalse(UserManager.getInstance().add(u));
		}
		catch(Exception e){
			fail();
		}
	}
	@Test
	public void test5(){
		User u=new User("ipp",null,null);
		try{
			UserManager.getInstance().add(u);
		}
		catch(Exception e){
			assertTrue(true);;
		}
	}
	@Test
	public void test6(){
		User u=new User(null,null,null);
		try{
			UserManager.getInstance().add(u);
		}
		catch(Exception e){
			assertTrue(true);
		}
	}
	@Test
	public void test7(){
		try {
			assertTrue(UserManager.getInstance().delete(UserManager.getInstance().getByUserName("guest")));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test8(){
		User u=new User("test","test",null);
		try {
			assertFalse(UserManager.getInstance().add(u));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test9(){
		User u=new User("guest","guest",null);
		try {
			assertFalse(UserManager.getInstance().add(u));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test10(){
		Role r=RoleManager.getInstance().getByName("mainAdmin");
		User u=new User("mainAdmin","000",r);
		try {
			assertFalse(UserManager.getInstance().update(u));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test11(){
		User u=new User();
		u=UserManager.getInstance().getByUserName("ggg");
		u.setPassword("ggg");
		try {
			assertTrue(UserManager.getInstance().update(u));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test12(){
		User u=null;
		try {
			UserManager.getInstance().update(u);
			fail();
		} catch (Exception e) {
			assertNull(null);
		}
	}
	@Test
	public void test13(){
		User u=null;
		try {
			UserManager.getInstance().delete(u);
			fail();
		} catch (Exception e) {
			assertNull(null);
		}
	}
	@Test
	public void test14(){
		try {
			assertTrue(UserManager.getInstance().delete(UserManager.getInstance().getByUserName("ooo")));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test15(){
		assertNotNull(UserManager.getInstance().getByUserName("test"));
	}
	@Test
	public void test16(){
		User u=new User();
		u=UserManager.getInstance().getByUserName("test");
		assertEquals(u.getUserName(),"test");
	}
	@Test
	public void test17(){
		assertNull(UserManager.getInstance().getByUserName("haha"));
	}
}
