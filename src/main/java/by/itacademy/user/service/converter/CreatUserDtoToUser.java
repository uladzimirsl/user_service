package by.itacademy.user.service.converter;

import by.itacademy.user.dao.api.RoleRepository;
import by.itacademy.user.dao.entity.Role;
import by.itacademy.user.dao.entity.User;
import by.itacademy.user.service.dto.CreatUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CreatUserDtoToUser implements Converter<CreatUserDto, User> {

    private final PasswordEncoder encoder;
    private final RoleRepository repository;

    public CreatUserDtoToUser(PasswordEncoder encoder, RoleRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @Override
    public User convert(CreatUserDto source) {
        Set<Role> roles = new HashSet<>();
        Arrays.stream(source.getRole()
                .split(", "))
                .map(s -> roles.add(repository.findByAuthority(s)))
                .collect(Collectors.toSet());
        System.out.println(roles);
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        return User.builder()
                .setUuid(UUID.randomUUID())
                .setUsername(source.getNick())
                .setMail(source.getMail())
                .setPassword(encoder.encode(source.getPassword()))
                .setStatus(source.getStatus())
                .setAuthorities(roles)
                .setDtCreate(localDateTime)
                .setDtUpdate(localDateTime)
                .setAccountNonLocked(true)
                .setAccountNonExpired(true)
                .setCredentialsNonExpired(true)
                .setEnabled(true)
                .build();
    }
}
