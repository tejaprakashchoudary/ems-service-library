package com.synectiks.library.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "shelf_no")
    private String shelfNo;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "edition")
    private String edition;

    @Column(name = "no_of_copies")
    private Long noOfCopies;

    @Column(name = "no_of_copies_available")
    private Long noOfCopiesAvailable;

    @Column(name = "isb_no")
    private Long isbNo;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "branch_id")
    private Long branchId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public Book shelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
        return this;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public Book bookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public Book publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEdition() {
        return edition;
    }

    public Book edition(String edition) {
        this.edition = edition;
        return this;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Long getNoOfCopies() {
        return noOfCopies;
    }

    public Book noOfCopies(Long noOfCopies) {
        this.noOfCopies = noOfCopies;
        return this;
    }

    public void setNoOfCopies(Long noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public Long getNoOfCopiesAvailable() {
        return noOfCopiesAvailable;
    }

    public Book noOfCopiesAvailable(Long noOfCopiesAvailable) {
        this.noOfCopiesAvailable = noOfCopiesAvailable;
        return this;
    }

    public void setNoOfCopiesAvailable(Long noOfCopiesAvailable) {
        this.noOfCopiesAvailable = noOfCopiesAvailable;
    }

    public Long getIsbNo() {
        return isbNo;
    }

    public Book isbNo(Long isbNo) {
        this.isbNo = isbNo;
        return this;
    }

    public void setIsbNo(Long isbNo) {
        this.isbNo = isbNo;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Book departmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public Book branchId(Long branchId) {
        this.branchId = branchId;
        return this;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", shelfNo='" + getShelfNo() + "'" +
            ", bookTitle='" + getBookTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", edition='" + getEdition() + "'" +
            ", noOfCopies=" + getNoOfCopies() +
            ", noOfCopiesAvailable=" + getNoOfCopiesAvailable() +
            ", isbNo=" + getIsbNo() +
            ", departmentId=" + getDepartmentId() +
            ", branchId=" + getBranchId() +
            "}";
    }
}
