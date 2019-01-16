package com.joaera.diff.controllers;

import com.joaera.diff.enums.Side;
import com.joaera.diff.exceptions.BadRequestException;
import com.joaera.diff.models.Content;
import com.joaera.diff.models.DiffResult;
import com.joaera.diff.models.Document;
import com.joaera.diff.services.DiffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/diff/{id}")
public class DiffController {
    private final DiffService service;

    @Autowired
    public DiffController(DiffService service) {
        this.service = service;
    }

    @RequestMapping(value = "/left", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> saveLeft(@PathVariable String id, @RequestBody @Validated Content request) {
        validateContent(request);
        return new ResponseEntity<>(service.save(id, request.getData(), Side.LEFT), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/right", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveRight(@PathVariable String id, @RequestBody Content request) {
        validateContent(request);
        return new ResponseEntity<>(service.save(id, request.getData(), Side.RIGHT), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiffResult> getDiff(@PathVariable String id) {
        return new ResponseEntity<>(service.diff(id), HttpStatus.OK);
    }

    //Custom Bad Request to avoid lot of unnecessary details usually returned by spring boot
    private void validateContent(Content request) {
        if (request == null || request.getData() == null) {
            throw new BadRequestException("Data cannnot be null");
        }
    }
}
