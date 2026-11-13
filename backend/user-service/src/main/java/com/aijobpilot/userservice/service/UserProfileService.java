package com.aijobpilot.userservice.service;

import com.aijobpilot.userservice.dto.UserProfileRequest;
import com.aijobpilot.userservice.dto.UserProfileResponse;
import com.aijobpilot.userservice.entity.UserProfile;
import com.aijobpilot.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfileByUserId(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> UserProfile.builder().userId(userId).build());
        return mapToResponse(profile);
    }

    @Transactional
    public UserProfileResponse upsertProfile(Long userId, UserProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> UserProfile.builder().userId(userId).build());

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setHeadline(request.getHeadline());
        profile.setSummary(request.getSummary());
        profile.setLocation(request.getLocation());
        profile.setPhone(request.getPhone());

        UserProfile saved = userProfileRepository.save(profile);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteProfile(Long userId) {
        userProfileRepository.findByUserId(userId).ifPresent(userProfileRepository::delete);
    }

    private UserProfileResponse mapToResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .headline(profile.getHeadline())
                .summary(profile.getSummary())
                .location(profile.getLocation())
                .phone(profile.getPhone())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
