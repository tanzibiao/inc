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

/**
 * 图书Service实现类
 * 
 * @author tanzibiao
 * @date 2021/06/13
 *
 * @mbg.generated 2021/06/13
 */
@Service("bookService")
public class BookServiceImpl implements BookService {
    @Resource
    private BookDao bookDao;

    /**
     * 分页查询
     * @param req
     * @return 
     */
    @Override
    public PageInfo<Book> listByPage(Book req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        return new PageInfo<>(bookDao.selectByCriteria(buildCriteria(req)));
    }

    /**
     * 查询list
     * @param req
     * @return 
     */
    @Override
    public List<Book> getList(Book req) {
        return bookDao.selectByCriteria(buildCriteria(req));
    }

    /**
     * 单个查询
     * @param req
     * @return 
     */
    @Override
    public Book getOne(Book req) {
        List<Book> list = getList(req);
        if(CollectionUtils.isNotEmpty(list)) {return list.get(0);}
        return null;
    }

    /**
     * 新增
     * @param record
     * @return 
     */
    @Override
    public int insert(Book record) {
        return bookDao.insertSelective(record);
    }

    /**
     * 根据主键更新不为空的值
     * @param record
     * @return 
     */
    @Override
    public int update(Book record) {
        return bookDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键删除该记录
     * @param id
     * @return 
     */
    @Override
    public int delete(Integer id) {
        return bookDao.deleteByPrimaryKey(id);
    }

    /**
     * 构建查询条件
     * @param req
     * @return 
     */
    private BookCriteria buildCriteria(Book req) {
        BookCriteria bookCriteria= new BookCriteria();
        BookCriteria.Criteria criteria = bookCriteria.createCriteria();
        if (req != null) {
            if (req.getId() != null) {criteria.andIdEqualTo(req.getId());}
            if (StringUtils.isNotBlank(req.getName())) {criteria.andNameEqualTo(req.getName());}
            if (StringUtils.isNotBlank(req.getStatus())) {criteria.andStatusEqualTo(req.getStatus());}
            if (StringUtils.isNotBlank(req.getUser())) {criteria.andUserEqualTo(req.getUser());}
            
        }
        return bookCriteria;
    }
}