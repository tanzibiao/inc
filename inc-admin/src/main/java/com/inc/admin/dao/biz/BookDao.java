package com.inc.admin.dao.biz;

import static com.inc.admin.dao.biz.BookSql.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.inc.admin.domain.biz.Book;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface BookDao {
    BasicColumn[] selectList = BasicColumn.columnList(id, name, status, user, del);

    /**
     * 查询 图书 数量
     * @param selectStatement
     * @return long
     */
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    /**
     * 删除 图书
     * @param deleteStatement
     * @return int
     */
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    /**
     * 添加 图书
     * @param insertStatement
     * @return int
     */
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Book> insertStatement);

    /**
     * 批量添加 图书
     * @param multipleInsertStatement
     * @return int
     */
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<Book> multipleInsertStatement);

    /**
     * 查询单条 图书
     * @param selectStatement
     * @return java.util.Optional<com.inc.admin.domain.biz.Book>
     */
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BookResult")
    Optional<Book> selectOne(SelectStatementProvider selectStatement);

    /**
     * 查询多条 图书
     * @param selectStatement
     * @return java.util.List<com.inc.admin.domain.biz.Book>
     */
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BookResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="user", property="user", jdbcType=JdbcType.VARCHAR),
        @Result(column="del", property="del", jdbcType=JdbcType.TINYINT)
    })
    List<Book> selectMany(SelectStatementProvider selectStatement);

    /**
     * 更新 图书
     * @param updateStatement
     * @return int
     */
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    /**
     * 查询 图书 数量
     * @param completer
     */
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, book, completer);
    }

    /**
     * 删除 图书
     * @param completer
     */
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, book, completer);
    }

    /**
     * 根据主键删除 图书
     */
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    /**
     * 添加 图书
     */
    default int insert(Book record) {
        return MyBatis3Utils.insert(this::insert, record, book, c ->
            c.map(id).toProperty("id")
            .map(name).toProperty("name")
            .map(status).toProperty("status")
            .map(user).toProperty("user")
            .map(del).toProperty("del")
        );
    }

    /**
     * 批量添加 图书
     */
    default int insertMultiple(Collection<Book> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, book, c ->
            c.map(id).toProperty("id")
            .map(name).toProperty("name")
            .map(status).toProperty("status")
            .map(user).toProperty("user")
            .map(del).toProperty("del")
        );
    }

    /**
     * 插入值不为空的列 
     */
    default int insertSelective(Book record) {
        return MyBatis3Utils.insert(this::insert, record, book, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(name).toPropertyWhenPresent("name", record::getName)
            .map(status).toPropertyWhenPresent("status", record::getStatus)
            .map(user).toPropertyWhenPresent("user", record::getUser)
            .map(del).toPropertyWhenPresent("del", record::getDel)
        );
    }

    /**
     * 查询单条 图书
     * @param completer
     */
    default Optional<Book> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, book, completer);
    }

    /**
     * 查询多条 图书
     * @param completer
     */
    default List<Book> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, book, completer);
    }

    /**
     * 去重查询 图书
     * @param completer
     */
    default List<Book> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, book, completer);
    }

    /**
     * 根据主键查询 图书
     */
    default Optional<Book> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    /**
     * 更新 图书
     * @param completer
     */
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, book, completer);
    }

    /**
     * 更新所有列 
     */
    static UpdateDSL<UpdateModel> updateAllColumns(Book record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(name).equalTo(record::getName)
                .set(status).equalTo(record::getStatus)
                .set(user).equalTo(record::getUser)
                .set(del).equalTo(record::getDel);
    }

    /**
     * 更新值不为空的列 
     */
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Book record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(name).equalToWhenPresent(record::getName)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(user).equalToWhenPresent(record::getUser)
                .set(del).equalToWhenPresent(record::getDel);
    }

    /**
     * 根据主键更新 图书
     */
    default int updateByPrimaryKey(Book record) {
        return update(c ->
            c.set(name).equalTo(record::getName)
            .set(status).equalTo(record::getStatus)
            .set(user).equalTo(record::getUser)
            .set(del).equalTo(record::getDel)
            .where(id, isEqualTo(record::getId))
        );
    }

    /**
     * 根据主键更新值不为空的列 
     */
    default int updateByPrimaryKeySelective(Book record) {
        return update(c ->
            c.set(name).equalToWhenPresent(record::getName)
            .set(status).equalToWhenPresent(record::getStatus)
            .set(user).equalToWhenPresent(record::getUser)
            .set(del).equalToWhenPresent(record::getDel)
            .where(id, isEqualTo(record::getId))
        );
    }
}