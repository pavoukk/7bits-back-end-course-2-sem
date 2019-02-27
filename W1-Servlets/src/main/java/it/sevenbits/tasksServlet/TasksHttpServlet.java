package it.sevenbits.tasksServlet;

import com.google.gson.Gson;
import it.sevenbits.tasksContainer.Task;
import it.sevenbits.tasksContainer.TasksContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class TasksHttpServlet extends HttpServlet {
    private TasksContainer container;

    @Override
    public void init() throws ServletException {
        super.init();
        container = TasksContainer.getTasksContainer();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String param = req.getParameter("name");
        Task task = new Task(param);
        container.addNewTask(task);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(201, "Created task");
        resp.getWriter().write(gson.toJson(task));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200, "The array of tasks");
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(container.getAllTasks()));
    }
}
