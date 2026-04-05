import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.yorku.App;
import com.yorku.coordinator.LabManager;

public class AppTest {

    // 1. Test initial state (before start() is called)
	@Test
	void testLabManagerInitiallyNull() {
		assertNull(App.getLabManager());
	}

	@Test
	void testSetAndGetLabManager() throws Exception {
		LabManager dummyManager = new LabManager("Test", "test@yorku.ca", "pass", "LM-01");

		java.lang.reflect.Field field = App.class.getDeclaredField("labManager");
		field.setAccessible(true);
		field.set(null, dummyManager);

		assertEquals(dummyManager, App.getLabManager());
	}


	@Test
	void testLabManagerNotNullAfterSet() throws Exception {
		LabManager dummyManager = new LabManager("Test2", "test2@yorku.ca", "pass", "LM-02");

		java.lang.reflect.Field field = App.class.getDeclaredField("labManager");
		field.setAccessible(true);
		field.set(null, dummyManager);

		assertNotNull(App.getLabManager());
	}

	// 4. Test multiple assignments
    @Test
	void testLabManagerOverwrite() throws Exception {
    	LabManager manager1 = new LabManager("A", "a@yorku.ca", "pass", "LM-01");
        LabManager manager2 = new LabManager("B", "b@yorku.ca", "pass", "LM-02");

        java.lang.reflect.Field field = App.class.getDeclaredField("labManager");
        field.setAccessible(true);

        field.set(null, manager1);
        assertEquals(manager1, App.getLabManager());

        field.set(null, manager2);
        assertEquals(manager2, App.getLabManager());
    }

	// 5. Test null assignment
    @Test
	void testSetLabManagerToNull() throws Exception {
    	java.lang.reflect.Field field = App.class.getDeclaredField("labManager");
    	field.setAccessible(true);

    	field.set(null, null);
    	assertNull(App.getLabManager());
	}

	// 6. Test class loading
	@Test
	void testAppClassLoads() {
		assertDoesNotThrow(() -> {
			Class.forName("com.yorku.App");
		});
	}

	// 7. Test main method does not crash (basic)
	@Test
	void testMainMethodExists() {
		assertDoesNotThrow(() -> {
			App.main(new String[]{});
		});
	}

	// 8. Test getLabManager consistency
	@Test
	void testGetLabManagerConsistency() throws Exception {
		LabManager manager = new LabManager("C", "c@yorku.ca", "pass", "LM-03");

		java.lang.reflect.Field field = App.class.getDeclaredField("labManager");
		field.setAccessible(true);
		field.set(null, manager);

		assertSame(manager, App.getLabManager());
	}

	// 9. Test repeated calls
	@Test
	void testMultipleGetCalls() throws Exception {
		LabManager manager = new LabManager("D", "d@yorku.ca", "pass", "LM-04");

		java.lang.reflect.Field field = App.class.getDeclaredField("labManager");
		field.setAccessible(true);
		field.set(null, manager);

		assertEquals(manager, App.getLabManager());
		assertEquals(manager, App.getLabManager());
	}

	// 10. Edge case: reflection access
	@Test
	void testReflectionAccess() {
		assertDoesNotThrow(() -> {
			java.lang.reflect.Field field = App.class.getDeclaredField("labManager");
			field.setAccessible(true);
		});
	}
}