package com.store.conveniencestore.security;

import com.store.conveniencestore.entity.SysUser;
import com.store.conveniencestore.mapper.SysUserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//UserDetails 是 Spring Security 能看懂的“用户登录与权限信息标准格式”。
@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    public CustomUserDetailsService(SysUserMapper sysUserMapper){
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.findByUsername(username);

        if (sysUser == null) {
            throw new UsernameNotFoundException(
                    "用户名或密码错误"
            );
        }

        //Stream 中的 map() 表示：
        //把集合里的每一个元素转换成另一个元素
        //authorities 就是 Spring Security 眼中“这个用户拥有的角色和权限清单”
        List<SimpleGrantedAuthority> authorities = sysUserMapper
                .findRoleCodeByUserId(sysUser.getId())
                .stream()
                .map(roleCode -> new SimpleGrantedAuthority("ROLE_" + roleCode)
                )
                .toList();

        return User.builder()
                .username(sysUser.getUsername())
                .password(sysUser.getPassword())
                .disabled(!Boolean.TRUE.equals(sysUser.getEnabled()))
                .authorities(authorities)
                .build();
    }

}
