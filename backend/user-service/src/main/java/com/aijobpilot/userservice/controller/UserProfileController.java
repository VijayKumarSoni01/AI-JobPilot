package com.aijobpilot.userservice.controller;

import com.aijobpilot.userservice.dto.UserProfileRequest;
import com.aijobpilot.userservice.dto.UserProfileResponse;
import com.aijobpilot.userservice.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable Long userId,
                                                          @RequestHeader(value = "X-Authenticated-User-Id", required = false) String gatewayUserId) {
        // If gateway forwarded user id, we can validate or use it; otherwise use path variable for standalone/testing
        return ResponseEntity.ok(userProfileService.getProfileByUserId(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> updateProfile(@PathVariable Long userId,
                                                             @Valid @RequestBody UserProfileRequest request,
                                                             @RequestHeader(value = "X-Authenticated-User-Id", required = false) String gatewayUserId) {
        return ResponseEntity.ok(userProfileService.upsertProfile(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long userId) {
        userProfileService.deleteProfile(userId);
        return ResponseEntity.noContent().build();
    }
}
