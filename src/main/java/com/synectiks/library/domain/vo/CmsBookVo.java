package com.synectiks.library.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.synectiks.library.domain.Branch;
import com.synectiks.library.domain.Department;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CmsBookVo extends CmsCommonVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String shelfNo;
    private String bookTitle;
    private String author;
    private String publisher;
    private String edition;
    private Long noOfCopies;
    private Long isbNo;
    private Long departmentId;
    private Long branchId;
    private Branch branch;
    private Department department;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private List<CmsBookVo> dataList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Long getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Long noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public Long getIsbNo() {
        return isbNo;
    }

    public void setIsbNo(Long isbNo) {
        this.isbNo = isbNo;
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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<CmsBookVo> getDataList() {
        return dataList;
    }

    public void setDataList(List<CmsBookVo> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "CmsBookVo{" +
            "id=" + id +
            ", shelfNo='" + shelfNo + '\'' +
            ", bookTitle='" + bookTitle + '\'' +
            ", author='" + author + '\'' +
            ", publisher='" + publisher + '\'' +
            ", edition='" + edition + '\'' +
            ", noOfCopies=" + noOfCopies +
            ", isbNo=" + isbNo +
            ", departmentId=" + departmentId +
            ", branchId=" + branchId +
            ", branch=" + branch +
            ", department=" + department +
            ", dataList=" + dataList +
            '}';
    }
}
