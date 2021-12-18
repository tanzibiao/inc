package com.inc.admin.dao.biz;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BookSql {
    public static final Book book = new Book();

    /**编号*/
    public static final SqlColumn<Integer> id = book.id;

    /**书名*/
    public static final SqlColumn<String> name = book.name;

    /**状态*/
    public static final SqlColumn<String> status = book.status;

    /**借书人*/
    public static final SqlColumn<String> user = book.user;

    /**是否删除*/
    public static final SqlColumn<Byte> del = book.del;

    public static final class Book extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<String> status = column("status", JDBCType.VARCHAR);

        public final SqlColumn<String> user = column("user", JDBCType.VARCHAR);

        public final SqlColumn<Byte> del = column("del", JDBCType.TINYINT);

        public Book() {
            super("book");
        }
    }
}