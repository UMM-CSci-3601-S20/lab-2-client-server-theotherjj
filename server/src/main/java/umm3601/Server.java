package umm3601;

import java.io.IOException;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import umm3601.user.UserDatabase;
import umm3601.user.UserController;
import umm3601.todo.TodoController;
import umm3601.todo.TodoDatabase;

public class Server {

  public static final String CLIENT_DIRECTORY = "../client";
  public static final String USER_DATA_FILE = "/users.json";
  public static final String TODOS_DATA_FILE = "/todos.json";
  private static UserDatabase userDatabase;
  private static TodoDatabase todoDatabase;


  public static void main(String[] args) {

    // Initialize dependencies
    UserController userController = buildUserController();
    TodoController todoController = buildTodoController();

    Javalin server = Javalin.create(config -> {
      // This tells the server where to look for static files,
      // like HTML and JavaScript.
      config.addStaticFiles(CLIENT_DIRECTORY, Location.EXTERNAL);
      // The next line starts the server listening on port 4567.
    }).start(4567);

    // Simple example route
    server.get("/hello", ctx -> ctx.result("Hello World"));

    // Redirects to create simpler URLs
    server.get("/users", ctx -> ctx.redirect("/users.html"));
    server.get("/todos", ctx -> ctx.redirect("/todos.html"));

    // API endpoints

    // Get specific user
    server.get("api/users/:id", ctx -> userController.getUser(ctx));

    // List users, filtered using query parameters
    server.get("api/users", ctx -> userController.getUsers(ctx));

    //Get specific todos
    server.get("api/todos/:id", ctx -> todoController.getTodo(ctx));
    server.get("api/todos", ctx -> todoController.getTodos(ctx));

    //List todos by limits
    server.get("api/todos?owner=", ctx -> todoController.getTodos(ctx));
    server.get("api/todos?category=", ctx -> todoController.getTodos(ctx));
    server.get("api/todos?limit=", ctx -> todoController.getTodos(ctx));
    server.get("api/todos?orderBy=", ctx -> todoController.getTodos(ctx));
    server.get("api/todos?body=", ctx -> todoController.getTodos(ctx));
    server.get("api/todos?status=", ctx -> todoController.getTodos(ctx));
  }

  /***
   * Create a database using the json file, use it as data source for a new
   * UserController
   *
   * Constructing the controller might throw an IOException if there are problems
   * reading from the JSON "database" file. If that happens we'll print out an
   * error message exit the program.
   */
   private static UserController buildUserController() {
   UserController userController = null;

     try {
      userDatabase = new UserDatabase(USER_DATA_FILE);
       userController = new UserController(userDatabase);
     } catch (IOException e) {
       System.err.println("The server failed to load the user data; shutting down.");
       e.printStackTrace(System.err);

       // Exit from the Java program
       System.exit(1);
     }

     return userController;
   }

  private static TodoController buildTodoController() {
    TodoController todoController = null;

    try {
      todoDatabase = new TodoDatabase(TODOS_DATA_FILE);
      todoController = new TodoController(todoDatabase);
    } catch (IOException e) {
      System.err.println("The server failed to load the user data; shutting down.");
      e.printStackTrace(System.err);

      // Exit from the Java program
      System.exit(1);
    }

    return todoController;
  }

}
