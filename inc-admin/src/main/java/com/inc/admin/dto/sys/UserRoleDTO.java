package com.inc.admin.dto.sys;

/**
 * 用户角色
 * @author 64301325@qq.com
 */
public class UserRoleDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private Long id;
    private String name;
    private boolean checked;

}
