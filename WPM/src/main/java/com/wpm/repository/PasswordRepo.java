package com.wpm.repository;

import com.wpm.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordRepo extends JpaRepository<Password, Integer> {

}
