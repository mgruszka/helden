package pl.protka;


import pl.protka.db.DatabaseDriver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gruszek on 02.05.15.
 */
@WebServlet(name = "helden")
public class MainServlet extends HttpServlet {
    DatabaseDriver dbdriver = DatabaseDriver.getInstance();
    List<String> list= new ArrayList<>(dbdriver.getPeople().values());


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String left = request.getParameter("left");        DatabaseDriver dbdriver = DatabaseDriver.getInstance();

        String right = request.getParameter("right");

        Random generator = new Random();
        int i = generator.nextInt(100);

        request.setAttribute("left", left);
        request.setAttribute("right", right);

        String leftContent = "<b>"+Integer.toString(dbdriver.getPersonID(left))+"</b>";
        String rightContent = Integer.toString(dbdriver.getPersonID(right));

        request.setAttribute("leftContent", leftContent);
        request.setAttribute("rightContent", rightContent);

        request.setAttribute("factor", Integer.toString(i));
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("heldenLeft", list);

        request.setAttribute("heldenRight", list);

        //Servlet JSP communication
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/index.jsp");
        reqDispatcher.forward(request,response);
    }
}
