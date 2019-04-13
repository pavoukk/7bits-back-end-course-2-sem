package it.sevenbits.web.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddTaskRequestTest {

    @Test
    public void getText() {
        String text = "text";
        AddTaskRequest addTaskRequest = new AddTaskRequest(text);
        assertEquals(text, addTaskRequest.getText());
    }
}