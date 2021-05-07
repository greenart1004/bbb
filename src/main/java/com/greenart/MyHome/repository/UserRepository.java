package com.greenart.MyHome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greenart.MyHome.model.User;



public interface UserRepository extends JpaRepository<User, Long> {
   
	@EntityGraph(attributePaths = { "boards" })
    List<User> findAll();

    User findByUsername(String username);  //id로도 유저정보를 가져올수 있지만 username이름으로 user 정보를 가져오기 위해 선언해줌
}
