//TODO: Remove this later

package com.broker.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authdebug")
public class AuthDebugController {

    @GetMapping("/public")
    @CrossOrigin("*")
    public ResponseEntity<String> getPublicResponse() {
        return ResponseEntity.ok("public response");
    }

    @GetMapping("/private")
    public ResponseEntity<String> getPrivateResponse() {
        return ResponseEntity.ok("private response");
    }


    // Only allowed for access tokens with manager permissions
    @GetMapping("/privatescoped")
    public ResponseEntity<String> getPrivateScopedResponse() {
        return ResponseEntity.ok("private scoped response");
    }

}