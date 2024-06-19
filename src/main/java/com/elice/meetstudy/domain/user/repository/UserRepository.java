package com.elice.meetstudy.domain.user.repository;

import com.elice.meetstudy.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  Optional<User> findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.id = :userId")
  User findUserByUserId(Long userId);

  @Query("SELECT u.id FROM User u WHERE u.email = :email")
  Long findUserIdByEmail(@Param("email") String email);

  @Query("SELECT u.username FROM User u WHERE u.email = :email")
  String findUserNameByEmail(@Param("email") String email);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  User findUserByEmail(@Param("email") String email);

  @Query("SELECT u FROM User u WHERE u.nickname = :nickname")
  User findUserByNickname(String nickname);
}
