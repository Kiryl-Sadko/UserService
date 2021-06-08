package com.hes.test.builder;

import com.hes.test.dto.UserAccountDto;
import com.hes.test.entity.Role;

import java.time.LocalDateTime;

public interface UserAccountDtoBuilder extends DtoBuilder<UserAccountDto> {

    UserAccountDtoBuilder setId(Long id);

    UserAccountDtoBuilder setUserName(String userName);

    UserAccountDtoBuilder setPassword(String password);

    UserAccountDtoBuilder setFirstName(String firstName);

    UserAccountDtoBuilder setLastName(String lastName);

    UserAccountDtoBuilder setRole(Role role);

    UserAccountDtoBuilder setStatus(boolean isActive);

    UserAccountDtoBuilder setRegistrationDateTime(LocalDateTime registrationDateTime);
}
