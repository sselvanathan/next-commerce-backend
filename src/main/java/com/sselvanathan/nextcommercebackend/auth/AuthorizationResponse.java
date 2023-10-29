package com.sselvanathan.nextcommercebackend.auth;

import com.sselvanathan.nextcommercebackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationResponse {
    private User user;
    private String token;
}
