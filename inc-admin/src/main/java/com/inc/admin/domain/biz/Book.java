package com.inc.admin.domain.biz;

import com.inc.admin.domain.sys.BaseEntity;

/**
 * 
 * 表名：book
 * 图书
 * @author tanzibiao
 * @date 2021-12-11 01:34:35
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
    private Byte del;

    /**
     * 获取 编号
     * @return java.lang.Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置 编号
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 书名
     * @return java.lang.String
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 书名
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取 状态
     * @return java.lang.String
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置 状态
     * @param status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取 借书人
     * @return java.lang.String
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置 借书人
     * @param user
     */
    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    /**
     * 获取 是否删除
     * @return java.lang.Byte
     */
    public Byte getDel() {
        return del;
    }

    /**
     * 设置 是否删除
     * @param del
     */
    public void setDel(Byte del) {
        this.del = del;
    }
}