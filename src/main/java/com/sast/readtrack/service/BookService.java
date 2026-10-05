package com.sast.readtrack.service;

import com.sast.readtrack.common.Result;
import com.sast.readtrack.entity.Book;
import com.sast.readtrack.mapper.BookMapper;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private static final Integer CURRENT_USER_ID = 1;

    private final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public Result<Void> create(Book book) {

        if (book.getTitle() == null || book.getTitle().isBlank()) {
            return Result.fail("书名不能为空");
        }

        if (book.getTotalPages() == null || book.getTotalPages() <= 0) {
            return Result.fail("总页数必须大于0");
        }

        book.setReadPages(0);
        book.setStatus("UNREAD");
        book.setUserId(CURRENT_USER_ID);
        bookMapper.insert(book);
        return Result.success();
    }


    public Result<Book> detail(Integer id) {
        Book book = bookMapper.findById(id);

        if (book == null) {
            return Result.fail("书籍不存在");
        }
        if (!book.getUserId().equals(CURRENT_USER_ID)) {
            return Result.fail("无权操作该书籍");
        }
        return Result.success(book);
    }

    public Result<Void> updateProgress(Integer id, Book book) {
        Book existing = bookMapper.findById(id);

        if (existing == null) {
            return Result.fail("书籍不存在");
        }
        if (!existing.getUserId().equals(CURRENT_USER_ID)) {
            return Result.fail("无权操作该书籍");
        }

        Integer readPages = book.getReadPages();

        if (readPages == null || readPages < 0) {
            return Result.fail("已读页数不能为负");
        }
        if (readPages > existing.getTotalPages()) {
            return Result.fail("已读页数不能超过总页数");
        }

        String status;
        if (readPages == 0) {
            status = "UNREAD";
        } else if (readPages < existing.getTotalPages()) {
            status = "READING";
        } else {
            status = "READ";
        }

        existing.setReadPages(readPages);
        existing.setStatus(status);
        bookMapper.updateProgress(existing);
        return Result.success();

    }

    public Result<Void> delete(Integer id) {
        Book book = bookMapper.findById(id);

        if (book == null) {
            return Result.fail("书籍不存在");
        }
        if (!book.getUserId().equals(CURRENT_USER_ID)) {
            return Result.fail("无权操作该书籍");
        }
        bookMapper.deleteById(id);
        return Result.success();
    }
}


