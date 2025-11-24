import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        /*email via gmail smtp*/
        //need oauth2 and will need to abstract so aany email provider can be used

        Javalin app = Javalin.create()
        .get("/", ctx -> ctx.result("Helloworld"))
        .start(7070);

        EmailSender sender = new EmailSender("smtp.gmail.com");
        sender.sendEmail("jdoucette69.jd@gmail.com","jdoucette69.jd@gmail.com","Test","Test");
        
    }
}
