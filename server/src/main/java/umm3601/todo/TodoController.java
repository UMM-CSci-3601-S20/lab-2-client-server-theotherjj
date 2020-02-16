package umm3601.todo;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

/**
 * Controller that manages requests for info about users.
 */
public class TodoController {

  private TodoDatabase database;

  /**
   * Construct a controller for users.
   * <p>
   * This loads the "TodoDatabase" of user info from a JSON file and stores that
   * internally so that (subsets of) users can be returned in response to
   * requests.
   *
   * @param todoDatabase the `Database` containing user data
   */
  public TodoController(TodoDatabase todoDatabase) {
    this.database = todoDatabase;
  }

  /**
   * Get the single user specified by the `id` parameter in the request.
   *
   * @param ctx a Javalin HTTP context
   */
  public void getTodo(Context ctx) {
    String id = ctx.pathParam("id", String.class).get();
    Todo todo = TodoDatabase.getTodo(id);
    if (todo != null) {
      ctx.json(todo);
      ctx.status(201);
    } else {
      throw new NotFoundResponse("No user with id " + id + " was found.");
    }
  }

  /**
   * Get a JSON response with a list of all the todos in the "TodoDatabase"
   *
   * @param ctx a Javalin HTTP context
   */
  public void getTodos(Context ctx) {
    Todo[] todos = TodoDatabase.listTodos(ctx.queryParamMap());
    ctx.json(todos);
  }

}
