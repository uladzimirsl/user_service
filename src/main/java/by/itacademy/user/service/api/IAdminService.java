package by.itacademy.user.service.api;

import by.itacademy.user.service.dto.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;
@Validated
public interface IAdminService {

    CreatUserDto create(@Valid CreatUserDto dto);

    PageDto<UserReadDto> getAll(int page, int size);

    UserReadDto get(UUID uuid);

    CreatUserDto update (@Valid CreatUserDto dto, UUID uuid, Long dtUpdate);

}
