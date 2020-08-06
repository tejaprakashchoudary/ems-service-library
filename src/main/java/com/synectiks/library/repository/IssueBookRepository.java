package com.synectiks.library.repository;

import com.synectiks.library.domain.IssueBook;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IssueBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueBookRepository extends JpaRepository<IssueBook, Long> {

}
