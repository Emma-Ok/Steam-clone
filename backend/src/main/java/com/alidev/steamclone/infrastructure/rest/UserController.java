package com.alidev.steamclone.infrastructure.rest;

import com.alidev.steamclone.application.dto.library.LibraryEntryResponse;
import com.alidev.steamclone.application.dto.user.UpdateUserRequest;
import com.alidev.steamclone.application.dto.user.UserResponse;
import com.alidev.steamclone.application.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id,
                                               @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @GetMapping("/{id}/library")
    public ResponseEntity<List<LibraryEntryResponse>> library(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getLibrary(id));
    }
}
