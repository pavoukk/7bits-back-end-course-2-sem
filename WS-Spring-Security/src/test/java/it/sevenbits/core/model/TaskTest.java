package it.sevenbits.core.model;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void get() {
        String id = UUID.randomUUID().toString();
        String text = "text";
        String status = "inbox";
        String createdAt = "createdAt";
        String updatedAt = "updatedAt";
        String owner = "owner";
        Task task = new Task(id, text, status, createdAt, updatedAt, owner);

        assertEquals(id, task.getId());
        assertEquals(status, task.getStatus());
        assertEquals(text, task.getText());
        assertEquals(createdAt, task.getCreatedAt());
        assertEquals(updatedAt, task.getUpdatedAt());
    }
}