package umm3601.todo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * A fake "database" of todo info
 * <p>
 * Since we don't want to complicate this lab with a real database, we're going
 * to instead just read a bunch of todo data from a specified JSON file, and
 * then provide various database-like methods that allow the `TodoController` to
 * "query" the "database".
 */
public class TodoDatabase{

  private Todo[] allTodos;

  public TodoDatabase(String todoDataFile) throws IOException {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
    allTodos = gson.fromJson(reader, Todo[].class);
  }

  public int size() {
    return allTodos.length;
  }

  public int getSize(Todo[] arrayToMeasure){
    int arraySize = 0;
    arraySize = arrayToMeasure.length;
    return arraySize;
  }

  /**
   * Get the single todo specified by the given ID. Return `null` if there is no
   * todo with that ID.
   *
   * @param id the ID of the desired todo
   * @return the todo with the given ID, or null if there is no todo with that ID
   */
  public Todo getTodo(String id) {
    return Arrays.stream(allTodos).filter(x -> x._id.equals(id)).findFirst().orElse(null);
  }

  /**
   * Get an array of all the todos satisfying the queries in the params.
   *
   * @param queryParams map of key-value pairs for the query
   * @return an array of all the todos matching the given criteria
   */
  public Todo[] listTodos(Map<String, List<String>> queryParams){
    Todo[] filteredTodos = allTodos;

    // Filter owner if defined
    if (queryParams.containsKey("owner")) {
      String targetOwner = queryParams.get("owner").get(0);
      filteredTodos = filterTodoByOwner(filteredTodos, targetOwner);
    }
    // Filter category if defined
    if (queryParams.containsKey("category")) {
      String targetCategory = queryParams.get("category").get(0);
      filteredTodos = filterTodoByCategory(filteredTodos, targetCategory);
    }

    // Filter by status
    if (queryParams.containsKey("status")) {
      String targetStatus = queryParams.get("status").get(0);
      filteredTodos = filterTodoByStatus(filteredTodos, targetStatus);
    }

    // Sort todos
    if (queryParams.containsKey("orderBy")){
    String orderByTarget = queryParams.get("orderBy").get(0);
    switch (orderByTarget) {
     case "owner":
     filteredTodos = orderByOwner(filteredTodos, orderByTarget);
     break;

     case "category":
     filteredTodos = orderByCategory(filteredTodos, orderByTarget);
     break;

     case "body":
     filteredTodos = orderByBody(filteredTodos, orderByTarget);
     break;

     case "status":
     Todo[] completeTodos = filterTodoByStatus(filteredTodos, "complete");
     Todo[] incompleteTodos = filterTodoByStatus(filteredTodos, "incomplete");
     Todo[] concatenateTodos = combine(completeTodos,incompleteTodos);
     filteredTodos = concatenateTodos;
     break;
     default:
     }
    }


    // Limit result numbers
    if (queryParams.containsKey("limit")) {
      String targetLimit = queryParams.get("limit").get(0);
      targetLimit = targetLimit.trim(); // Trim white spaces around numbers
      if (targetLimit.matches("[0-9]+") && targetLimit.length() <= 4) { // If target length is less than 9999
        int limit = Integer.valueOf(targetLimit);
        if (getSize(filteredTodos) > limit) {
        filteredTodos = limitTodosList(filteredTodos, limit);
        }
      }
      /* If target length is greater than 4 digits (9999), we may reach the integer size limit (5 digits).
          Any database is finite. Rather than use a long type which can also run out, we check if the string
          (composed only of numbers) is longer than the known size of our database (with a safety margin). If we don't know the
          size of our database within a reasonable margin, code elements such as variables, which are tied to hardware,
          ultimately, may start breaking down.
      */
      else if (targetLimit.matches("[0-9]+") && targetLimit.length() > 4)
      {
        filteredTodos = allTodos;
      }    else {
      filteredTodos = limitTodosList(filteredTodos, 0);
      }
    }
    // Process other query parameters here...

    return filteredTodos;
  }







  /**
   * Get an array of all the todos having the target owner.
   *
   * @param todos     the list of todos to filter by owner
   * @param targetOwner the target owner to look for
   * @return an array of all the todos from the given list that have the target
   *         owner
   */
  public Todo[] filterTodoByOwner(Todo[] todos, String targetOwner) {
    return Arrays.stream(todos).filter(x -> x.owner.equals(targetOwner)).toArray(Todo[]::new);
  }


  /**
   * Get an array of all the todos having the target category.
   *
   * @param todos         the list of todos to filter by category
   * @param targetCategory the target category to look for
   * @return an array of all the todos from the given list that have the target
   *         category
   */
  public Todo[] filterTodoByCategory(Todo[] todos, String targetCategory) {
    return Arrays.stream(todos).filter(x -> x.category.equals(targetCategory)).toArray(Todo[]::new);
  }


  /**
   * Get an array of all the todos having the target status
   *
   * @param todos         the list of todos to filter by status
   * @param targetStatus the target status to look for
   * @return an array of all the todos from the given list that have the target
   * status
   */
  public Todo[] filterTodoByStatus(Todo[] todos, String targetStatus) {
    if (targetStatus.equals("incomplete")){
      return Arrays.stream(todos).filter(x -> x.status == false).toArray(Todo[]::new);
    }
    else if (targetStatus.equals("complete")) {
      return Arrays.stream(todos).filter(x -> x.status == true).toArray(Todo[]::new);
    }
    else
      return todos;
  }

   /**
  * Limit the number of results in array.
  * @param todos         the list of todos to limit
  * @param limit the number of items the list should be limited to
  * @return an array of all the todos from the given list that contains
  * no more items than specified
  */
  public Todo[] limitTodosList(Todo[] todos, int limit) {
    Todo[] results = new Todo[limit];
    for(int i = 0; i < limit; i++){
    results [i] = todos[i];
    }

    return results;
    }

  public Todo[] orderByOwner(Todo[] todos, String targetOrderBy){
    return Arrays.stream(todos).sorted((p1,p2) -> p1.owner.compareTo(p2.owner)).toArray(Todo[]::new);
  }

  public Todo[] orderByBody(Todo[] todos, String targetOrderBy){
    return Arrays.stream(todos).sorted((p1,p2) -> p1.body.compareTo(p2.body)).toArray(Todo[]::new);
  }

  public Todo[] orderByCategory(Todo[] todos, String targetOrderBy){
    return Arrays.stream(todos).sorted((p1,p2) -> p1.category.compareTo(p2.category)).toArray(Todo[]::new);
  }

    //Combining two arrays for status ordering - for testing
    public static Todo[] combine(Todo[] completeTodos, Todo[] incompleteTodos) {
      int length = completeTodos.length + incompleteTodos.length;
      Todo[] result = new Todo[length];
      System.arraycopy(completeTodos, 0, result, 0, completeTodos.length);
      System.arraycopy(incompleteTodos, 0, result, completeTodos.length, incompleteTodos.length);
      return result;
  }

  public Todo[] searchBody(Todo[] todos, String searchTerm) {
    return Arrays.stream(todos).filter(x -> x.body.toLowerCase().contains(searchTerm)).toArray(Todo[]::new);
   }


}
