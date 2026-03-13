package com.wrogn.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wrogn.task.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{

        Page<UserEntity> findByEmailContaining(String email,Pageable pageable);

        @Query("select u from UserEntity u where u.email like %:keyword%")
        Page<UserEntity> searchByEmail(@Param("keyword") String keyword,Pageable pageable );

        @Query(value = "select * from  users where email like %:keyword%",nativeQuery = true) // Native Query
        List<UserEntity> searchNative(@Param("keyword") String keyword);

        @Query("select u from UserEntity u where u.email = :email")
        Optional<UserEntity> findByEmailCustom(@Param("email")String email);


}
