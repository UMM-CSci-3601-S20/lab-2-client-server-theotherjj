package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listUser functionality
 */
public class OrderTodoTest {


  public static Todo[] combine(Todo[] completeTodos, Todo[] incompleteTodos) {
    int length = completeTodos.length + incompleteTodos.length;
    Todo[] result = new Todo[length];
    System.arraycopy(completeTodos, 0, result, 0, completeTodos.length);
    System.arraycopy(incompleteTodos, 0, result, completeTodos.length, incompleteTodos.length);
    return result;
}

  @Test
  public void orderByStatus() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo[] completeTodos = db.filterTodoByStatus(allTodos, "complete");
    System.out.println(completeTodos.length);
    Todo[] incompleteTodos = db.filterTodoByStatus(allTodos, "incomplete");
    System.out.println(incompleteTodos.length);
    Todo[] concatenateTodos = combine(completeTodos,incompleteTodos);
    System.out.println(concatenateTodos.length);
    Todo lastCompleteTodo = concatenateTodos[143-1];
    System.out.println(lastCompleteTodo._id);
    Todo firstIncompleteTodo = concatenateTodos[144-1];
    System.out.println(firstIncompleteTodo._id);

    assertEquals(300, concatenateTodos.length,"Incorrect total number of todos");
    assertEquals("58895985a69d6afde00af172", lastCompleteTodo._id, "Incorrect todo last complete todo");
    assertEquals("58895985a22c04e761776d54", firstIncompleteTodo._id, "Incorrect todo first incomplete");
    // assertNotEquals(counter, 10, "Incorrect total");

  }


}
