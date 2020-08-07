package com.synectiks.library.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.collect.Lists;
import com.synectiks.library.business.service.BookService;
import com.synectiks.library.business.service.CommonService;
import com.synectiks.library.business.service.IssueBookService;
import com.synectiks.library.config.ApplicationProperties;
import com.synectiks.library.domain.Batch;
import com.synectiks.library.domain.Department;
import com.synectiks.library.domain.Student;
import com.synectiks.library.domain.vo.CmsBookVo;
import com.synectiks.library.domain.vo.CmsIssueBookVo;
import com.synectiks.library.domain.vo.LibraryFilterDataCache;
import com.synectiks.library.filter.Book.BookFilterProcessor;
import com.synectiks.library.filter.IssueBook.IssueBookFilterProcessor;
import com.synectiks.library.repository.BookRepository;
import com.synectiks.library.repository.IssueBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private final static Logger logger = LoggerFactory.getLogger(Query.class);

    private final IssueBookRepository issueBookRepository;
    @Autowired
    private IssueBookFilterProcessor issueBookFilterProcessor;

    @Autowired
    private IssueBookService issueBookService;

    private final BookRepository bookRepository;

    @Autowired
    private BookFilterProcessor bookFilterProcessor;

    @Autowired
    private BookService bookService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ApplicationProperties applicationProperties;


    public Query(IssueBookRepository issueBookRepository, BookRepository bookRepository) {
        this.issueBookRepository = issueBookRepository;
        this.bookRepository = bookRepository;
    }
    public List<CmsBookVo> getBookList() throws Exception {
        logger.debug("Query - getBookList :");
        return this.bookService.getBookList();
    }

    public List<CmsIssueBookVo> getIssueBookList() throws Exception {
        logger.debug("Query - getIssueBookList :");
        return this.issueBookService.getIssueBookList();
    }
    public List<CmsIssueBookVo>searchIssueBook(Long bookId, Long studentId,Long departmentId,Long batchId) throws Exception{
        return Lists.newArrayList(issueBookFilterProcessor.searchIssueBook(bookId,studentId,departmentId,batchId));
    }

    public List<CmsBookVo>searchBook(String bookTitle, Long departmentId, Long libraryId ) throws Exception {
        return  Lists.newArrayList(bookFilterProcessor.searchBook(bookTitle, departmentId,libraryId));
    }
    public LibraryFilterDataCache createLibraryDataCache() throws Exception{
        String preUrl = this.applicationProperties.getPrefSrvUrl();
        String stUrl = this.applicationProperties.getStdSrvUrl();
        String baurl = preUrl+"/api/batch-by-filters/";
        Batch[] batchList = this.commonService.getObject(baurl,Batch[].class);
        String deurl = preUrl+"/api/department-by-filters/";
        Department[] departmentList = this.commonService.getObject(deurl,Department[].class);
        List<CmsIssueBookVo> issueBookList = this.issueBookService.getIssueBookList();
        String stuurl = stUrl+"/api/student-by-filters/";
        Student[] studentList = this.commonService.getObject(stuurl,Student[].class);
        List<CmsBookVo> bookList = this.bookService.getBookList();
        LibraryFilterDataCache cache = new LibraryFilterDataCache();
        cache.setBatches(Arrays.asList(batchList));
        cache.setDepartments(Arrays.asList(departmentList));
        cache.setBooks(bookList);
        cache.setIssueBooks(issueBookList);
        cache.setStudents(Arrays.asList(studentList));
        return cache;
    }

}
