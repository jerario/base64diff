package com.joaera.diff.controllers;

import com.joaera.diff.enums.Side;
import com.joaera.diff.exceptions.BadRequestException;
import com.joaera.diff.models.Content;
import com.joaera.diff.models.DiffResult;
import com.joaera.diff.models.Document;
import com.joaera.diff.services.DiffService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiffControllerTests {

    private DiffController controller;
    private java.lang.String id = "id1";
    private Content data = new Content("anyString");
    private DiffService diffService;

    @Before
    public void setup(){
        this.diffService = mock(DiffService.class);
        when(diffService.save(eq(id),anyString(),any(Side.class))).thenReturn(new Document(id));
        when(diffService.diff(eq(id))).thenReturn(new DiffResult());
        controller = new DiffController(diffService);
    }

    @Test
    public void saveLeftInput() {
        HttpStatus status = controller.saveLeft(id, data).getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    public void saveRightInput() {
        HttpStatus status = controller.saveRight(id, data).getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }

    @Test(expected = BadRequestException.class)
    public void saveLeftInputWithNullData() {
        HttpStatus status = controller.saveLeft(id, null).getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }

    @Test(expected = BadRequestException.class)
    public void saveRightInputWithNullData() {
        HttpStatus status = controller.saveRight(id, null).getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    public void getDiff() {
        HttpStatus status = controller.getDiff(id).getStatusCode();

        assertEquals(HttpStatus.OK, status);
    }
}
