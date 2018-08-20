package com.gsq.javalearning.springbootdemo.repository;

import com.gsq.javalearning.springbootdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author guishangquan
 * @date 2018/8/20
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
