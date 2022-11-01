package by.itacademy.user.service;


import by.itacademy.user.dao.api.UserRepository;

import by.itacademy.user.dao.entity.User;
import by.itacademy.user.dao.entity.enums.Status;
import by.itacademy.user.service.api.IUserService;
import by.itacademy.user.service.dto.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ConversionService conversionService;

    public UserService(UserRepository repository, PasswordEncoder encoder, ConversionService conversionService) {
        this.repository = repository;
        this.encoder = encoder;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public RegistrationDto save(RegistrationDto dto) {
        if (repository.existsByMail(dto.getMail())) {
            throw new EntityExistsException("User already exists");
        }
        if (repository.existsByUsername(dto.getNick())){
            throw new EntityExistsException("Nick already exists");
        }
        User entity = conversionService.convert(dto, User.class);

        repository.save(entity);
        return dto;
    }

    @Override
    public User login(LoginUserDto dto) {
        User user = repository.findByMail(dto.getMail());

        if(!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Wrong password");
        }
        if(!user.isEnabled()){
            throw new SecurityException("Account is blocked");
        }
        if (!user.getStatus().equals(Status.ACTIVATED)) {
            throw new SecurityException("Account not activated, wait for activation");
        }
        return user;
    }

    @Override
    public UserReadDto infoForMe(String username) {
        User entity = repository.findByUsername(username);
        return conversionService.convert(entity, UserReadDto.class);
    }

}
