package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserMapper {

    @Select("SELECT id, username,password,enabled,create_time,update_time FROM sys_user WHERE username = #{username}")
    SysUser findByUsername(String username);

    /**
     * 查询指定用户拥有的全部角色编码。
     */
    @Select("""
            SELECT r.code
            FROM sys_role r
            INNER JOIN sys_user_role ur
                ON ur.role_id = r.id
            WHERE ur.user_id = #{userId}
            ORDER BY r.id
            """)
    List<String> findRoleCodeByUserId(Integer userId);
}
