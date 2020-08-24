package com.synectiks.library.web.rest;

import com.synectiks.library.service.IssueBookService;
import com.synectiks.library.web.rest.errors.BadRequestAlertException;
import com.synectiks.library.service.dto.IssueBookDTO;

import com.synectiks.library.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.synectiks.library.domain.IssueBook}.
 */
@RestController
@RequestMapping("/api")
public class IssueBookResource {

    private final Logger log = LoggerFactory.getLogger(IssueBookResource.class);

    private static final String ENTITY_NAME = "libraryIssueBook";

    private final IssueBookService issueBookService;

    public IssueBookResource(IssueBookService issueBookService) {
        this.issueBookService = issueBookService;
    }

    /**
     * {@code POST  /issue-books} : Create a new issueBook.
     *
     * @param issueBookDTO the issueBookDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueBookDTO, or with status {@code 400 (Bad Request)} if the issueBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-books")
    public ResponseEntity<IssueBookDTO> createIssueBook(@RequestBody IssueBookDTO issueBookDTO) throws URISyntaxException {
        log.debug("REST request to save IssueBook : {}", issueBookDTO);
        if (issueBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new issueBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssueBookDTO result = issueBookService.save(issueBookDTO);
        return ResponseEntity.created(new URI("/api/issue-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert( ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issue-books} : Updates an existing issueBook.
     *
     * @param issueBookDTO the issueBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueBookDTO,
     * or with status {@code 400 (Bad Request)} if the issueBookDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-books")
    public ResponseEntity<IssueBookDTO> updateIssueBook(@RequestBody IssueBookDTO issueBookDTO) throws URISyntaxException {
        log.debug("REST request to update IssueBook : {}", issueBookDTO);
        if (issueBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IssueBookDTO result = issueBookService.save(issueBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert( ENTITY_NAME, issueBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /issue-books} : get all the issueBooks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueBooks in body.
     */
    @GetMapping("/issue-books")
    public List<IssueBookDTO> getAllIssueBooks() {
        log.debug("REST request to get all IssueBooks");
        return issueBookService.findAll();
    }

    /**
     * {@code GET  /issue-books/:id} : get the "id" issueBook.
     *
     * @param id the id of the issueBookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueBookDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-books/{id}")
    public ResponseEntity<IssueBookDTO> getIssueBook(@PathVariable Long id) {
        log.debug("REST request to get IssueBook : {}", id);
        Optional<IssueBookDTO> issueBookDTO = issueBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueBookDTO);
    }

    /**
     * {@code DELETE  /issue-books/:id} : delete the "id" issueBook.
     *
     * @param id the id of the issueBookDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-books/{id}")
    public ResponseEntity<Void> deleteIssueBook(@PathVariable Long id) {
        log.debug("REST request to delete IssueBook : {}", id);
        issueBookService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert( ENTITY_NAME, id.toString())).build();
    }
}
