import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "student" , urlPatterns = "/student")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
           Jsonb jsonb = JsonbBuilder.create();
           Student student = jsonb.fromJson(req.getReader(), Student.class);
           System.out.println(student);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
