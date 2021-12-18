package com.inc.admin.service.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inc.admin.dao.biz.BookDao;
import com.inc.admin.dao.biz.BookSql;
import com.inc.admin.domain.biz.Book;
import com.inc.admin.service.biz.BookService;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;
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
        SelectDSLCompleter completer = buildCompleter(req);
        return new PageInfo<>(bookDao.select(completer));
    }

    /**
     * 查询list
     */
    @Override
    public List<Book> getList(Book req) {
        return bookDao.select(buildCompleter(req));
    }

    /**
     * 单个查询
     */
    @Override
    public Book getOne(Book req) {
        Optional<Book> book = bookDao.selectOne(buildCompleter(req));
        return book.orElse(null);
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
    private SelectDSLCompleter buildCompleter(Book req) {
        SelectDSLCompleter completer = new SelectDSLCompleter() {
            @Override
            public Buildable<SelectModel> apply(QueryExpressionDSL<SelectModel> selectModelQueryExpressionDSL) {
                QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder where = selectModelQueryExpressionDSL.where();
                Integer id = req.getId();
                if (id != null) {
                    where.and(BookSql.id, SqlBuilder.isEqualTo(req.getId()));
                }
                String name = req.getName();
                if (StringUtils.isNotBlank(name)) {
                    where.and(BookSql.name, SqlBuilder.isEqualTo(req.getName()));
                }
                String status = req.getStatus();
                if (StringUtils.isNotBlank(status)) {
                    where.and(BookSql.status, SqlBuilder.isEqualTo(req.getStatus()));
                }
                String user = req.getUser();
                if (StringUtils.isNotBlank(user)) {
                    where.and(BookSql.user, SqlBuilder.isEqualTo(req.getUser()));
                }
                Byte del = req.getDel();
                if (del != null) {
                    where.and(BookSql.del, SqlBuilder.isEqualTo(req.getDel()));
                }
                return where;
            }
        };
        return completer;
    }
}