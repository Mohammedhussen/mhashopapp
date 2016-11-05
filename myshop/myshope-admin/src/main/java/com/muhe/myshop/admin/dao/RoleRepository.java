package com.muhe.myshop.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhe.myshop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);
}
