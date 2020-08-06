package com.synectiks.library.service;

import com.synectiks.library.service.dto.IssueBookDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing IssueBook.
 */
public interface IssueBookService {

    /**
     * Save a issueBook.
     *
     * @param issueBookDTO the entity to save
     * @return the persisted entity
     */
    IssueBookDTO save(IssueBookDTO issueBookDTO);

    /**
     * Get all the issueBooks.
     *
     * @return the list of entities
     */
    List<IssueBookDTO> findAll();


    /**
     * Get the "id" issueBook.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IssueBookDTO> findOne(Long id);

    /**
     * Delete the "id" issueBook.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
