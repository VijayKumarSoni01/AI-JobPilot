package com.aijobpilot.userservice.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequest {

    @Size(max = 100, message = "First name must be at most 100 characters")
    private String firstName;

    @Size(max = 100, message = "Last name must be at most 100 characters")
    private String lastName;

    @Size(max = 255, message = "Headline must be at most 255 characters")
    private String headline;

    private String summary;

    @Size(max = 150, message = "Location must be at most 150 characters")
    private String location;

    @Size(max = 50, message = "Phone must be at most 50 characters")
    private String phone;
}
