package it.sevenbits.servlets.identificationServlet;

import it.sevenbits.servlets.sessions.Sessions;
import it.sevenbits.servlets.sessions.exceptions.SessionsException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IdentificationHttpServlet extends HttpServlet {
    private Sessions sessions;

    @Override
    public void init() throws ServletException {
        super.init();
        sessions = Sessions.initialize();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String id = sessions.add(name);

        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(201);
        resp.addCookie(new Cookie("id", id));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("id")) {
                try {
                    String userName = sessions.get(cookies[i].getValue());
                    resp.getWriter().write("<html><body>Current User is " + userName + "</body></html>");
                    resp.setStatus(200);
                } catch (SessionsException e) {
                    resp.sendError(404);
                }
                break;
            }
        }
    }
}
