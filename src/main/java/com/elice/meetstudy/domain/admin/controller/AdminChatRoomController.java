package com.elice.meetstudy.domain.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/chatrooms")
@Tag(name = "C. 관리자")
public class AdminChatRoomService {

    private final AdminChatRoomService adminChatRoomService;

    @Autowired
    public ChatRoomController(AdminChatRoomService adminChatRoomService) {
        this.adminChatRoomService = adminChatRoomService;
    }

    
}
