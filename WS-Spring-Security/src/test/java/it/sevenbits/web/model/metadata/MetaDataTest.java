package it.sevenbits.web.model.metadata;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.Assert.*;

public class MetaDataTest {
    private MetaData mockMetaData;

    @Before
    public void init() {
        mockMetaData = Mockito.mock(MetaData.class);
        Mockito.when(mockMetaData.getFirst())
                .thenReturn(UriComponentsBuilder
                        .fromPath("/tasks?status=done&order=desc&page=1&size=25")
                        .build()
                        .toUri());
        Mockito.when(mockMetaData.getLast())
                .thenReturn(UriComponentsBuilder
                        .fromPath("/tasks?status=done&order=desc&page=2&size=25")
                        .build()
                        .toUri());
        Mockito.when(mockMetaData.getPrev())
                .thenReturn(UriComponentsBuilder
                        .fromPath("/tasks?status=done&order=desc&page=1&size=25")
                        .build()
                        .toUri());
        Mockito.when(mockMetaData.getNext())
                .thenReturn(UriComponentsBuilder
                        .fromPath("/tasks?status=done&order=desc&page=2&size=25")
                        .build()
                        .toUri());

        Mockito.when(mockMetaData.getPage())
                .thenReturn(1);
        Mockito.when(mockMetaData.getSize())
                .thenReturn(25);
        Mockito.when(mockMetaData.getTotal())
                .thenReturn(12);
    }

    @Test
    public void getTotal() {
        assertEquals(12, mockMetaData.getTotal());
    }

    @Test
    public void getPage() {
        assertEquals(1, mockMetaData.getPage());
    }

    @Test
    public void getSize() {
        assertEquals(25, mockMetaData.getSize());
    }

    @Test
    public void getPrev() {
        assertEquals(UriComponentsBuilder
                .fromPath("/tasks?status=done&order=desc&page=1&size=25")
                .build()
                .toUri(), mockMetaData.getPrev());
    }

    @Test
    public void getNext() {
        assertEquals(UriComponentsBuilder
                .fromPath("/tasks?status=done&order=desc&page=2&size=25")
                .build()
                .toUri(), mockMetaData.getNext());
    }

    @Test
    public void getFirst() {
        assertEquals(UriComponentsBuilder
                .fromPath("/tasks?status=done&order=desc&page=1&size=25")
                .build()
                .toUri(), mockMetaData.getFirst());
    }

    @Test
    public void getLast() {
        assertEquals(UriComponentsBuilder
                .fromPath("/tasks?status=done&order=desc&page=2&size=25")
                .build()
                .toUri(), mockMetaData.getLast());
    }
}