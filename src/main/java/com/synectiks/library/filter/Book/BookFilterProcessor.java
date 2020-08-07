package com.synectiks.library.filter.Book;

import com.synectiks.library.domain.vo.CmsBookVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookFilterProcessor {
    private final Logger logger = LoggerFactory.getLogger(BookFilterProcessor.class);

    @Autowired
    private com.synectiks.library.business.service.BookService BookService;

    public List<CmsBookVo> searchBook(String bookTitle, Long departmentId, Long libraryId) throws Exception {
        return BookService.searchBook(bookTitle, departmentId,libraryId);
    }

    public List<CmsBookVo> searchBook(BookFilterInput filter) throws Exception {
        return BookService.searchBook(filter);
    }
}
