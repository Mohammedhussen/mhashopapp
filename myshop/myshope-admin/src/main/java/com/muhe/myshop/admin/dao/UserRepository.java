package com.muhe.myshop.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muhe.myshop.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>
{
User findByEmail(String email);
}
