package com.synectiks.library.web.rest;

import com.synectiks.library.LibraryApp;

import com.synectiks.library.domain.Book;
import com.synectiks.library.repository.BookRepository;
import com.synectiks.library.service.BookService;
import com.synectiks.library.service.dto.BookDTO;
import com.synectiks.library.service.mapper.BookMapper;
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
import java.util.List;


import static com.synectiks.library.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryApp.class)
public class BookResourceIntTest {

    private static final String DEFAULT_SHELF_NO = "AAAAAAAAAA";
    private static final String UPDATED_SHELF_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BOOK_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_BOOK_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLISHER = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHER = "BBBBBBBBBB";

    private static final String DEFAULT_EDITION = "AAAAAAAAAA";
    private static final String UPDATED_EDITION = "BBBBBBBBBB";

    private static final Long DEFAULT_NO_OF_COPIES = 1L;
    private static final Long UPDATED_NO_OF_COPIES = 2L;

    private static final Long DEFAULT_NO_OF_COPIES_AVAILABLE = 1L;
    private static final Long UPDATED_NO_OF_COPIES_AVAILABLE = 2L;

    private static final Long DEFAULT_ISB_NO = 1L;
    private static final Long UPDATED_ISB_NO = 2L;

    private static final Long DEFAULT_BATCH_ID = 1L;
    private static final Long UPDATED_BATCH_ID = 2L;

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;

    private static final Long DEFAULT_BRANCH_ID = 1L;
    private static final Long UPDATED_BRANCH_ID = 2L;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

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

    private MockMvc restBookMockMvc;

    private Book book;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookResource bookResource = new BookResource(bookService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
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
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .shelfNo(DEFAULT_SHELF_NO)
            .bookTitle(DEFAULT_BOOK_TITLE)
            .author(DEFAULT_AUTHOR)
            .publisher(DEFAULT_PUBLISHER)
            .edition(DEFAULT_EDITION)
            .noOfCopies(DEFAULT_NO_OF_COPIES)
            .noOfCopiesAvailable(DEFAULT_NO_OF_COPIES_AVAILABLE)
            .isbNo(DEFAULT_ISB_NO)
            .batchId(DEFAULT_BATCH_ID)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            .branchId(DEFAULT_BRANCH_ID);
        return book;
    }

    @Before
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getShelfNo()).isEqualTo(DEFAULT_SHELF_NO);
        assertThat(testBook.getBookTitle()).isEqualTo(DEFAULT_BOOK_TITLE);
        assertThat(testBook.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBook.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testBook.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testBook.getNoOfCopies()).isEqualTo(DEFAULT_NO_OF_COPIES);
        assertThat(testBook.getNoOfCopiesAvailable()).isEqualTo(DEFAULT_NO_OF_COPIES_AVAILABLE);
        assertThat(testBook.getIsbNo()).isEqualTo(DEFAULT_ISB_NO);
        assertThat(testBook.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testBook.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testBook.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);
        BookDTO bookDTO = bookMapper.toDto(book);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].shelfNo").value(hasItem(DEFAULT_SHELF_NO.toString())))
            .andExpect(jsonPath("$.[*].bookTitle").value(hasItem(DEFAULT_BOOK_TITLE.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
            .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.toString())))
            .andExpect(jsonPath("$.[*].noOfCopies").value(hasItem(DEFAULT_NO_OF_COPIES.intValue())))
            .andExpect(jsonPath("$.[*].noOfCopiesAvailable").value(hasItem(DEFAULT_NO_OF_COPIES_AVAILABLE.intValue())))
            .andExpect(jsonPath("$.[*].isbNo").value(hasItem(DEFAULT_ISB_NO.intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.shelfNo").value(DEFAULT_SHELF_NO.toString()))
            .andExpect(jsonPath("$.bookTitle").value(DEFAULT_BOOK_TITLE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.toString()))
            .andExpect(jsonPath("$.noOfCopies").value(DEFAULT_NO_OF_COPIES.intValue()))
            .andExpect(jsonPath("$.noOfCopiesAvailable").value(DEFAULT_NO_OF_COPIES_AVAILABLE.intValue()))
            .andExpect(jsonPath("$.isbNo").value(DEFAULT_ISB_NO.intValue()))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID.intValue()))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .shelfNo(UPDATED_SHELF_NO)
            .bookTitle(UPDATED_BOOK_TITLE)
            .author(UPDATED_AUTHOR)
            .publisher(UPDATED_PUBLISHER)
            .edition(UPDATED_EDITION)
            .noOfCopies(UPDATED_NO_OF_COPIES)
            .noOfCopiesAvailable(UPDATED_NO_OF_COPIES_AVAILABLE)
            .isbNo(UPDATED_ISB_NO)
            .batchId(UPDATED_BATCH_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .branchId(UPDATED_BRANCH_ID);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getShelfNo()).isEqualTo(UPDATED_SHELF_NO);
        assertThat(testBook.getBookTitle()).isEqualTo(UPDATED_BOOK_TITLE);
        assertThat(testBook.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBook.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBook.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testBook.getNoOfCopies()).isEqualTo(UPDATED_NO_OF_COPIES);
        assertThat(testBook.getNoOfCopiesAvailable()).isEqualTo(UPDATED_NO_OF_COPIES_AVAILABLE);
        assertThat(testBook.getIsbNo()).isEqualTo(UPDATED_ISB_NO);
        assertThat(testBook.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testBook.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testBook.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);
        book2.setId(2L);
        assertThat(book1).isNotEqualTo(book2);
        book1.setId(null);
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookDTO.class);
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        BookDTO bookDTO2 = new BookDTO();
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
        bookDTO2.setId(bookDTO1.getId());
        assertThat(bookDTO1).isEqualTo(bookDTO2);
        bookDTO2.setId(2L);
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
        bookDTO1.setId(null);
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookMapper.fromId(null)).isNull();
    }
}
