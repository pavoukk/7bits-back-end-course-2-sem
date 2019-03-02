package it.sevenbits.servlets.tasksServlets;

import com.google.gson.Gson;
import it.sevenbits.servlets.tasksContainer.TasksContainer;
import it.sevenbits.servlets.tasksContainer.exceptions.TasksContainerException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TaskHttpServlet extends HttpServlet {
    private TasksContainer container;

    @Override
    public void init() throws ServletException {
        super.init();
        container = TasksContainer.getTasksContainer();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        String id = req.getParameter("taskId");
        String idHeader = req.getHeader("Authorization");
        if (idHeader == null || idHeader.isEmpty()) {
            resp.sendError(401);
        }

        Cookie[] cookies = req.getCookies();
        boolean isEqual = false;

        for (Cookie cooky : cookies) {
            if (cooky.getValue().equals(idHeader)) {
                isEqual = true;

                try {
                    resp.getWriter().write(gson.toJson(container.getTaskById(id)));
                    resp.setStatus(200, "Task returned");
                } catch (TasksContainerException e) {
                    resp.sendError(404, "Task not found");
                }
                break;
            }
        }

        if (!isEqual) {
            resp.sendError(403);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        String id = req.getParameter("taskId");
        String idHeader = req.getHeader("Authorization");
        if (idHeader == null || idHeader.isEmpty()) {
            resp.sendError(401);
        }

        Cookie[] cookies = req.getCookies();
        boolean isEqual = false;

        for (Cookie cooky : cookies) {
            if (cooky.getValue().equals(idHeader)) {
                isEqual = true;

                try {
                    container.removeTask(id);
                    resp.setStatus(200, "Deleted task id");
                    resp.getWriter().write(gson.toJson(id));
                } catch (TasksContainerException e) {
                    resp.sendError(404, "Task not found");
                }
                break;
            }
        }

        if (!isEqual) {
            resp.sendError(403);
        }
    }

}
