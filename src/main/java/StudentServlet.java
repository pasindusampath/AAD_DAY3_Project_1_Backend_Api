import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "student", urlPatterns = "/student")
public class StudentServlet extends HttpServlet {
    Connection con;
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ((req.getContentType() == null) || !(req.getContentType().equalsIgnoreCase("application/json"))) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        Jsonb jsonb = JsonbBuilder.create();
        Student student = jsonb.fromJson(req.getReader(), Student.class);
        if (!student.getName().matches("^[a-zA-Z]+(([a-zA-Z ])?[a-zA-Z]*)*$")) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"Invalid Name");
            return;
        } else if (!student.getCity().matches("^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$")) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"Invalid City Name");
            return;
        }else if(student.getEmail().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-]+)(\\.[a-zA-Z]{2,5}){1,2}$")){
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"Invalid Email Address");
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement("insert  into student (name,city,email,level) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,student.getName());
            ps.setString(2,student.getCity());
            ps.setString(3,student.getEmail());
            ps.setInt(4,student.getLevel());

            int i = ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                int key = keys.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
