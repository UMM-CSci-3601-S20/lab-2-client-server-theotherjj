package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
//import java.util.Arrays;
import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listUser functionality
 */
public class TodoBodiesFilterTest{

  @Test
  public void firstBodyFilter() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo[] firstTodosBody = db.searchBody(allTodos, "tempor");
    System.out.println(firstTodosBody.length);
    Todo firstTempor = firstTodosBody[0];
    assertEquals(72, firstTodosBody.length, "Incorrect total number of todos");
    assertEquals("58895985a22c04e761776d54", firstTempor._id, "Incorrect id for firstTempor");
    assertEquals("Blanche", firstTempor.owner, "Incorrect owner for firstTempor");
    assertEquals("software design", firstTempor.category, "Incorrect category for firstTempor");
  }

  // @Test
  // public void bodyFilterWithStatus() throws IOException{
  //   TodoDatabase db = new TodoDatabase("/todos.json");
  //   Map<String, List<String>> queryParams = new HashMap<>();

  //   queryParams.put("contains", Arrays.asList(new String[] {"tempor ullamco"}));
  //   queryParams.put("status", Arrays.asList(new String[] { "complete" }));
  //   Todo[] completeTodosTempor = db.listTodos(queryParams);
  //   System.out.println(completeTodosTempor.length);
  //   Todo thirdCompleteTemporTodo = completeTodosTempor[2];
  //   System.out.println(thirdCompleteTemporTodo.owner);
  //   System.out.println(thirdCompleteTemporTodo.category);
  //   System.out.println(thirdCompleteTemporTodo._id);
  //   System.out.println(thirdCompleteTemporTodo.status);
  //   assertEquals(3, completeTodosTempor.length, "Incorrect number of todos with complete status");
  //   assertEquals("58895985839dd0a53b16bf3f", thirdCompleteTemporTodo._id, "Incorrect id with tempor and complete");
  //   assertEquals("Roberta", thirdCompleteTemporTodo.owner, "Incorrect owner with tempor and complete");
  // }

  // @Test
  // public void bodyFilterWithLimit() throws IOException{
  //   TodoDatabase db = new TodoDatabase("/todos.json");
  //   Map<String, List<String>> queryParams = new HashMap<>();
  //   queryParams.put("contains", Arrays.asList(new String[] {"tempor ullamco"}));
  //   queryParams.put("limit", Arrays.asList(new String[] { "2" }));
  //   Todo[] limitTodosTempor = db.listTodos(queryParams);
  //   System.out.println(limitTodosTempor.length);
  //   Todo secondLimitTemporTodo = limitTodosTempor[1];
  //   assertEquals(2, limitTodosTempor.length, "Incorrect number of limit Tempor todos");
  //   assertEquals("58895985daa7d137399e8c58", secondLimitTemporTodo._id, "Incorrect id of limit Tempor todos");
  //   assertEquals(true, secondLimitTemporTodo.status, "Incorrect status of limit Tempor todos");
  // }

}
