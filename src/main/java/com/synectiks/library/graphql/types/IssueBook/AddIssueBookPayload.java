package com.synectiks.library.graphql.types.IssueBook;

import com.synectiks.library.domain.vo.CmsIssueBookVo;

public class AddIssueBookPayload {

    private final CmsIssueBookVo cmsIssueBookVo;

    public AddIssueBookPayload(CmsIssueBookVo cmsIssueBookVo) {
        this.cmsIssueBookVo = cmsIssueBookVo;
    }

    public CmsIssueBookVo getCmsIssueBookVo() {
        return cmsIssueBookVo;
    }
}
