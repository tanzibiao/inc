package com.inc.admin.dao.biz;

import com.github.pagehelper.Page;
import com.inc.admin.domain.biz.Book;
import com.inc.admin.domain.biz.BookCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 图书Dao类
 * 
 * @author tanzibiao
 * @date 2021/06/13
 *
 * @mbg.generated 2021/06/13
 */
@Mapper
public interface BookDao {
    /**
     * countByCriteria
     * @param example
     * @return 
     */
    long countByCriteria(BookCriteria example);

    /**
     * deleteByCriteria
     * @param example
     * @return 
     */
    int deleteByCriteria(BookCriteria example);

    /**
     * deleteByPrimaryKey
     * @param id
     * @return 
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert
     * @param record
     * @return 
     */
    int insert(Book record);

    /**
     * insertSelective
     * @param record
     * @return 
     */
    int insertSelective(Book record);

    /**
     * selectByCriteria
     * @param example
     * @return 
     */
    Page<Book> selectByCriteria(BookCriteria example);

    /**
     * selectByPrimaryKey
     * @param id
     * @return 
     */
    Book selectByPrimaryKey(Integer id);

    /**
     * updateByCriteriaSelective
     * @param record
     * @param example
     * @return 
     */
    int updateByCriteriaSelective(@Param("record") Book record, @Param("example") BookCriteria example);

    /**
     * updateByCriteria
     * @param record
     * @param example
     * @return 
     */
    int updateByCriteria(@Param("record") Book record, @Param("example") BookCriteria example);

    /**
     * updateByPrimaryKeySelective
     * @param record
     * @return 
     */
    int updateByPrimaryKeySelective(Book record);

    /**
     * updateByPrimaryKey
     * @param record
     * @return 
     */
    int updateByPrimaryKey(Book record);
}