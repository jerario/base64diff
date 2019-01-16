package com.joaera.diff.services;

import com.joaera.diff.constants.Constants;
import com.joaera.diff.enums.Side;
import com.joaera.diff.exceptions.DocumentNotFoundException;
import com.joaera.diff.models.DiffResult;
import com.joaera.diff.models.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DiffServiceTests {

    private DiffService service;
    private DocumentRepository repository;

    private String id = "1";
    private String data = "anyString";

    @Before
    public void setup() {
        this.repository = mock(DocumentRepository.class);
        when(repository.save(any(Document.class))).then(returnsFirstArg());
        this.service = new DiffService(repository);
    }

    @Test
    public void saveLeftSideInNewDocument() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        Document result = service.save(id, data, Side.LEFT);

        assertEquals(data, result.getLeft());
        assertEquals(id, result.getId());
        assertNull(result.getRight());
    }

    @Test
    public void saveRightSideInNewDocument() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        Document result = service.save(id, data, Side.RIGHT);

        assertEquals(data, result.getRight());
        assertEquals(id, result.getId());
        assertNull(result.getLeft());
    }

    @Test
    public void saveRightSideInOldDocument() {
        String right = "right";
        Document oldDocument = new Document(id);
        oldDocument.setLeft(data);
        Optional optionalDocument = Optional.of(oldDocument);

        when(repository.findById(id)).thenReturn(optionalDocument);

        Document result = service.save(id, right, Side.RIGHT);

        assertEquals(data, result.getLeft());
        assertEquals(id, result.getId());
        assertEquals(right, result.getRight());
    }

    @Test
    public void saveLeftSideInOldDocument() {
        String left = "left";
        Document oldDocument = new Document(id);
        oldDocument.setRight(data);
        Optional optionalDocument = Optional.of(oldDocument);

        when(repository.findById(id)).thenReturn(optionalDocument);

        Document result = service.save(id, left, Side.LEFT);

        assertEquals(left, result.getLeft());
        assertEquals(id, result.getId());
        assertEquals(data, result.getRight());
    }

    @Test
    public void comparingEqualData() {
        Document document = new Document(id, data, "anyString");
        Optional optionalDocument = Optional.of(document);

        when(repository.findById(id)).thenReturn(optionalDocument);

        DiffResult result = service.diff(id);

        assertEquals(Constants.EQUALS, result.getMessage());
    }

    @Test
    public void comparingDifferentLength() {
        Document document = new Document(id, "12345", "123456");
        Optional optionalDocument = Optional.of(document);

        when(repository.findById(id)).thenReturn(optionalDocument);

        DiffResult result = service.diff(id);

        assertEquals(Constants.DIFF_LENGTH, result.getMessage());
    }

    @Test
    public void comparingDifferentContent() {
        Document document = new Document(id, "02345", "12346");
        Optional optionalDocument = Optional.of(document);

        when(repository.findById(id)).thenReturn(optionalDocument);

        DiffResult result = service.diff(id);

        assertEquals(Constants.DIFF_AND_SAME_LENGTH, result.getMessage());
        assertEquals(Integer.valueOf(0), result.getDifference().getOffsets().get(0));
        assertEquals(Integer.valueOf(4), result.getDifference().getOffsets().get(1));

    }

    @Test(expected = DocumentNotFoundException.class)
    public void comparingWithoutDocument() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        service.diff(id);
    }

    @Test(expected = DocumentNotFoundException.class)
    public void comparingWithoutLeftSideOfDocument() {
        Document document = new Document(id, null, data);

        when(repository.findById(id)).thenReturn(Optional.of(document));

        service.diff(id);
    }

    @Test(expected = DocumentNotFoundException.class)
    public void comparingWithoutRightSideOfDocument() {
        Document document = new Document(id, data, null);

        when(repository.findById(id)).thenReturn(Optional.of(document));

        service.diff(id);
    }
}
