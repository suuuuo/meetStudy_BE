package com.elice.meetstudy.domain.admin.controller;

import com.elice.meetstudy.domain.admin.service.AdminUserService;
import com.elice.meetstudy.domain.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "C. 관리자")
public class AdminUserController {

  private final AdminUserService adminUserService;

  public AdminUserController(AdminUserService adminUserService) {
    this.adminUserService = adminUserService;
  }

  // 모든 회원 조회
  @GetMapping
  public ResponseEntity<Page<User>> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
    Page<User> users = adminUserService.findAllUsers(pageable);
    return ResponseEntity.ok(users);
  }

  // id로 회원 조회
  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(adminUserService.findUser(id));
  }

  // 회원 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    adminUserService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}