package com.synectiks.library.web.rest;

import com.synectiks.library.LibraryApp;

import com.synectiks.library.domain.IssueBook;
import com.synectiks.library.repository.IssueBookRepository;
import com.synectiks.library.service.IssueBookService;
import com.synectiks.library.service.dto.IssueBookDTO;
import com.synectiks.library.service.mapper.IssueBookMapper;
import com.synectiks.library.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.synectiks.library.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IssueBookResource REST controller.
 *
 * @see IssueBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryApp.class)
public class IssueBookResourceIntTest {

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECEIVED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NO_OF_COPIES_AVAILABLE = 1;
    private static final Integer UPDATED_NO_OF_COPIES_AVAILABLE = 2;

    private static final String DEFAULT_BOOK_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_BOOK_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_STUDENT_ID = 1L;
    private static final Long UPDATED_STUDENT_ID = 2L;

    private static final Long DEFAULT_BATCH_ID = 1L;
    private static final Long UPDATED_BATCH_ID = 2L;

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;

    private static final Long DEFAULT_BRANCH_ID = 1L;
    private static final Long UPDATED_BRANCH_ID = 2L;

    @Autowired
    private IssueBookRepository issueBookRepository;

    @Autowired
    private IssueBookMapper issueBookMapper;

    @Autowired
    private IssueBookService issueBookService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIssueBookMockMvc;

    private IssueBook issueBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IssueBookResource issueBookResource = new IssueBookResource(issueBookService);
        this.restIssueBookMockMvc = MockMvcBuilders.standaloneSetup(issueBookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueBook createEntity(EntityManager em) {
        IssueBook issueBook = new IssueBook()
            .issueDate(DEFAULT_ISSUE_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .receivedDate(DEFAULT_RECEIVED_DATE)
            .noOfCopiesAvailable(DEFAULT_NO_OF_COPIES_AVAILABLE)
            .bookStatus(DEFAULT_BOOK_STATUS)
            .studentId(DEFAULT_STUDENT_ID)
            .batchId(DEFAULT_BATCH_ID)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            .branchId(DEFAULT_BRANCH_ID);
        return issueBook;
    }

    @Before
    public void initTest() {
        issueBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssueBook() throws Exception {
        int databaseSizeBeforeCreate = issueBookRepository.findAll().size();

        // Create the IssueBook
        IssueBookDTO issueBookDTO = issueBookMapper.toDto(issueBook);
        restIssueBookMockMvc.perform(post("/api/issue-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issueBookDTO)))
            .andExpect(status().isCreated());

        // Validate the IssueBook in the database
        List<IssueBook> issueBookList = issueBookRepository.findAll();
        assertThat(issueBookList).hasSize(databaseSizeBeforeCreate + 1);
        IssueBook testIssueBook = issueBookList.get(issueBookList.size() - 1);
        assertThat(testIssueBook.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testIssueBook.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testIssueBook.getReceivedDate()).isEqualTo(DEFAULT_RECEIVED_DATE);
        assertThat(testIssueBook.getNoOfCopiesAvailable()).isEqualTo(DEFAULT_NO_OF_COPIES_AVAILABLE);
        assertThat(testIssueBook.getBookStatus()).isEqualTo(DEFAULT_BOOK_STATUS);
        assertThat(testIssueBook.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testIssueBook.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testIssueBook.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testIssueBook.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
    }

    @Test
    @Transactional
    public void createIssueBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issueBookRepository.findAll().size();

        // Create the IssueBook with an existing ID
        issueBook.setId(1L);
        IssueBookDTO issueBookDTO = issueBookMapper.toDto(issueBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssueBookMockMvc.perform(post("/api/issue-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issueBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueBook in the database
        List<IssueBook> issueBookList = issueBookRepository.findAll();
        assertThat(issueBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIssueBooks() throws Exception {
        // Initialize the database
        issueBookRepository.saveAndFlush(issueBook);

        // Get all the issueBookList
        restIssueBookMockMvc.perform(get("/api/issue-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issueBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(DEFAULT_RECEIVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].noOfCopiesAvailable").value(hasItem(DEFAULT_NO_OF_COPIES_AVAILABLE)))
            .andExpect(jsonPath("$.[*].bookStatus").value(hasItem(DEFAULT_BOOK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getIssueBook() throws Exception {
        // Initialize the database
        issueBookRepository.saveAndFlush(issueBook);

        // Get the issueBook
        restIssueBookMockMvc.perform(get("/api/issue-books/{id}", issueBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(issueBook.getId().intValue()))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.receivedDate").value(DEFAULT_RECEIVED_DATE.toString()))
            .andExpect(jsonPath("$.noOfCopiesAvailable").value(DEFAULT_NO_OF_COPIES_AVAILABLE))
            .andExpect(jsonPath("$.bookStatus").value(DEFAULT_BOOK_STATUS.toString()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID.intValue()))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID.intValue()))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIssueBook() throws Exception {
        // Get the issueBook
        restIssueBookMockMvc.perform(get("/api/issue-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssueBook() throws Exception {
        // Initialize the database
        issueBookRepository.saveAndFlush(issueBook);

        int databaseSizeBeforeUpdate = issueBookRepository.findAll().size();

        // Update the issueBook
        IssueBook updatedIssueBook = issueBookRepository.findById(issueBook.getId()).get();
        // Disconnect from session so that the updates on updatedIssueBook are not directly saved in db
        em.detach(updatedIssueBook);
        updatedIssueBook
            .issueDate(UPDATED_ISSUE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .receivedDate(UPDATED_RECEIVED_DATE)
            .noOfCopiesAvailable(UPDATED_NO_OF_COPIES_AVAILABLE)
            .bookStatus(UPDATED_BOOK_STATUS)
            .studentId(UPDATED_STUDENT_ID)
            .batchId(UPDATED_BATCH_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .branchId(UPDATED_BRANCH_ID);
        IssueBookDTO issueBookDTO = issueBookMapper.toDto(updatedIssueBook);

        restIssueBookMockMvc.perform(put("/api/issue-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issueBookDTO)))
            .andExpect(status().isOk());

        // Validate the IssueBook in the database
        List<IssueBook> issueBookList = issueBookRepository.findAll();
        assertThat(issueBookList).hasSize(databaseSizeBeforeUpdate);
        IssueBook testIssueBook = issueBookList.get(issueBookList.size() - 1);
        assertThat(testIssueBook.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testIssueBook.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testIssueBook.getReceivedDate()).isEqualTo(UPDATED_RECEIVED_DATE);
        assertThat(testIssueBook.getNoOfCopiesAvailable()).isEqualTo(UPDATED_NO_OF_COPIES_AVAILABLE);
        assertThat(testIssueBook.getBookStatus()).isEqualTo(UPDATED_BOOK_STATUS);
        assertThat(testIssueBook.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testIssueBook.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testIssueBook.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testIssueBook.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingIssueBook() throws Exception {
        int databaseSizeBeforeUpdate = issueBookRepository.findAll().size();

        // Create the IssueBook
        IssueBookDTO issueBookDTO = issueBookMapper.toDto(issueBook);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueBookMockMvc.perform(put("/api/issue-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issueBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueBook in the database
        List<IssueBook> issueBookList = issueBookRepository.findAll();
        assertThat(issueBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssueBook() throws Exception {
        // Initialize the database
        issueBookRepository.saveAndFlush(issueBook);

        int databaseSizeBeforeDelete = issueBookRepository.findAll().size();

        // Delete the issueBook
        restIssueBookMockMvc.perform(delete("/api/issue-books/{id}", issueBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IssueBook> issueBookList = issueBookRepository.findAll();
        assertThat(issueBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueBook.class);
        IssueBook issueBook1 = new IssueBook();
        issueBook1.setId(1L);
        IssueBook issueBook2 = new IssueBook();
        issueBook2.setId(issueBook1.getId());
        assertThat(issueBook1).isEqualTo(issueBook2);
        issueBook2.setId(2L);
        assertThat(issueBook1).isNotEqualTo(issueBook2);
        issueBook1.setId(null);
        assertThat(issueBook1).isNotEqualTo(issueBook2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueBookDTO.class);
        IssueBookDTO issueBookDTO1 = new IssueBookDTO();
        issueBookDTO1.setId(1L);
        IssueBookDTO issueBookDTO2 = new IssueBookDTO();
        assertThat(issueBookDTO1).isNotEqualTo(issueBookDTO2);
        issueBookDTO2.setId(issueBookDTO1.getId());
        assertThat(issueBookDTO1).isEqualTo(issueBookDTO2);
        issueBookDTO2.setId(2L);
        assertThat(issueBookDTO1).isNotEqualTo(issueBookDTO2);
        issueBookDTO1.setId(null);
        assertThat(issueBookDTO1).isNotEqualTo(issueBookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(issueBookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(issueBookMapper.fromId(null)).isNull();
    }
}
