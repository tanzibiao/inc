package com.inc.admin.domain.biz;

import com.inc.admin.domain.sys.BaseEntity;

/**
 * 
 * 表名：book
 * 图书
 * @author tanzibiao
 * @date 2023-05-08 20:19:41
 */
public class Book extends BaseEntity {
    /**编号*/
    private Integer id;

    /**书名*/
    private String name;

    /**状态*/
    private String status;

    /**借书人*/
    private String user;

    /**是否删除*/
    private Boolean del;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }
}