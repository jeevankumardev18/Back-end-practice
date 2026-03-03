package com.wrogn.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wrogn.task.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{

        Page<UserEntity> findByEmailContaining(String email,Pageable pageable);
}
