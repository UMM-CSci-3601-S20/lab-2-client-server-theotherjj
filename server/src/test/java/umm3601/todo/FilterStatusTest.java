package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listUser functionality
 */
public class FilterStatusTest {

  @Test
  public void completeStatus() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo[] completeTodos = db.filterTodoByStatus(allTodos, "complete");
    Todo firstTodo = completeTodos[0];
    assertEquals("Fry", firstTodo.owner, "Wrong owner");
    assertEquals("homework", firstTodo.category,"Wrong category");
    assertEquals("Ullamco irure laborum magna dolor non. Anim occaecat adipisicing cillum eu magna in.",
    firstTodo.body, "Wrong body");
    assertEquals(true, firstTodo.status, "Wrong status");
  }

  @Test
  public void incompleteStatus() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo[] completeTodos = db.filterTodoByStatus(allTodos, "incomplete");
    Todo firstTodo = completeTodos[0];
    assertEquals("Blanche", firstTodo.owner, "Wrong owner");
    assertEquals("software design", firstTodo.category,"Wrong category");
    assertEquals("In sunt ex non tempor cillum commodo amet incididunt anim qui commodo quis. Cillum non labore ex sint esse.",
    firstTodo.body, "Wrong body");
    assertEquals(false, firstTodo.status, "Wrong status");
    assertEquals("58895985a22c04e761776d54", firstTodo._id, "Wrong id");
  }

  @Test
  public void listTodosStatusFilters() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("status", Arrays.asList(new String[] { "complete" }));
    Todo[] completeTodos = db.listTodos(queryParams);
    System.out.println(completeTodos.length);
    assertEquals(143, completeTodos.length, "Incorrect number of todos with complete status");

    queryParams.clear();
    queryParams.put("status", Arrays.asList(new String[] { "incomplete" }));
    Todo[] incompleteTodos = db.listTodos(queryParams);
    System.out.println(incompleteTodos.length);
    assertEquals(157, incompleteTodos.length, "Incorrect number of todos with incomplete status");

    //With no status filter
    queryParams.clear();
    queryParams.put("status", Arrays.asList(new String[] { "" }));
    //queryParams.put("limit", Arrays.asList(new String[] { "7" }));
    Todo[] allTodos = db.listTodos(queryParams);
    assertEquals(300, allTodos.length, "Incorrect number of todos");

    //Tests with limit attached to status
    queryParams.clear();
    queryParams.put("status", Arrays.asList(new String[] {"complete"}));
    queryParams.put("limit", Arrays.asList(new String[] {"2"}));
    Todo[] limitCompleteTodos = db.listTodos(queryParams);
    Todo secondLimit = limitCompleteTodos[1];
    System.out.println(limitCompleteTodos.length);
    assertEquals(2, limitCompleteTodos.length, "Incorrect number of todos with complete status and limit");
    assertEquals("58895985186754887e0381f5", secondLimit._id, "Wrong second todo id");
    assertEquals("software design", secondLimit.category, "Wrong second todo category");

    queryParams.clear();
    queryParams.put("status", Arrays.asList(new String[] {"incomplete"}));
    queryParams.put("limit", Arrays.asList(new String[] {"2"}));
    Todo[] limitIncompleteTodos = db.listTodos(queryParams);
    Todo secondIncompleteLimit = limitIncompleteTodos[1];
    System.out.println(limitIncompleteTodos.length);
    assertEquals(2, limitIncompleteTodos.length, "Incorrect number of todos with complete status and limit");
    assertEquals("58895985c1849992336c219b", secondIncompleteLimit._id, "Wrong second todo id");
    assertEquals("video games", secondIncompleteLimit.category, "Wrong second todo category");


  }


}
