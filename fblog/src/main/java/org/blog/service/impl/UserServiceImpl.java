package org.blog.service.impl;

import org.blog.mapper.RoleMapper;
import org.blog.mapper.UserMapper;
import org.blog.model.User;
import org.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null)
            throw new UsernameNotFoundException("用户名不存在");
        user.setRoles(roleMapper.getRolesByUid(user.getId()));
        return user;
    }
}
