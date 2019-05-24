

import Entity.DAO;
import Entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/hello", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getRequestDispatcher("/WEB-INF/login.html").forward(req, resp);
        System.out.println("Enter Login doGet");
        req.getRequestDispatcher("/WEB-INF/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Enter Login doPost");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("User data: " + username + " = " + password);
        DAO dao = new DAO();
        User user = new User();

        try {
            user = dao.getUser(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
            this.doGet(req,resp);
        }
        if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            //req.getRequestDispatcher("/main").forward(req,resp);
            resp.sendRedirect("/main");
        }
        else {
            this.doGet(req,resp);
        }

    }
}
