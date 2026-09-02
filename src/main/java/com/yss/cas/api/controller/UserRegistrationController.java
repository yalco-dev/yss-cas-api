package com.yss.cas.api.controller;

import com.yss.cas.api.model.UserRegistrationRequest;
import com.yss.cas.api.service.UserRegistartionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cas/users/v1.0.0")
public class UserRegistrationController {

    private final UserRegistartionService userService;

    @PostMapping
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        userService.registerNewUser(request);
        return ResponseEntity.ok().build();
    }
}
