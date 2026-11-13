package com.aijobpilot.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String headline;
    private String summary;
    private String location;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
