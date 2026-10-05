package com.sast.readtrack.mapper;


import com.sast.readtrack.entity.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BookMapper {
    @Insert("INSERT INTO books (title, author, total_pages, read_pages, status, user_id) VALUES (#{title}, #{author}, #{totalPages}, #{readPages}, #{status}, #{userId})")
    void insert(Book book);

    @Select("SELECT * FROM books WHERE id = #{id}")
    Book findById(Integer id);

    @Update("UPDATE books SET read_pages = #{readPages}, status = #{status} WHERE id = #{id}")
    void updateProgress(Book book);

    @Delete("DELETE FROM books WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("SELECT * FROM books WHERE user_id = #{userId} ORDER BY created_at DESC LIMIT #{offset}, #{size}")
    List<Book> findByPage(@Param("userId") Integer userId,
                          @Param("offset") Integer offset,
                          @Param("size") Integer size);

    @Select("SELECT COUNT(*) FROM books WHERE user_id = #{userId}")
    int countByUserId(Integer userId);

}
