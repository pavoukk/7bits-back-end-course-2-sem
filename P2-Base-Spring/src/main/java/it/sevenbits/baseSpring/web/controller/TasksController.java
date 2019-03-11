package it.sevenbits.baseSpring.web.controller;

import it.sevenbits.baseSpring.core.model.Task;
import it.sevenbits.baseSpring.core.repository.ITasksRepository;
import it.sevenbits.baseSpring.web.model.AddTaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final ITasksRepository tasksRepository;

    public TasksController(ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Task> list() {
        return tasksRepository.getAllTasks();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody AddTaskRequest request) {
        Task newTask = tasksRepository.create(request.getText());
        URI location = UriComponentsBuilder.fromPath("/tasks/").path(String.valueOf(newTask.getId())).build().toUri();
        return ResponseEntity.created(location).body(newTask);
    }
}
