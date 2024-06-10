package com.elice.meetstudy.domain.admin.controller;

import com.elice.meetstudy.domain.admin.dto.AdminUserDto;
import com.elice.meetstudy.domain.admin.service.AdminUserService;
import com.elice.meetstudy.domain.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "C. 관리자")
public class AdminUserController {

  private final AdminUserService adminUserService;

  public AdminUserController(AdminUserService adminUserService) {
    this.adminUserService = adminUserService;
  }

  // 모든 회원 조회
  @Operation(summary = "모든 회원 조회")
  @GetMapping
  public ResponseEntity<List<AdminUserDto>> getAllUsers() {
    return ResponseEntity.ok(adminUserService.findAllUsers());
  }

  // id로 회원 조회
  @Operation(summary = "id로 회원 조회")
  @GetMapping("/{id}")
  public ResponseEntity<AdminUserDto> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(adminUserService.findUser(id));
  }

  // 회원 삭제
  @Operation(summary = "회원 삭제")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    adminUserService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}