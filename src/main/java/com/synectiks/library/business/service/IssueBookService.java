package com.synectiks.library.business.service;

import com.synectiks.library.config.ApplicationProperties;
import com.synectiks.library.constant.CmsConstants;
import com.synectiks.library.domain.*;
import com.synectiks.library.domain.vo.CmsBookVo;
import com.synectiks.library.domain.vo.CmsIssueBookVo;
import com.synectiks.library.filter.IssueBook.IssueBookListFilterInput;
import com.synectiks.library.graphql.types.Book.AddBookInput;
import com.synectiks.library.graphql.types.IssueBook.AddIssueBookInput;
import com.synectiks.library.repository.BookRepository;
import com.synectiks.library.repository.IssueBookRepository;
import com.synectiks.library.service.util.CommonUtil;
import com.synectiks.library.service.util.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//import com.synectiks.cms.filter.IssueBook.IssueBookListFilterInput;

@Component
public class IssueBookService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IssueBookRepository issueBookRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CommonService commonService;
    @Autowired
    private ApplicationProperties applicationProperties;

    public List<CmsIssueBookVo> searchIssueBook(Long studentId, Long issueBookId, Long departmentId, Long batchId) {
        IssueBook b = new IssueBook();
        if (studentId != null) {
            b.setStudentId(studentId);
        }
        if (issueBookId != null) {
            b.setId(issueBookId);
        }
        if (departmentId != null) {
            b.setDepartmentId(departmentId);
        }
        if (batchId != null) {
            b.setBatchId(batchId);
        }
//        if (bookStatus != null) {
//            b.set(issueBookId);
//        }
        Example<IssueBook> example = Example.of(b);
        List<IssueBook> list = this.issueBookRepository.findAll(example);
        List<CmsIssueBookVo> ls = new ArrayList<>();
        for(IssueBook issueBook: list) {
            CmsIssueBookVo vo = CommonUtil.createCopyProperties(issueBook, CmsIssueBookVo.class);
//            vo.setStrIssueDate(DateFormatUtil.changeLocalDateFormat(issueBook.getIssueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
//            vo.setStrDueDate(DateFormatUtil.changeLocalDateFormat(issueBook.getDueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
//            vo.setStrReceivedDate(DateFormatUtil.changeLocalDateFormat(issueBook.getReceivedDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
//            vo.setIssueDate(null);
//            vo.setDueDate(null);
//            vo.setReceivedDate(null);
            ls.add(vo);
        }
        return ls;
    }

    public List<CmsIssueBookVo> searchIssueBook(IssueBookListFilterInput filter) {
        IssueBook b = new IssueBook();
        if (!CommonUtil.isNullOrEmpty(filter.getStudentId())) {
            b.setStudentId(Long.parseLong(filter.getStudentId()));
        }
        if (!CommonUtil.isNullOrEmpty(filter.getIssueBookId())) {
            if (b != null) {
                b.setId(Long.valueOf(filter.getIssueBookId()));
            }
        }
        if (!CommonUtil.isNullOrEmpty(filter.getDepartmentId())) {
            b.setDepartmentId(Long.parseLong(filter.getDepartmentId()));
        }
        if (!CommonUtil.isNullOrEmpty(filter.getBatchId())) {
            b.setBatchId(Long.parseLong(filter.getBatchId()));
        }
        Example<IssueBook> example = Example.of(b);
        List<IssueBook> list = this.issueBookRepository.findAll(example);
        List<CmsIssueBookVo> ls = new ArrayList<>();
        for(IssueBook issueBook: list) {
            CmsIssueBookVo vo = CommonUtil.createCopyProperties(issueBook, CmsIssueBookVo.class);
//            vo.setStrIssueDate(DateFormatUtil.changeLocalDateFormat(issueBook.getIssueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
//            vo.setStrDueDate(DateFormatUtil.changeLocalDateFormat(issueBook.getDueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
//            vo.setStrReceivedDate(DateFormatUtil.changeLocalDateFormat(issueBook.getReceivedDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
//            vo.setIssueDate(null);
//            vo.setDueDate(null);
//            vo.setReceivedDate(null);
            ls.add(vo);
        }
        return ls;
    }

    public List<CmsIssueBookVo> getIssueBookList(){
        List<IssueBook> list = this.issueBookRepository.findAll();
        List<CmsIssueBookVo> ls = changeIssueBookToCmsIssueBookList(list);
        Collections.sort(ls, (o1, o2) -> o2.getId().compareTo(o1.getId()));
        return ls;
    }

    public CmsIssueBookVo getCmsIssueBook(Long id){
        Optional<IssueBook> b = this.issueBookRepository.findById(id);
        if(b.isPresent()) {
            CmsIssueBookVo vo = CommonUtil.createCopyProperties(b.get(), CmsIssueBookVo.class);
            convertDatesAndProvideDependencies(b.get(), vo);
            logger.debug("CmsIssueBook for given id : "+id+". CmsIssueBook object : "+vo);
            return vo;
        }
        logger.debug("issueBook object not found for the given id. "+id+". Returning null object");
        return null;
    }
    public IssueBook getIssueBook(Long id){
        Optional<IssueBook> b = this.issueBookRepository.findById(id);
        if(b.isPresent()) {
            return b.get();
        }
        logger.debug("issueBook object not found for the given id. "+id+". Returning null");
        return null;
    }
    private List<CmsIssueBookVo> changeIssueBookToCmsIssueBookList(List<IssueBook> list){
        List<CmsIssueBookVo> ls = new ArrayList<>();
        for(IssueBook b: list) {
            CmsIssueBookVo vo = CommonUtil.createCopyProperties(b, CmsIssueBookVo.class);
            String preUrl = this.applicationProperties.getPrefSrvUrl();
            String stUrl = this.applicationProperties.getStdSrvUrl();
            String baurl = preUrl+"/api/batch-by-id/"+vo.getBatchId();
            Batch batch = this.commonService.getObject(baurl,Batch.class);
//            String deurl = preUrl+"/api/department-by-id/"+vo.getDepartmentId();
//            Department department = this.commonService.getObject(deurl,Department.class);
            String stuurl = stUrl+"/api/student-by-id/"+vo.getStudentId();
            Student student = this.commonService.getObject(stuurl,Student.class);
//            vo.setDepartment(department);
            vo.setBatch(batch);
            vo.setStudent(student);
            convertDatesAndProvideDependencies(b, vo);
            ls.add(vo);
        }
        return ls;
    }

    private void convertDatesAndProvideDependencies(IssueBook b, CmsIssueBookVo vo) {
        if(b.getIssueDate() != null) {
            vo.setStrIssueDate(DateFormatUtil.changeLocalDateFormat(b.getIssueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
            vo.setIssueDate(null);
        }
        if(b.getDueDate() != null) {
            vo.setStrDueDate(DateFormatUtil.changeLocalDateFormat(b.getDueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
            vo.setDueDate(null);
        }
        if(b.getReceivedDate() != null) {
            vo.setStrReceivedDate(DateFormatUtil.changeLocalDateFormat(b.getReceivedDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
            vo.setReceivedDate(null);
        }
    }

    public CmsIssueBookVo addIssueBook(AddIssueBookInput input) {
        logger.info("Saving issueBook");
        CmsIssueBookVo vo = null;
        try {
            IssueBook issueBook = null;
            if (input.getId() == null) {
                logger.debug("Adding new IssueBook");
                issueBook = CommonUtil.createCopyProperties(input, IssueBook.class);
            } else {
                logger.debug("Updating existing IssueBook");
                issueBook = this.issueBookRepository.findById(input.getId()).get();
            }
//            Book lb = this.bookRepository.findById(input.getBookId()).get();
//            issueBook.setBook(lb);
            Optional<Book> oe = this.bookRepository.findById(input.getBookId());
            if(oe.isPresent()) {
                Book book = oe.get();
                if("ISSUED".equalsIgnoreCase(input.getBookStatus())){
                    book.setNoOfCopiesAvailable(book.getNoOfCopiesAvailable()-1);
                    book = this.bookRepository.save(book);
                }
                else if("RECEIVED".equalsIgnoreCase(input.getBookStatus())){
                    book.setNoOfCopiesAvailable(book.getNoOfCopiesAvailable()+1);
                    book = this.bookRepository.save(book);
                }
                issueBook.setBook(book);
            }
            issueBook.setBookStatus(input.getBookStatus());
            issueBook.setBatchId(input.getBatchId());
            issueBook.setDepartmentId(input.getDepartmentId());
            issueBook.setStudentId(input.getStudentId());
            issueBook.setBranchId(input.getBranchId());
            issueBook.setIssueDate(input.getStrIssueDate() != null ? DateFormatUtil.convertStringToLocalDate(input.getStrIssueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy) : null);
            issueBook.setDueDate(input.getStrDueDate() != null ? DateFormatUtil.convertStringToLocalDate(input.getStrDueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy) : null);
            if(input.getId() != null) {
                issueBook.setReceivedDate(input.getStrReceivedDate() != null ? DateFormatUtil.convertStringToLocalDate(input.getStrReceivedDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy) : null);
            }
            issueBook = this.issueBookRepository.save(issueBook);
            vo = CommonUtil.createCopyProperties(issueBook, CmsIssueBookVo.class);
            vo.setStrIssueDate(issueBook.getIssueDate() != null ? DateFormatUtil.changeLocalDateFormat(issueBook.getIssueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy) : "");
            vo.setStrDueDate(issueBook.getDueDate() != null ? DateFormatUtil.changeLocalDateFormat(issueBook.getDueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy) : "");
            vo.setStrReceivedDate(issueBook.getReceivedDate() != null ? DateFormatUtil.changeLocalDateFormat(issueBook.getReceivedDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy) : "");
            vo.setExitCode(0L);
            if (input.getId() == null) {
                vo.setExitDescription("issueBook is added successfully");
                logger.debug("issueBook is added successfully");
            } else {
                vo.setExitDescription("issueBook is updated successfully");
                logger.debug("issueBook is updated successfully");
            }

        } catch (Exception e) {
            vo = new CmsIssueBookVo();
            vo.setExitCode(1L);
            vo.setExitDescription("Due to some exception, issueBook data not be saved");
            logger.error("IssueBook save failed. Exception : ", e);
        }
        logger.info("IssueBook saved successfully");
        List ls = getIssueBookList();
        vo.setDataList(ls);
        return vo;
    }
}
