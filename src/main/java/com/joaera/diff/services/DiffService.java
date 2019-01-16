package com.joaera.diff.services;

import com.joaera.diff.constants.Constants;
import com.joaera.diff.enums.Side;
import com.joaera.diff.exceptions.DocumentNotFoundException;
import com.joaera.diff.models.DiffResult;
import com.joaera.diff.models.Difference;
import com.joaera.diff.models.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiffService {

    private final DocumentRepository repository;

    @Autowired
    public DiffService(DocumentRepository repository) {
        this.repository = repository;
    }

    public Document save(String id, String data, Side side) {
        Optional<Document> savedDocument = repository.findById(id);
        Document document = savedDocument.orElseGet(() -> new Document(id));
        if (side.equals(Side.RIGHT)) {
            document.setRight(data);
        } else {
            document.setLeft(data);
        }

        return repository.save(document);
    }

    public DiffResult diff(String id) {
        DiffResult result = new DiffResult();
        Optional<Document> savedDocument = repository.findById(id);
        if (!savedDocument.isPresent()) {
            throw new DocumentNotFoundException(String.format("Document with id: %s not found", id));
        } else {
            Document document = savedDocument.get();
            validateDocument(document);
            if (document.equalsSides()) {
                result.setMessage(Constants.EQUALS);
            } else if (!document.equalsLength()) {
                result.setMessage(Constants.DIFF_LENGTH);
            } else {
                result.setMessage(Constants.DIFF_AND_SAME_LENGTH);
                Difference difference = new Difference(document.diffSides(), document.getLeft().length());
                result.setDifference(difference);
            }
        }
        return result;
    }

    private void validateDocument(Document document) {
        if (document.getLeft() == null) {
            throw new DocumentNotFoundException("Left side of Document is missing");
        }
        if (document.getRight() == null) {
            throw new DocumentNotFoundException("Right side of Document is missing");
        }
    }

}
