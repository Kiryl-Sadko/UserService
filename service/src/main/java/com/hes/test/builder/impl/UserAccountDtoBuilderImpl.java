package com.hes.test.builder.impl;

import com.hes.test.builder.UserAccountDtoBuilder;
import com.hes.test.dto.RoleDto;
import com.hes.test.dto.Status;
import com.hes.test.dto.UserAccountDto;
import com.hes.test.entity.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserAccountDtoBuilderImpl implements UserAccountDtoBuilder {

    private UserAccountDto userAccountDto = new UserAccountDto();

    @Override
    public UserAccountDto build() {
        UserAccountDto result = new UserAccountDto();
        result.setId(userAccountDto.getId());
        result.setUserName(userAccountDto.getUserName());
        result.setFirstName(userAccountDto.getFirstName());
        result.setLastName(userAccountDto.getLastName());
        result.setPassword(userAccountDto.getPassword());
        result.setRoleDto(userAccountDto.getRoleDto());
        result.setStatus(userAccountDto.getStatus());
        result.setRegistrationDateTime(userAccountDto.getRegistrationDateTime());
        this.reset();
        return result;
    }

    @Override
    public void reset() {
        userAccountDto = new UserAccountDto();
    }

    @Override
    public UserAccountDtoBuilder setId(Long id) {
        userAccountDto.setId(id);
        return this;
    }

    @Override
    public UserAccountDtoBuilder setUserName(String userName) {
        userAccountDto.setUserName(userName);
        return this;
    }

    @Override
    public UserAccountDtoBuilder setPassword(String password) {
        userAccountDto.setPassword(password);
        return this;
    }

    @Override
    public UserAccountDtoBuilder setFirstName(String firstName) {
        userAccountDto.setFirstName(firstName);
        return this;
    }

    @Override
    public UserAccountDtoBuilder setLastName(String lastName) {
        userAccountDto.setLastName(lastName);
        return this;
    }

    @Override
    public UserAccountDtoBuilder setRole(Role role) {
        RoleDto roleDto = RoleDto.formRole(role);
        userAccountDto.setRoleDto(roleDto);
        return this;
    }

    @Override
    public UserAccountDtoBuilder setStatus(boolean isActive) {
        Status status = Status.fromBoolean(isActive);
        userAccountDto.setStatus(status);
        return this;
    }

    @Override
    public UserAccountDtoBuilder setRegistrationDateTime(LocalDateTime registrationDateTime) {
        userAccountDto.setRegistrationDateTime(registrationDateTime);
        return this;
    }
}
