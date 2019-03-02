package it.sevenbits.servlets.tasksServlets;

import com.google.gson.Gson;
import it.sevenbits.servlets.tasksContainer.Task;
import it.sevenbits.servlets.tasksContainer.TasksContainer;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TasksHttpServlet extends HttpServlet {
    private TasksContainer container;

    @Override
    public void init() throws ServletException {
        super.init();
        container = TasksContainer.getTasksContainer();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idHeader = req.getHeader("Authorization");
        if (idHeader == null || idHeader.isEmpty()) {
            resp.sendError(401);
        }

        Cookie[] cookies = req.getCookies();
        boolean isEqual = false;

        for (Cookie cooky : cookies) {
            if (cooky.getValue().equals(idHeader)) {
                isEqual = true;

                Gson gson = new Gson();
                String param = req.getParameter("name");
                Task task = new Task(param);
                container.addNewTask(task);


                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.setStatus(201, "Created task");
                resp.getWriter().write(gson.toJson(task));

                break;
            }
        }

        if (!isEqual) {
            resp.sendError(403);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idHeader = req.getHeader("Authorization");
        if (idHeader == null || idHeader.isEmpty()) {
            resp.sendError(401);
        }

        Cookie[] cookies = req.getCookies();
        boolean isEqual = false;

        for (Cookie cooky : cookies) {
            if (cooky.getValue().equals(idHeader)) {
                isEqual = true;

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.setStatus(200, "The array of tasks");
                Gson gson = new Gson();
                resp.getWriter().write(gson.toJson(container.getAllTasks()));

                break;
            }
        }

        if (!isEqual) {
            resp.sendError(403);
        }
    }
}
