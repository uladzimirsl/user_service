package by.itacademy.user.service.api;

import by.itacademy.user.dao.entity.User;
import by.itacademy.user.service.dto.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface IUserService {
    RegistrationDto save (@Valid RegistrationDto dto);
    User login(@Valid LoginUserDto dto);
    UserReadDto infoForMe(String mail);
}
