package com.muhe.myshop.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhe.myshop.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}
