package com.inc.admin.service.biz;

import com.github.pagehelper.PageInfo;
import com.inc.admin.domain.biz.Book;
import java.util.List;

/**
 * 图书管理 service
 * @author tanzibiao
 * @date 2021-12-11 01:34:36
*/
public interface BookService {
    /**
     * 分页查询
     */
    PageInfo<Book> listByPage(Book req);

    /**
     * 查询列表
     */
    List<Book> getList(Book req);

    /**
     * 单个查询
     */
    Book getOne(Book req);

    /**
     * 新增
     */
    int insert(Book req);

    /**
     * 根据主键修改
     */
    int update(Book req);

    /**
     * 根据主键删除
     */
    int delete(Integer id);
}