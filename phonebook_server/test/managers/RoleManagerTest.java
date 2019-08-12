package test.managers;

import static org.junit.Assert.*;

import org.junit.Test;

import ir.maktabsharif.model.entity.Role;
import ir.maktabsharif.model.manager.RoleManager;

public class RoleManagerTest {

	@Test
	public void test1() {
		Role r=new Role();
		assertNotNull(r);
	}
	@Test
	public void test2() {
		assertNotNull(RoleManager.getInstance().getByName("guest"));
	}

	@Test
	public void test3() {
		assertNull(RoleManager.getInstance().getByName("abc"));
	}

	@Test
	public void test4() {
		Role r=new Role();
		r.setName("n");
		try {
			assertTrue(RoleManager.getInstance().add(r));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test5() {
		try {
			assertFalse(RoleManager.getInstance().delete(RoleManager.getInstance().getByName("guest")));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test6() {
		try {
			assertFalse(RoleManager.getInstance().delete(RoleManager.getInstance().getByName("jjj")));
		} catch (Exception e) {
			assertTrue(true);
		}
	}

}
