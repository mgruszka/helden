package pl.protka.presentation;


import pl.protka.britanica.BritanicaContentManager;
import pl.protka.db.DatabaseDriver;
import pl.protka.db.PersonEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gruszek on 02.05.15.
 */

@WebServlet(name = "helden")
public class MainServlet extends HttpServlet {
    static final DatabaseDriver dbdriver = DatabaseDriver.getInstance();
    static List<String> list = new ArrayList<>(dbdriver.getPeople().values());


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String right = request.getParameter("right");
        String left = request.getParameter("left");

        PersonEntity leftPerson = dbdriver.getPersonEntity(left);
        PersonEntity rightPerson = dbdriver.getPersonEntity(right);

        request.setAttribute("left", left);
        request.setAttribute("right", right);

        String leftContent = contentBuilder(leftPerson);
        String rightContent = contentBuilder(rightPerson);

        request.setAttribute("leftContent", leftContent);
        request.setAttribute("rightContent", rightContent);

        CorrelationCounter cc = new CorrelationCounter();
        int factor = cc.correlate(right, left);

        request.setAttribute("factor", "0");
        doGet(request, response);
    }

    private String contentBuilder(PersonEntity person) {
        BritanicaContentManager bcm = new BritanicaContentManager();
        String output = "<table><tr><td>Name</td><td>";
        output += person.getName();
        if(person.getBirthDate() != null && !person.getBirthDate().isEmpty())
            output += "</td></tr><tr><td>Birth Date</td><td>"+person.getBirthDate();
        if(person.getBirthPlace() != null && !person.getBirthPlace().isEmpty())
            output += "</td></tr><tr><td>Birth Place</td><td>"+person.getBirthPlace();
        if(person.getDeathDate() != null && !person.getDeathDate().isEmpty())
            output += "</td></tr><tr><td>Death date</td><td>"+person.getDeathDate();
        if(person.getDeathPlace() != null && !person.getDeathPlace().isEmpty())
            output += "</td></tr><tr><td>Death Place</td><td>"+person.getDeathPlace();
        if(person.getFields() != null && !person.getFields().isEmpty())
            output += "</td></tr><tr><td>Fields</td><td>"+person.getFields();
        output += "</td></tr></table>";
        try {
            output += bcm.getPersonContent(person.getName(), person.getBritURL());
        } catch (IOException e) {
            //Gotcha!
            System.err.println("unable to get content from britannica");
        }
        return output;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("heldenLeft", list);

        request.setAttribute("heldenRight", list);

        //Servlet JSP communication
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/index.jsp");
        reqDispatcher.forward(request, response);
    }
}
