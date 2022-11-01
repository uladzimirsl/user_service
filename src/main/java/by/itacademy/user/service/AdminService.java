package by.itacademy.user.service;

import by.itacademy.user.dao.api.UserRepository;
import by.itacademy.user.dao.entity.User;
import by.itacademy.user.service.api.IAdminService;
import by.itacademy.user.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AdminService implements IAdminService {
    private final UserRepository repository;
    private final ModelMapper mapper;
    private final ConversionService conversionService;


    public AdminService(UserRepository repository, ModelMapper mapper, ConversionService conversionService) {
        this.repository = repository;
        this.mapper = mapper;
        this.conversionService = conversionService;
    }


    @Override
    @Transactional
    public CreatUserDto create(CreatUserDto dto) {
        User entity = conversionService.convert(dto, User.class);
        repository.save(entity);
        return dto;
    }

    @Override
    public PageDto<UserReadDto> getAll(int page, int size) {
        List<User> listEntity = repository.findAll();
        List<UserReadDto> listDto = listEntity.stream()
                .map(element -> conversionService.convert(element, UserReadDto.class))
                .collect(Collectors.toList());
        Pageable pageRequest = PageRequest.of(--page,size);
        Page<User> entities = repository.findAll(pageRequest);
        Page<UserReadDto> pageDtoR = new PageImpl<>(listDto, pageRequest, entities.getTotalElements());
        PageDto<UserReadDto> pageDto = mapper.map(pageDtoR,PageDto.class);
        return pageDto;
    }

    @Override
    public UserReadDto get(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("This field cannot be null");
        }
        User entity = repository.findById(uuid).
                orElseThrow(()-> {
                    throw new EntityNotFoundException("There is no such user");
                });
        return conversionService.convert(entity, UserReadDto.class);
    }

    @Override
    @Transactional
    public CreatUserDto update(CreatUserDto dto, UUID uuid, Long dtUpdate) {
        User entity = repository.findById(uuid).orElseThrow(()-> {
            throw new EntityNotFoundException("There is no such user");
        });

        if (uuid == null) {
            throw new IllegalArgumentException("This field cannot be null");
        }

        LocalDateTime dateUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdate), ZoneId.systemDefault());
        if (!entity.getDtUpdate().equals(dateUpdate)) {
            throw new OptimisticLockException("Entity already updated");
        }

        User updateEntity = conversionService.convert(dto, User.class);
        updateEntity.setUuid(uuid);
        updateEntity.setDtUpdate(dateUpdate);
        repository.save(updateEntity);
        return dto;
    }

}
