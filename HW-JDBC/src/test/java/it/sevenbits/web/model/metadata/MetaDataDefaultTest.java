package it.sevenbits.web.model.metadata;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MetaDataDefaultTest {
    private MetaDataDefault mockMetaDataDefault;

    @Before
    public void init() {
        mockMetaDataDefault = Mockito.mock(MetaDataDefault.class);
        Mockito.when(mockMetaDataDefault.getMinPage())
                .thenReturn(1);
        Mockito.when(mockMetaDataDefault.getMinSize())
                .thenReturn(10);
        Mockito.when(mockMetaDataDefault.getMaxSize())
                .thenReturn(50);
        Mockito.when(mockMetaDataDefault.getPage())
                .thenReturn(1);
        Mockito.when(mockMetaDataDefault.getOrder())
                .thenReturn("desc");
        Mockito.when(mockMetaDataDefault.getSize())
                .thenReturn(25);
        Mockito.when(mockMetaDataDefault.getStatus())
                .thenReturn("inbox");

        Mockito.when(mockMetaDataDefault.getQueryMainPath())
                .thenReturn("/tasks");
        Mockito.when(mockMetaDataDefault.getQueryOrder())
                .thenReturn("order");
        Mockito.when(mockMetaDataDefault.getQueryPage())
                .thenReturn("page");
        Mockito.when(mockMetaDataDefault.getQuerySize())
                .thenReturn("size");
        Mockito.when(mockMetaDataDefault.getQueryStatus())
                .thenReturn("status");
    }

    @Test
    public void getStatus() {
        assertEquals("inbox", mockMetaDataDefault.getStatus());
    }

    @Test
    public void getOrder() {
        assertEquals("desc", mockMetaDataDefault.getOrder());
    }

    @Test
    public void getPage() {
        assertEquals(1, mockMetaDataDefault.getPage());
    }

    @Test
    public void getSize() {
        assertEquals(25, mockMetaDataDefault.getSize());
    }

    @Test
    public void getMinSize() {
        assertEquals(10, mockMetaDataDefault.getMinSize());
    }

    @Test
    public void getMaxSize() {
        assertEquals(50, mockMetaDataDefault.getMaxSize());
    }

    @Test
    public void getMinPage() {
        assertEquals(1, mockMetaDataDefault.getMinPage());
    }

    @Test
    public void getQueryStatus() {
        assertEquals("status", mockMetaDataDefault.getQueryStatus());
    }

    @Test
    public void getQueryOrder() {
        assertEquals("order", mockMetaDataDefault.getQueryOrder());
    }

    @Test
    public void getQueryPage() {
        assertEquals("page", mockMetaDataDefault.getQueryPage());
    }

    @Test
    public void getQuerySize() {
        assertEquals("size", mockMetaDataDefault.getQuerySize());
    }

    @Test
    public void getQueryMainPath() {
        assertEquals("/tasks", mockMetaDataDefault.getQueryMainPath());
    }
}