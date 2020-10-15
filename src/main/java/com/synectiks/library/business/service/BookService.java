package com.synectiks.library.business.service;

import com.synectiks.library.config.ApplicationProperties;
import com.synectiks.library.domain.Book;
import com.synectiks.library.domain.Department;
import com.synectiks.library.domain.vo.CmsBookVo;
import com.synectiks.library.filter.Book.BookFilterInput;
import com.synectiks.library.graphql.types.Book.AddBookInput;
import com.synectiks.library.repository.BookRepository;
import com.synectiks.library.service.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//import com.synectiks.cms.filter.library.LibraryFilterInput;

@Service
@Transactional
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ApplicationProperties applicationProperties;

    public List<CmsBookVo> searchBook(String bookTitle, Long departmentId, Long bookId) {
        Book bk = new Book();

        if (bookTitle != null) {
            bk.setBookTitle(bookTitle);
        }
        if (departmentId != null) {
            bk.setId(departmentId);
        }
        if (bookId != null) {
            bk.setId(bookId);
        }
        Example<Book> example = Example.of(bk);
        List<Book> list = this.bookRepository.findAll(example);
        List<CmsBookVo> ls = new ArrayList<>();
        for(Book book: list) {
            CmsBookVo vo = CommonUtil.createCopyProperties(book, CmsBookVo.class);
            String url = this.applicationProperties.getPrefSrvUrl()+"/department-by-id/"+vo.getDepartmentId();
            Department d = this.commonService.getObject(url, Department.class);
            vo.setDepartment(d);
            ls.add(vo);
        }
        return ls;
    }

    public List<CmsBookVo> searchBook(BookFilterInput filter) {
        Book bk = new Book();
        if (!CommonUtil.isNullOrEmpty(filter.getBookId())) {
            if (bk != null) {
                bk.setId(Long.valueOf(filter.getBookId()));
            }
        }
        if (!CommonUtil.isNullOrEmpty(filter.getDepartmentId())) {
            bk.setDepartmentId(Long.parseLong(filter.getDepartmentId()));
        }
        Example<Book> example = Example.of(bk);
        List<Book> list = this.bookRepository.findAll(example);
        List<CmsBookVo> ls = new ArrayList<>();
        String prefUrl = applicationProperties.getPrefSrvUrl();
        for(Book book: list) {
            CmsBookVo vo = CommonUtil.createCopyProperties(book, CmsBookVo.class);
            String url = prefUrl+"/api/department-by-id/"+vo.getDepartmentId();
            Department d = this.commonService.getObject(url, Department.class);
            vo.setDepartment(d);
            ls.add(vo);
        }
        return ls;
    }
    public List<CmsBookVo> getBookList(){
        List<Book> list = this.bookRepository.findAll();
        List<CmsBookVo> ls = changeBookToCmsBookList(list);
        logger.debug("Book list : "+list);
        return ls;
    }

    public Book getBook(Long id){
        Optional<Book> bk = this.bookRepository.findById(id);
        if(bk.isPresent()) {
            logger.debug("Book object found for given id : "+id+". Book object : "+bk.get());
            return bk.get();
        }
        logger.debug("Book object not found for the given id. "+id+". Returning null");
        return null;
    }

    public List<CmsBookVo> getCmsBookList(){
        List<Book> list = this.bookRepository.findAll();
        List<CmsBookVo> ls = changeBookToCmsBookList(list);
        Collections.sort(ls, (o1, o2) -> o2.getId().compareTo(o1.getId()));
        logger.debug("CmsBook list : "+list);
        return ls;
    }

    private List<CmsBookVo> changeBookToCmsBookList(List<Book> list) {
        List<CmsBookVo> ls = new ArrayList<>();
        for (Book bk : list) {
            CmsBookVo vo = CommonUtil.createCopyProperties(bk, CmsBookVo.class);
            ls.add(vo);
        }
        return ls;
    }

    public CmsBookVo getCmsBook(Long id){
        Optional<Book> bk = this.bookRepository.findById(id);
        if(bk.isPresent()) {
            CmsBookVo vo = CommonUtil.createCopyProperties(bk.get(), CmsBookVo.class);
            logger.debug("CmsBook for given id : "+id+". CmsBook object : "+vo);
            return vo;
        }
        logger.debug("Book object not found for the given id. "+id+". Returning null object");
        return null;
    }


    public CmsBookVo addBook(AddBookInput input) {
        logger.info("Saving book");
        CmsBookVo vo = null;
        try {
            Book book = null;
            if (input.getId() == null) {
                logger.debug("Adding new Book");
                book = CommonUtil.createCopyProperties(input, Book.class);
            } else {
                logger.debug("Updating existing Book");
                book = this.bookRepository.findById(input.getId()).get();
            }
            book.setShelfNo(input.getShelfNo());
            book.setBookTitle(input.getBookTitle());
            book.setAuthor(input.getAuthor());
            book.setPublisher(input.getPublisher());
            book.setEdition(input.getEdition());
            book.setNoOfCopies(input.getNoOfCopies());
            book.setNoOfCopiesAvailable(input.getNoOfCopiesAvailable());
            book.setIsbNo(input.getIsbNo());
            book.setDepartmentId(input.getDepartmentId());
            book.setBatchId(input.getBatchId());
            book.setBranchId(input.getBranchId());
            book = this.bookRepository.save(book);
            vo = CommonUtil.createCopyProperties(book, CmsBookVo.class);
            vo.setCreatedOn(null);
            vo.setUpdatedOn(null);
            vo.setExitCode(0L);
            if (input.getId() == null) {
                vo.setExitDescription("book is added successfully");
                logger.debug("book is added successfully");
            } else {
                vo.setExitDescription("book is updated successfully");
                logger.debug("book is updated successfully");
            }

        } catch (Exception e) {
            vo = new CmsBookVo();
            vo.setExitCode(1L);
            vo.setExitDescription("Due to some exception, book data not be saved");
            logger.error("Book save failed. Exception : ", e);
        }
        logger.info("Book saved successfully");
        List ls = getBookList();
        vo.setDataList(ls);
        return vo;
    }
}
