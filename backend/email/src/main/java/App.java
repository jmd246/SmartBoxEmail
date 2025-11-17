import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        /*email via gmail smtp*/
        

        Javalin app = Javalin.create()
        .get("/", ctx -> ctx.result("Helloworld"))
        .start(7070);
        
    }
}
