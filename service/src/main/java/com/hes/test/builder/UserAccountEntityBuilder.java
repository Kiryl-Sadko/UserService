package com.hes.test.builder;

import com.hes.test.dto.RoleDto;
import com.hes.test.dto.Status;
import com.hes.test.entity.UserAccount;

import java.time.LocalDateTime;

public interface UserAccountEntityBuilder extends EntityBuilder<UserAccount> {

    UserAccountEntityBuilder setId(Long id);

    UserAccountEntityBuilder setUserName(String userName);

    UserAccountEntityBuilder setPassword(String password);

    UserAccountEntityBuilder setFirstName(String firstName);

    UserAccountEntityBuilder setLastName(String lastName);

    UserAccountEntityBuilder setRole(RoleDto roleDto);

    UserAccountEntityBuilder setIsActive(Status status);

    UserAccountEntityBuilder setRegistrationDateTime(LocalDateTime registrationDateTime);
}
