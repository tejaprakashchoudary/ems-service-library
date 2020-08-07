package com.synectiks.library.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.synectiks.library.domain.Batch;
import com.synectiks.library.domain.Book;
import com.synectiks.library.domain.Department;
import com.synectiks.library.domain.Student;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CmsIssueBookVo extends CmsCommonVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate receivedDate;
    private  Integer noOfCopiesAvailable;
    private String bookStatus;
    private String strIssueDate;
    private String strDueDate;
    private String strReceivedDate;
    private Long studentId;
    private Long bookId;
    private Book book;
    private Long batchId;
    private Long departmentId;
    private Long branchId;
    private Department department;
    private Batch batch;
    private Student student;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private List<CmsIssueBookVo> dataList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Integer getNoOfCopiesAvailable() {
        return noOfCopiesAvailable;
    }

    public void setNoOfCopiesAvailable(Integer noOfCopiesAvailable) {
        this.noOfCopiesAvailable = noOfCopiesAvailable;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getStrIssueDate() {
        return strIssueDate;
    }

    public void setStrIssueDate(String strIssueDate) {
        this.strIssueDate = strIssueDate;
    }

    public String getStrDueDate() {
        return strDueDate;
    }

    public void setStrDueDate(String strDueDate) {
        this.strDueDate = strDueDate;
    }

    public String getStrReceivedDate() {
        return strReceivedDate;
    }

    public void setStrReceivedDate(String strReceivedDate) {
        this.strReceivedDate = strReceivedDate;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<CmsIssueBookVo> getDataList() {
        return dataList;
    }

    public void setDataList(List<CmsIssueBookVo> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "CmsIssueBookVo{" +
            "id=" + id +
            ", issueDate=" + issueDate +
            ", dueDate=" + dueDate +
            ", receivedDate=" + receivedDate +
            ", noOfCopiesAvailable=" + noOfCopiesAvailable +
            ", bookStatus='" + bookStatus + '\'' +
            ", strIssueDate='" + strIssueDate + '\'' +
            ", strDueDate='" + strDueDate + '\'' +
            ", strReceivedDate='" + strReceivedDate + '\'' +
            ", studentId=" + studentId +
            ", bookId=" + bookId +
            ", book=" + book +
            ", batchId=" + batchId +
            ", departmentId=" + departmentId +
            ", branchId=" + branchId +
            ", department=" + department +
            ", batch=" + batch +
            ", student=" + student +
            ", dataList=" + dataList +
            '}';
    }
}
