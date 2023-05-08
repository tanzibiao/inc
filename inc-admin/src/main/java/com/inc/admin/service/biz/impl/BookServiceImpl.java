package com.inc.admin.service.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inc.admin.dao.biz.BookDao;
import com.inc.admin.domain.biz.Book;
import com.inc.admin.domain.biz.BookCriteria;
import com.inc.admin.service.biz.BookService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("bookService")
public class BookServiceImpl implements BookService {
    @Resource
    private BookDao bookDao;

    /**
     * 分页查询
     */
    @Override
    public PageInfo<Book> listByPage(Book req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        BookCriteria condition = buildCondition(req);
        return new PageInfo<>(bookDao.selectByExample(condition));
    }

    /**
     * 查询list
     */
    @Override
    public List<Book> getList(Book req) {
        return bookDao.selectByExample(buildCondition(req));
    }

    /**
     * 单个查询
     */
    @Override
    public Book getOne(Book req) {
        List<Book> lst = getList(req);
        if (CollectionUtils.isEmpty(lst)) {
            return null;
        }
        return lst.get(0);
    }

    /**
     * 新增
     */
    @Override
    public int insert(Book req) {
        return bookDao.insertSelective(req);
    }

    /**
     * 根据主键更新不为空的值
     */
    @Override
    public int update(Book req) {
        return bookDao.updateByPrimaryKeySelective(req);
    }

    /**
     * 根据主键删除该记录
     */
    @Override
    public int delete(Integer id) {
        return bookDao.deleteByPrimaryKey(id);
    }

    /**
     * 构建查询条件
     */
    private BookCriteria buildCondition(Book req) {
        BookCriteria bookCriteria= new BookCriteria();
        BookCriteria.Criteria criteria = bookCriteria.createCriteria();
        if (req != null) {
            if (req.getId() != null) {criteria.andIdEqualTo(req.getId());}
            if (StringUtils.isNotBlank(req.getName())) {criteria.andNameEqualTo(req.getName());}
            if (StringUtils.isNotBlank(req.getStatus())) {criteria.andStatusEqualTo(req.getStatus());}
            if (StringUtils.isNotBlank(req.getUser())) {criteria.andUserEqualTo(req.getUser());}
            if (req.getDel() != null) {criteria.andDelEqualTo(req.getDel());        }
        }
        return bookCriteria;
    }
}