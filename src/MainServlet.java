import Entity.DAO;
import Entity.DataMessage;
import Entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    private int cur_user_id = -1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAO dao = new DAO();
        List<DataMessage> dataMessages = new ArrayList<DataMessage>();
        List<User> contacts = new ArrayList<User>();
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        System.out.println("Main session attribute: "+user.getUsername());
        try {
            dataMessages = dao.MesList(user);
            System.out.println("main count mess: "+ dataMessages.size());
            session.setAttribute("messages",dataMessages);
            session.setAttribute("user", user);
            cur_user_id = user.getId();
            contacts = dao.getUsers();
            //System.out.println("main contacts count: " + contacts.size());
            session.setAttribute("messages", dataMessages);
            session.setAttribute("contacts",contacts);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Enter doPost main!!!");
        this.doGet(req,resp);

    }

    public int getCur_user_id() {
        return cur_user_id;
    }
}
