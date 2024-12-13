package org.example.memberservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.memberservice.dto.ModifyUserDto;
import org.example.memberservice.dto.RegisterUserDto;
import org.example.memberservice.entity.UserEntity;
import org.example.memberservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final UserService userService;

    @PostMapping("/users/registration")
    public UserEntity registerUser(@RequestBody RegisterUserDto dto) {
        return userService.registerUser(dto.loginId(), dto.userName());
    }

    @PutMapping("/users/{userId}/modify")
    public UserEntity modifyUser(@PathVariable long userId, @RequestBody ModifyUserDto dto) {
        return userService.modifyUser(userId, dto.userName());
    }

    @PostMapping("/users/{loginId}/login")
    public UserEntity login(@PathVariable String loginId) {
        return userService.getUser(loginId);
    }
}
