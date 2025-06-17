package org.sopt.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.jwt.util.JwtUtil;
import org.sopt.user.domain.User;
import org.sopt.user.dto.request.LoginRequest;
import org.sopt.user.dto.request.UserCreateRequest;
import org.sopt.global.error.exception.BusinessException;
import org.sopt.user.repository.UserRepository;
import org.sopt.validator.TextValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final int MAX_USERNAME = 10;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public Long createUser(UserCreateRequest request){

        validateUsername(request.name());

        User newUser = User.createUser(request.name(), bCryptPasswordEncoder.encode(request.password()), request.email());
        userRepository.save(newUser);

        return newUser.getId();
    }

    public String loginProcess(LoginRequest request) {

        User loginUser = userRepository.findByName(request.username()).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        if(!bCryptPasswordEncoder.matches(request.password(), loginUser.getPassword())){
            throw new BusinessException(INVALID_PASSWORD);
        }

        return jwtUtil.createAccessToken(loginUser.getId(), loginUser.getName());
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
    }

    private void validateUsername(String name) {
        if(name == null || name.isEmpty() || name.isBlank()) throw new BusinessException(NOT_ALLOWED_BLANK_USERNAME);
        if(TextValidator.isTextLengthBiggerThanLimit(name, MAX_USERNAME)) throw new BusinessException(TOO_LONG_USERNAME);
    }
}
