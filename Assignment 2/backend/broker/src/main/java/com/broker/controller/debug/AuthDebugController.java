//TODO: Remove this later

package com.broker.controller.debug;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authdebug")
public class AuthDebugController {

    @GetMapping("/public")
    public String getPublicResponse() {
        return "public response";
    }

    @GetMapping("/private")
    public String getPrivateResponse(@AuthenticationPrincipal Jwt token) {
        String userId = token.getSubject();
        return "Welcome " + userId;
    }

    // Only allowed for access tokens with manager permissions
    @GetMapping("/privatescoped")
    public String getPrivateScopedResponse(@AuthenticationPrincipal Jwt token) {
        String userId = token.getSubject();
        return "You're a manager, " + userId;
    }

}