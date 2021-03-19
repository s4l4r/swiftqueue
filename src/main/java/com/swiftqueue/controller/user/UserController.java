package com.swiftqueue.controller.user;

import com.swiftqueue.dto.auth.VerificationCodeDTO;
import com.swiftqueue.dto.user.UserInfoDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/{username}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<UserInfoDTO> getUserByUsername(@PathVariable String username) throws ResourceNotFoundException {
        UserInfoDTO userInfoDTO = userInfoService.getByUsername(username);
        if (userInfoDTO == null)
            throw new ResourceNotFoundException("User", username);
        return ResponseEntity.ok(userInfoDTO);
    }

    @PostMapping
    public ResponseEntity<UserInfoDTO> createUser(@RequestBody @Valid UserInfoDTO dto) throws BusinessException {
        UserInfoDTO savedUserInfoDTO = userInfoService.save(dto);
        return ResponseEntity.created(UriComponentsBuilder
                        .fromPath("/api/v1/users/")
                        .pathSegment(String.valueOf(savedUserInfoDTO.getId()))
                        .build().toUri()).build();
    }

    @PostMapping("/start-verify")
    public ResponseEntity<VerificationCodeDTO> sendVerificationCodeAsSMS(@RequestParam("userId") Long userId) throws ResourceNotFoundException {
        UserInfoDTO userInfoDTO = userInfoService.getById(userId);
        return ResponseEntity.ok(userInfoService.sendVerificationCodeAsSMS(userInfoDTO.getUsername()));
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyUserVerificationCode(@RequestParam("code") String code, @RequestParam("userId") Long userId) throws ResourceNotFoundException {
        UserInfoDTO userInfoDTO = userInfoService.getById(userId);
        return ResponseEntity.ok(userInfoService.verifyRegisteredUser(code, userInfoDTO));
    }

    @GetMapping("/test/{username}")
    public ResponseEntity<Boolean> isUserRegistered(@PathVariable String username) {
        return ResponseEntity.ok(userInfoService.getByUsername(username) != null);
    }

    @PutMapping
    public ResponseEntity<Void> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) throws BusinessException {
        userInfoService.updateUserInfo(userInfoDTO);
        return ResponseEntity.noContent().build();
    }
}
