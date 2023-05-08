package com.inc.admin.dao.biz;

import com.inc.admin.domain.biz.Book;
import com.inc.admin.domain.biz.BookCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookDao {
    /**
     * @param example
     * @return long
     */
    long countByExample(BookCriteria example);

    /**
     * @param example
     * @return int
     */
    int deleteByExample(BookCriteria example);

    /**
     * 根据主键删除 图书
     * @param id
     * @return int
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加 图书
     * @param record
     * @return int
     */
    int insert(Book record);

    /**
     * 插入值不为空的列 
     * @param record
     * @return int
     */
    int insertSelective(Book record);

    /**
     * @param example
     * @return java.util.List<com.inc.admin.domain.biz.Book>
     */
    List<Book> selectByExample(BookCriteria example);

    /**
     * 根据主键查询 图书
     * @param id
     * @return com.inc.admin.domain.biz.Book
     */
    Book selectByPrimaryKey(Integer id);

    /**
     * @param record
     * @param example
     * @return int
     */
    int updateByExampleSelective(@Param("record") Book record, @Param("example") BookCriteria example);

    /**
     * @param record
     * @param example
     * @return int
     */
    int updateByExample(@Param("record") Book record, @Param("example") BookCriteria example);

    /**
     * 根据主键更新值不为空的列 
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelective(Book record);

    /**
     * 根据主键更新 图书
     * @param record
     * @return int
     */
    int updateByPrimaryKey(Book record);
}