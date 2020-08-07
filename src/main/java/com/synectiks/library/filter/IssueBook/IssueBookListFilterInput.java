package com.synectiks.library.filter.IssueBook;

public class IssueBookListFilterInput {
    private String studentId;
    private String issueBookId;
    private String departmentId;
    private String batchId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getIssueBookId() {
        return issueBookId;
    }

    public void setIssueBookId(String issueBookId) {
        this.issueBookId = issueBookId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
