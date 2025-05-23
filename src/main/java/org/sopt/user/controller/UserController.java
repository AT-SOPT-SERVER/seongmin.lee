package org.sopt.user.controller;

import org.sopt.user.dto.UserCreateRequest;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.sopt.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResultResponse<Void>> join(@RequestBody UserCreateRequest request){
        Long id = userService.createUser(request);
        URI location = URI.create("/users" + id);
        return ResponseEntity.created(location)
                .body(ResultResponse.of(ResultCode.CREATED, null));
    }

}
