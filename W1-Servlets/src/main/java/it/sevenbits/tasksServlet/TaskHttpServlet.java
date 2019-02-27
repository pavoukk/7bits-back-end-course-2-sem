package it.sevenbits.tasksServlet;

import com.google.gson.Gson;
import it.sevenbits.tasksContainer.TasksContainer;
import it.sevenbits.tasksContainer.exceptions.TasksContainerException;

import javax.servlet.ServletException;
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
        try {
            resp.getWriter().write(gson.toJson(container.getTaskById(id)));
            resp.setStatus(200, "Task returned");
        } catch (TasksContainerException e) {
            resp.sendError(404, "Task not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        String id = req.getParameter("taskId");
        try {
            container.removeTask(id);
            resp.setStatus(200, "Deleted task id");
            resp.getWriter().write(gson.toJson(id));
        } catch (TasksContainerException e) {
            resp.sendError(404, "Task not found");
        }
    }

}
