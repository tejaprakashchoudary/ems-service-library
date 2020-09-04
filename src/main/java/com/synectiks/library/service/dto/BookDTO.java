package com.synectiks.library.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Book entity.
 */
public class BookDTO implements Serializable {

    private Long id;

    private String shelfNo;

    private String bookTitle;

    private String author;

    private String publisher;

    private String edition;

    private Long noOfCopies;

    private Long noOfCopiesAvailable;

    private Long isbNo;

    private Long batchId;

    private Long departmentId;

    private Long branchId;


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

    public Long getNoOfCopiesAvailable() {
        return noOfCopiesAvailable;
    }

    public void setNoOfCopiesAvailable(Long noOfCopiesAvailable) {
        this.noOfCopiesAvailable = noOfCopiesAvailable;
    }

    public Long getIsbNo() {
        return isbNo;
    }

    public void setIsbNo(Long isbNo) {
        this.isbNo = isbNo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookDTO bookDTO = (BookDTO) o;
        if (bookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", shelfNo='" + getShelfNo() + "'" +
            ", bookTitle='" + getBookTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", edition='" + getEdition() + "'" +
            ", noOfCopies=" + getNoOfCopies() +
            ", noOfCopiesAvailable=" + getNoOfCopiesAvailable() +
            ", isbNo=" + getIsbNo() +
            ", batchId=" + getBatchId() +
            ", departmentId=" + getDepartmentId() +
            ", branchId=" + getBranchId() +
            "}";
    }
}
