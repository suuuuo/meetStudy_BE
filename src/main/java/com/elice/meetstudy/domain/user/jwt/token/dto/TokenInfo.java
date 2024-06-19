package com.elice.meetstudy.domain.user.jwt.token.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(exclude = {"accessToken"})
public class TokenInfo {

    private String accessToken;

    private Date accessTokenExpireTime;
    private String ownerLoginId;

    @Builder
    public TokenInfo(String accessToken, Date accessTokenExpireTime, String ownerLoginId){
        this.accessToken = accessToken;
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.ownerLoginId = ownerLoginId;
    }
}