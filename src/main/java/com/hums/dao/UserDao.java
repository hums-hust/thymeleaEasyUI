package com.hums.dao;

import com.hums.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Mapper
public interface UserDao extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {
}
