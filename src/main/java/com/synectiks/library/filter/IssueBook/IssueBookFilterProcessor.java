package com.synectiks.library.filter.IssueBook;

import com.synectiks.library.business.service.IssueBookService;
import com.synectiks.library.domain.vo.CmsIssueBookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IssueBookFilterProcessor {
    @Autowired
    private IssueBookService issueBookService;
    public List<CmsIssueBookVo> searchIssueBook(Long issueBookId, Long studentId, Long departmentId, Long batchId) {
        return issueBookService.searchIssueBook(issueBookId,studentId,departmentId,batchId);
    }
    public List<CmsIssueBookVo> searchIssueBook(IssueBookListFilterInput filter){
        return issueBookService.searchIssueBook(filter);
    }
}

//
