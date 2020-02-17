package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listUser functionality
 */
public class FullTodoListFromDB {

  @Test
  public void firstTodoInFullList() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo firstTodo = allTodos[0];
    assertEquals("Blanche", firstTodo.owner, "Incorrect owner");
    assertEquals("software design", firstTodo.category, "Incorrect category");
  }

  @Test
  public void tenthTodoInFullList() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo tenthTodo = allTodos[9];
    assertEquals("Workman", tenthTodo.owner, "Incorrect owner");
    assertEquals("Eiusmod commodo officia amet aliquip est ipsum nostrud duis sunt voluptate mollit excepteur. Sunt non in pariatur et culpa est sunt.",
    tenthTodo.body, "Incorrect body");
  }

  @Test
  public void totalTodoCount() throws IOException {
    int counter = 0;

    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    System.out.println(db.getSize(allTodos));
    for(int i = 0; i < allTodos.length; i++) {
      counter++;
  }
    assertEquals(counter, db.size(), "Incorrect total number of todos");
    assertEquals(counter, 300, "Incorrect total number");
    assertNotEquals(counter, 0, "Incorrect total");
    assertNotEquals(counter, 100, "Incorrect total");
    assertNotEquals(counter, 10, "Incorrect total");
  }
}
