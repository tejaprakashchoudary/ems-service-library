package com.synectiks.library.filter.Book;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookFilterInput {
    private String bookId;
    private String batchId;
    private String departmentId;

    public String getBookId() {
        return bookId;
    }
    @JsonProperty ("bookId")
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @JsonProperty ("departmentId")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
