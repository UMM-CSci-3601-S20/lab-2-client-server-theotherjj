package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.junit.jupiter.api.BeforeEach;
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

  @Test
  public void orderByOwner() throws IOException{
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("owner", Arrays.asList(new String[] {"Blanche"}));
    Todo[] blancheOwners = db.listTodos(queryParams);
    System.out.println(blancheOwners.length);
    Todo firstBlanche = blancheOwners[0];
    assertEquals(43, blancheOwners.length, "Incorrect number of owners name Blanche");
    assertEquals("58895985a22c04e761776d54", firstBlanche._id, "Incorrect id for first Blanche");

    queryParams.clear();
    queryParams.put("owner", Arrays.asList(new String[] {"Fry"}));
    queryParams.put("limit", Arrays.asList(new String[] {"5"}));
    Todo[] fryOwners = db.listTodos(queryParams);
    Todo firstFry = fryOwners[0];
    assertEquals(5, fryOwners.length, "Incorret number of Frys");
    assertEquals("58895985c1849992336c219b", firstFry._id, "Incorrect owner of Fry limits");
  }

  @Test
  public void orderByCategory() throws IOException{
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("category", Arrays.asList(new String[] {"video games"}));
    Todo[] vgCategory = db.listTodos(queryParams);
    System.out.println(vgCategory.length);
    Todo firstVG = vgCategory[0];
    assertEquals(71, vgCategory.length, "Incorrect number of categories in videogames");
    assertEquals("58895985c1849992336c219b", firstVG._id, "Incorrect id for first video game category");

    queryParams.clear();
    queryParams.put("category", Arrays.asList(new String[] {"software design"}));
    queryParams.put("limit", Arrays.asList(new String[] {"3"}));
    Todo[] sdCategory = db.listTodos(queryParams);
    Todo firstSD = sdCategory[2];
    assertEquals(3, sdCategory.length, "Incorret number of software design category");
    assertEquals("5889598528c4748a0292e014", firstSD._id, "Incorrect category id in limits");

  }

  @Test
  public void orderByBody() throws IOException{
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("category", Arrays.asList(new String[] {"video games"}));
    queryParams.put("body", Arrays.asList(new String[] {"tempor"}));
    Todo[] adBody = db.listTodos(queryParams);
    System.out.println(adBody.length);
    Todo firstVG = adBody[0];
    assertEquals(71, adBody.length, "Incorrect number of categories in videogames");
    assertEquals("58895985c1849992336c219b", firstVG._id, "Incorrect id for first video game category");

    queryParams.clear();
    queryParams.put("category", Arrays.asList(new String[] {"software design"}));
    queryParams.put("limit", Arrays.asList(new String[] {"3"}));
    Todo[] sdCategory = db.listTodos(queryParams);
    Todo firstSD = sdCategory[2];
    assertEquals(3, sdCategory.length, "Incorret number of software design category");
    assertEquals("5889598528c4748a0292e014", firstSD._id, "Incorrect category id in limits");

  }


}
