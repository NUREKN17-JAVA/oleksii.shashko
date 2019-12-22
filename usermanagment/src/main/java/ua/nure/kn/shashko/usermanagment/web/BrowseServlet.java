package ua.nure.kn.shashko.usermanagment.web;

import ua.nure.kn.shashko.usermanagment.db.DaoFactory;
import ua.nure.kn.shashko.usermanagment.db.DatabaseException;
import ua.nure.kn.shashko.usermanagment.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class BrowseServlet extends HttpServlet {
    private static final String DETAILS_BUTTON = "detailsButton";
    private static final String DELETE_BUTTON = "deleteButton";
    private static final String DETAILS_JSP = "/details.jsp";
    private static final String BROWSE_JSP = "/browse.jsp";
    private static final String EDIT_BUTTON = "editButton";
    private static final String ADD_BUTTON = "addButton";
    private static final String EDIT_JSP = "/edit.jsp";
    private static final String ADD_JSP = "/add.jsp";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter(ADD_BUTTON) != null) {
            add(req, resp);
        } else if (req.getParameter(EDIT_BUTTON) != null) {
            edit(req, resp);
        } else if (req.getParameter(DELETE_BUTTON) != null) {
            delete(req, resp);
        } else if (req.getParameter(DETAILS_BUTTON) != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> users;
        try {
            users = DaoFactory.getInstance().getUserDao().findAll();
            req.getSession().setAttribute("users", users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (DatabaseException | ReflectiveOperationException e) {
            throw new ServletException(e);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {

    }
}
