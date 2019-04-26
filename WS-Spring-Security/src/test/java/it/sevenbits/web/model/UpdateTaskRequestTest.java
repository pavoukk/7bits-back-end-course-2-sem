package it.sevenbits.web.model;

import it.sevenbits.web.model.tasks.request.UpdateTaskRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdateTaskRequestTest {

    @Test
    public void getTextGetStatus() {
        String text = "text";
        String status = "inbox";
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest(text, status);
        assertEquals(text, updateTaskRequest.getText());
        assertEquals(status, updateTaskRequest.getStatus());
    }


}