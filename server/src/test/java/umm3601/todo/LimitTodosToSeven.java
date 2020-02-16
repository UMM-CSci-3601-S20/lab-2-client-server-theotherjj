package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listUser functionality
 */
public class LimitTodosToSeven {
  @Test
  public void totalTodoCount() throws IOException {
    int counter = 0;
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo[] sevenTodos = db.limitTodosList(allTodos, 7);
    for(int i = 0; i < sevenTodos.length; i++) {
      counter++;
  }
    assertEquals(counter, 7, "Incorrect total number of users");
    assertNotEquals(counter, 0, "Incorrect total");
    assertNotEquals(counter, 100, "Incorrect total");
    assertNotEquals(counter, 10, "Incorrect total");
  }




}
