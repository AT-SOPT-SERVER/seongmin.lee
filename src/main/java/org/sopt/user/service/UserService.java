package org.sopt.user.service;

import org.sopt.user.domain.User;
import org.sopt.user.dto.UserCreateRequest;
import org.sopt.global.error.exception.BusinessException;
import org.sopt.user.repository.UserRepository;
import org.sopt.validator.TextValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.global.error.ErrorCode.NOT_ALLOWED_BLANK_USERNAME;
import static org.sopt.global.error.ErrorCode.TOO_LONG_USERNAME;

@Service
public class UserService {

    private final int USERNAME_LIMIT = 10;
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

    private void validateUsername(String name) {
        if(name == null || name.isEmpty() || name.isBlank()) throw new BusinessException(NOT_ALLOWED_BLANK_USERNAME);
        if(TextValidator.isTextLengthBiggerThanLimit(name, USERNAME_LIMIT)) throw new BusinessException(TOO_LONG_USERNAME);
    }
}
