package com.synectiks.library.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.synectiks.library.business.service.BookService;
import com.synectiks.library.business.service.CommonService;
import com.synectiks.library.business.service.IssueBookService;
import com.synectiks.library.config.ApplicationProperties;
import com.synectiks.library.constant.CmsConstants;
import com.synectiks.library.domain.Batch;
import com.synectiks.library.domain.Department;
import com.synectiks.library.domain.Student;
import com.synectiks.library.domain.vo.CmsBookVo;
import com.synectiks.library.domain.vo.CmsIssueBookVo;
import com.synectiks.library.filter.Book.BookFilterInput;
import com.synectiks.library.filter.Book.BookFilterProcessor;
import com.synectiks.library.filter.IssueBook.IssueBookFilterProcessor;
import com.synectiks.library.filter.IssueBook.IssueBookListFilterInput;
import com.synectiks.library.graphql.types.Book.AddBookInput;
import com.synectiks.library.graphql.types.Book.AddBookPayload;
import com.synectiks.library.graphql.types.IssueBook.AddIssueBookInput;
import com.synectiks.library.graphql.types.IssueBook.AddIssueBookPayload;
import com.synectiks.library.repository.BookRepository;
import com.synectiks.library.repository.IssueBookRepository;
import com.synectiks.library.service.util.CommonUtil;
import com.synectiks.library.service.util.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final static Logger logger = LoggerFactory.getLogger(Mutation.class);

    @Autowired
    BookService bookService;
    @Autowired
    IssueBookService issueBookService;
    @Autowired
    CommonService commonService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private IssueBookRepository issueBookRepository;
    @Autowired
    private BookFilterProcessor bookFilterProcessor;
    @Autowired
    private IssueBookFilterProcessor issueBookFilterProcessor;
    @Autowired
    private ApplicationProperties applicationProperties;

    public AddIssueBookPayload addIssueBook(AddIssueBookInput cmsIssueBookVo) {
        CmsIssueBookVo vo = this.issueBookService.addIssueBook(cmsIssueBookVo);
        return new AddIssueBookPayload(vo);
    }
    public List<CmsIssueBookVo> getIssueBookList(IssueBookListFilterInput filter) throws Exception {
        List<CmsIssueBookVo> list = this.issueBookFilterProcessor.searchIssueBook(filter);
        List<CmsIssueBookVo> ls = new ArrayList<>();
        String prefUrl = applicationProperties.getPrefSrvUrl();
        String stUrl = applicationProperties.getStdSrvUrl();
        for(CmsIssueBookVo issueBook: list) {
            CmsIssueBookVo vo = CommonUtil.createCopyProperties(issueBook, CmsIssueBookVo.class);
            vo.setStrIssueDate(DateFormatUtil.changeLocalDateFormat(issueBook.getIssueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
            vo.setStrDueDate(DateFormatUtil.changeLocalDateFormat(issueBook.getDueDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
            vo.setStrReceivedDate(DateFormatUtil.changeLocalDateFormat(issueBook.getReceivedDate(), CmsConstants.DATE_FORMAT_dd_MM_yyyy));
            String url = prefUrl+"/api/department-by-id/"+vo.getDepartmentId();
            Department d = this.commonService.getObject(url, Department.class);
            url = prefUrl+"/api/batch-by-id/"+vo.getBatchId();
            Batch b = this.commonService.getObject(url,Batch.class);
            url = stUrl+"/api/student-by-id/"+vo.getStudentId();
            Student s = this.commonService.getObject(url,Student.class);
            vo.setBatch(b);
            vo.setDepartment(d);
            vo.setStudent(s);
            vo.setIssueDate(null);
            vo.setDueDate(null);
            vo.setReceivedDate(null);
            ls.add(vo);
        }
        logger.debug("Total issueBooks retrieved. "+list.size());
        return ls;
    }
    public AddBookPayload addBook(AddBookInput cmsBookVo) {
        CmsBookVo vo = this.bookService.addBook(cmsBookVo);
        return new AddBookPayload(vo);
    }
    public List<CmsBookVo> getBookList(BookFilterInput filter) throws Exception {
        List<CmsBookVo> list = this.bookFilterProcessor.searchBook(filter);
        List<CmsBookVo> ls = new ArrayList<>();
        String prefUrl = applicationProperties.getPrefSrvUrl();
        for(CmsBookVo lb: list) {
            CmsBookVo vo = CommonUtil.createCopyProperties(lb, CmsBookVo.class);
            String url = prefUrl+"/api/department-by-id/"+vo.getDepartmentId();
            Department d = this.commonService.getObject(url, Department.class);
            url = prefUrl+"/api/batch-by-id/"+vo.getBatchId();
            Batch b = this.commonService.getObject(url,Batch.class);
            vo.setDepartment(d);
            vo.setBatch(b);
            ls.add(vo);
        }
        logger.debug("Total books retrieved. "+list.size());
        return ls;
    }
}
