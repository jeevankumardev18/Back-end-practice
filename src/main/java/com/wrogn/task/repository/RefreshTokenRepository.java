package com.wrogn.task.repository;

import com.wrogn.task.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long>
{
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByEmail(String email);
}
