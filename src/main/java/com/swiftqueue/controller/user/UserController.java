package com.swiftqueue.controller.user;

import com.swiftqueue.dto.user.UserInfoDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.user.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Slf4j
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

    @GetMapping("/test/{username}")
    public ResponseEntity<Boolean> isUserRegisteredByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userInfoService.getByUsername(username) != null);
    }

    @GetMapping("/enabled/{username}")
    public ResponseEntity<Boolean> isUserEnabledByUsername(@PathVariable String username) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(userInfoService.enabledByUsername(username));
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) throws BusinessException {
        userInfoService.updateUserInfo(userInfoDTO);
        return ResponseEntity.noContent().build();
    }
}
