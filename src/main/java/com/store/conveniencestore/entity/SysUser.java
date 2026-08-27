package com.store.conveniencestore.entity;

import java.time.LocalDateTime;

public class SysUser {
    private Integer id;
    private String username;
    private String passward;
    private Boolean enabled;
    private LocalDateTime creaTime;
    private LocalDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreaTime() {
        return creaTime;
    }

    public void setCreaTime(LocalDateTime creaTime) {
        this.creaTime = creaTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
