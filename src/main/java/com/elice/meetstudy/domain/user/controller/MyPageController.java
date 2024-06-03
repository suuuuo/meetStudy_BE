package com.elice.meetstudy.domain.user.controller;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.jwt.web.json.ApiResponseJson;
import com.elice.meetstudy.domain.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.elice.meetstudy.domain.user.dto.UserUpdateDto;

@RestController
@RequestMapping("api/vi/user/mypage")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "마이페이지", description = "마이페이지 관련 API 입니다.")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "회원 정보 조회")
    @GetMapping
    public User getUserInfo(@RequestHeader("Authorization") String token){
        return myPageService.getUserByUserId(token);
    }

//    @Operation(summary = "회원 정보 수정")
//    @PutMapping("/edit")
//    public User updateUser(@RequestHeader("Authorization") String token, @RequestBody UserUpdateDto userUpdateDto){
//        User updatedUser = myPageService.updateUser(token, userUpdateDto);
//
//        return updatedUser;
//    }

    @Operation(summary = "회원 삭제 (탈퇴)")
    @DeleteMapping("/delete")
    public void userDelete(@RequestHeader("Authorization") String token){

        myPageService.delete(token);
    }
}
