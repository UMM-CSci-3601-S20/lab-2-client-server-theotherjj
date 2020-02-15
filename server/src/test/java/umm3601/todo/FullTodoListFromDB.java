package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.user.Database listUser functionality
 */
public class FullTodoListFromDB {

  @Test
  public void firstTodoInFullList() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo firstTodo = allTodos[0];
    assertEquals("Blanche", firstTodo.owner, "Incorrect name");
    assertEquals("software design", firstTodo.category, "Incorrect e-mail");
  }
}
