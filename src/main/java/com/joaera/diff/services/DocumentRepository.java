package com.joaera.diff.services;

import com.joaera.diff.models.Document;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

//Repository for saving Documents. It's works because the h2 dependency.
@Repository
public interface DocumentRepository extends CrudRepository<Document, String> {
}
