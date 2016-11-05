package com.muhe.myshop.admin.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.muhe.myshop.model.Permission;
import com.muhe.myshop.model.Role;
import com.muhe.myshop.model.User;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User
{
	private static final long serialVersionUID = 1L;
    private User user;
     
    public AuthenticatedUser(User user)
    {
        super(user.getEmail(), user.getPassword(), getAuthorities(user));
        this.user = user;
    }
    
    
    public User getUser()
    {
        return user;
    }
    
    
    private static Collection<? extends GrantedAuthority> getAuthorities(User user)
    {
    Set<String> roleAndPermissions = new HashSet<>();
    List<Role> roles = user.getRoles();
     
    for (Role role : roles)
    {
    roleAndPermissions.add(role.getName());
    List<Permission> permissions = role.getPermissions();
    for (Permission permission : permissions)
    {
    roleAndPermissions.add("ROLE_"+permission.getName());
    }
    }
    String[] roleNames = new String[roleAndPermissions.size()];
    Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roleAndPermissions.toArray(roleNames));
    return authorities;
    }
     
}
