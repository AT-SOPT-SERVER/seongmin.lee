package org.sopt.user.service;

import org.sopt.user.domain.User;
import org.sopt.user.dto.UserCreateRequest;
import org.sopt.global.error.exception.BusinessException;
import org.sopt.user.repository.UserRepository;
import org.sopt.validator.TextValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.global.error.ErrorCode.*;

@Service
public class UserService {

    private static final int MAX_USERNAME = 10;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createUser(UserCreateRequest request){

        validateUsername(request.name());

        User newUser = User.createUser(request.name(), request.email());
        userRepository.save(newUser);

        return newUser.getId();
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
    }

    private void validateUsername(String name) {
        if(name == null || name.isEmpty() || name.isBlank()) throw new BusinessException(NOT_ALLOWED_BLANK_USERNAME);
        if(TextValidator.isTextLengthBiggerThanLimit(name, MAX_USERNAME)) throw new BusinessException(TOO_LONG_USERNAME);
    }
}
