package com.hes.test.converter.impl;

import com.hes.test.builder.UserAccountDtoBuilder;
import com.hes.test.builder.UserAccountEntityBuilder;
import com.hes.test.converter.UserAccountConverter;
import com.hes.test.dto.UserAccountDto;
import com.hes.test.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserAccountConverterImpl implements UserAccountConverter {

    private final UserAccountDtoBuilder dtoBuilder;
    private final UserAccountEntityBuilder entityBuilder;

    public UserAccountConverterImpl(UserAccountDtoBuilder dtoBuilder, UserAccountEntityBuilder entityBuilder) {
        this.dtoBuilder = dtoBuilder;
        this.entityBuilder = entityBuilder;
    }

    @Override
    public UserAccount convertToEntity(UserAccountDto dto) {
        return entityBuilder
                .setId(dto.getId())
                .setUserName(dto.getUserName())
                .setPassword(dto.getPassword())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setRole(dto.getRoleDto())
                .setIsActive(dto.getStatus())
                .setRegistrationDateTime(dto.getRegistrationDateTime())
                .build();
    }

    @Override
    public UserAccountDto convertToDto(UserAccount entity) {
        return dtoBuilder
                .setId(entity.getId())
                .setUserName(entity.getUserName())
                .setPassword(entity.getPassword())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setRole(entity.getRole())
                .setStatus(entity.isActive())
                .setRegistrationDateTime(entity.getRegistrationDateTime())
                .build();
    }
}
