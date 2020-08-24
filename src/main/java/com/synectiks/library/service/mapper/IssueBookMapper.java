package com.synectiks.library.service.mapper;


import com.synectiks.library.domain.*;
import com.synectiks.library.service.dto.IssueBookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IssueBook} and its DTO {@link IssueBookDTO}.
 */
@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface IssueBookMapper extends EntityMapper<IssueBookDTO, IssueBook> {

    @Mapping(source = "book.id", target = "bookId")
    IssueBookDTO toDto(IssueBook issueBook);

    @Mapping(source = "bookId", target = "book")
    IssueBook toEntity(IssueBookDTO issueBookDTO);

    default IssueBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        IssueBook issueBook = new IssueBook();
        issueBook.setId(id);
        return issueBook;
    }
}
