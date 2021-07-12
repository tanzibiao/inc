package com.inc.admin.service.biz;

import com.github.pagehelper.PageInfo;
import com.inc.admin.domain.biz.Book;

import java.util.List;

/**
 * 图书Service类
 * 
 * @author tanzibiao
 * @date 2021/06/13
 *
 * @mbg.generated 2021/06/13
 */
public interface BookService {
    /**
     * 分页查询
     * @param req
     * @return 
     */
    PageInfo<Book> listByPage(Book req);

    /**
     * 获取list
     * @param req
     * @return 
     */
    List<Book> getList(Book req);

    /**
     * 单个查询
     * @param req
     * @return 
     */
    Book getOne(Book req);

    /**
     * 新增
     * @param record
     * @return 
     */
    int insert(Book record);

    /**
     * 根据主键修改该记录
     * @param record
     * @return 
     */
    int update(Book record);

    /**
     * 根据主键删除该记录
     * @param id
     * @return 
     */
    int delete(Integer id);
}