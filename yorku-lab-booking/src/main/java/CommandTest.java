import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.yorku.command.Command;


public class CommandTest {

	// Dummy implementation of Command
	private static class TestCommand implements Command {
		private boolean executed = false;

		@Override
		public void execute() {
			executed = true;
		}

		public boolean wasExecuted() {
			return executed;
		}
	}

	// 1. Test execute() runs without error
	@Test
	void testExecuteRuns() {
		Command cmd = new TestCommand();
		assertDoesNotThrow(cmd::execute);
	}

	// 2. Test execute changes state
	@Test
	void testExecuteChangesState() {
		TestCommand cmd = new TestCommand();
		cmd.execute();
		assertTrue(cmd.wasExecuted());
	}

	// 3. Test multiple executions
	@Test
	void testMultipleExecuteCalls() {
		TestCommand cmd = new TestCommand();

		cmd.execute();
		cmd.execute();

		assertTrue(cmd.wasExecuted());
    }

	// 4. Test command instance creation
	@Test
	void testCommandInstantiation() {
		Command cmd = new TestCommand();
		assertNotNull(cmd);
	}

	// 5. Test polymorphism
	@Test
	void testPolymorphicBehavior() {
		Command cmd = new TestCommand();
		cmd.execute();

		assertTrue(((TestCommand) cmd).wasExecuted());
	}

	// 6. Test separate instances independent
	@Test
	void testSeparateInstances() {
		TestCommand cmd1 = new TestCommand();
		TestCommand cmd2 = new TestCommand();

		cmd1.execute();

		assertTrue(cmd1.wasExecuted());
		assertFalse(cmd2.wasExecuted());
	}

	// 7. Test execute idempotency behavior
	@Test
	void testExecuteIdempotency() {
		TestCommand cmd = new TestCommand();

		cmd.execute();
		boolean firstState = cmd.wasExecuted();

		cmd.execute();
		boolean secondState = cmd.wasExecuted();

		assertEquals(firstState, secondState);
	}

	// 8. Test casting safety
	@Test
	void testCasting() {
		Command cmd = new TestCommand();
		assertInstanceOf(TestCommand.class, cmd);
	}

	// 9. Test execute with lambda (if interface supports it)
	@Test
	void testLambdaCommand() {
		Command cmd = () -> {};
		assertDoesNotThrow(cmd::execute);
	}

	// 10. Test execute side effect consistency
	@Test
	void testExecuteSideEffect() {
		TestCommand cmd = new TestCommand();

		assertFalse(cmd.wasExecuted());
		cmd.execute();
		assertTrue(cmd.wasExecuted());
	}
}