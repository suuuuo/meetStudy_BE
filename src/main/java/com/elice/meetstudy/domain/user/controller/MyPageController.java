package com.elice.meetstudy.domain.user.controller;

import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.MyPageDto;
import com.elice.meetstudy.domain.user.dto.UserUpdateDto;
import com.elice.meetstudy.domain.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mypage")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "B. 마이페이지", description = "마이페이지 관련 API 입니다.")
public class MyPageController {

  private final MyPageService myPageService;

  @Operation(summary = "회원 정보 조회", description = "회원별 정보를 조회합니다.")
  @GetMapping
  public MyPageDto getUserInfo() {
    return myPageService.getUserByUserId();
  }

  @Operation(summary = "회원 정보 수정", description = "비밀번호, 이름, 닉네임, 관심분야 중 선택하여 수정할 수 있습니다.")
  @PutMapping("/edit")
  public User updateUser(@RequestBody UserUpdateDto userUpdateDto) {
    return myPageService.updateUser(userUpdateDto);
  }

  @Operation(summary = "회원 삭제 (탈퇴)", description = "탈퇴일자에 현재 시간이 들어가며 탈퇴됩니다.")
  @DeleteMapping("/delete")
  public void userDelete() {
    myPageService.delete();
  }

  @Operation(summary = "스크랩 한 게시글 조회", description = "회원별 스크랩 한 게시글을 조회합니다.")
  @GetMapping("/scraplist")
  public ResponseEntity<List<Post>> getScrappedPosts() {
    List<Post> scrappedPosts = myPageService.getScrappedPostsByUserId();
    return new ResponseEntity<>(scrappedPosts, HttpStatus.OK);
  }
}
