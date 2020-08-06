package com.synectiks.library.web.rest;
import com.synectiks.library.service.IssueBookService;
import com.synectiks.library.web.rest.errors.BadRequestAlertException;
import com.synectiks.library.web.rest.util.HeaderUtil;
import com.synectiks.library.service.dto.IssueBookDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing IssueBook.
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
     * POST  /issue-books : Create a new issueBook.
     *
     * @param issueBookDTO the issueBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new issueBookDTO, or with status 400 (Bad Request) if the issueBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/issue-books")
    public ResponseEntity<IssueBookDTO> createIssueBook(@RequestBody IssueBookDTO issueBookDTO) throws URISyntaxException {
        log.debug("REST request to save IssueBook : {}", issueBookDTO);
        if (issueBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new issueBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssueBookDTO result = issueBookService.save(issueBookDTO);
        return ResponseEntity.created(new URI("/api/issue-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /issue-books : Updates an existing issueBook.
     *
     * @param issueBookDTO the issueBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated issueBookDTO,
     * or with status 400 (Bad Request) if the issueBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the issueBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/issue-books")
    public ResponseEntity<IssueBookDTO> updateIssueBook(@RequestBody IssueBookDTO issueBookDTO) throws URISyntaxException {
        log.debug("REST request to update IssueBook : {}", issueBookDTO);
        if (issueBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IssueBookDTO result = issueBookService.save(issueBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, issueBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /issue-books : get all the issueBooks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of issueBooks in body
     */
    @GetMapping("/issue-books")
    public List<IssueBookDTO> getAllIssueBooks() {
        log.debug("REST request to get all IssueBooks");
        return issueBookService.findAll();
    }

    /**
     * GET  /issue-books/:id : get the "id" issueBook.
     *
     * @param id the id of the issueBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the issueBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/issue-books/{id}")
    public ResponseEntity<IssueBookDTO> getIssueBook(@PathVariable Long id) {
        log.debug("REST request to get IssueBook : {}", id);
        Optional<IssueBookDTO> issueBookDTO = issueBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueBookDTO);
    }

    /**
     * DELETE  /issue-books/:id : delete the "id" issueBook.
     *
     * @param id the id of the issueBookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/issue-books/{id}")
    public ResponseEntity<Void> deleteIssueBook(@PathVariable Long id) {
        log.debug("REST request to delete IssueBook : {}", id);
        issueBookService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
