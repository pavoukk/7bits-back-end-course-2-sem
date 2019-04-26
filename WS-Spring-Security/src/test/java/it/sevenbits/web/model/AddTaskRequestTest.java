package it.sevenbits.web.model;

import it.sevenbits.web.model.tasks.request.AddTaskRequest;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AddTaskRequestTest {

    @Test
    public void getText() {
        String text = "text";
        AddTaskRequest addTaskRequest = new AddTaskRequest(text);
        assertEquals(text, addTaskRequest.getText());
    }
}