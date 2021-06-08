package com.hes.test.builder.impl;

import com.hes.test.builder.UserAccountEntityBuilder;
import com.hes.test.dto.RoleDto;
import com.hes.test.dto.Status;
import com.hes.test.entity.Role;
import com.hes.test.entity.UserAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserAccountEntityBuilderImpl implements UserAccountEntityBuilder {

    private UserAccount userAccount = new UserAccount();

    @Override
    public UserAccount build() {
        UserAccount result = new UserAccount();
        result.setId(userAccount.getId());
        result.setUserName(userAccount.getUserName());
        result.setFirstName(userAccount.getFirstName());
        result.setLastName(userAccount.getLastName());
        result.setPassword(userAccount.getPassword());
        result.setRole(userAccount.getRole());
        result.setIsActive(userAccount.isActive());
        result.setRegistrationDateTime(userAccount.getRegistrationDateTime());
        this.reset();
        return result;
    }

    @Override
    public void reset() {
        userAccount = new UserAccount();
    }


    @Override
    public UserAccountEntityBuilder setId(Long id) {
        userAccount.setId(id);
        return this;
    }

    @Override
    public UserAccountEntityBuilder setUserName(String userName) {
        userAccount.setUserName(userName);
        return this;
    }

    @Override
    public UserAccountEntityBuilder setPassword(String password) {
        userAccount.setPassword(password);
        return this;
    }

    @Override
    public UserAccountEntityBuilder setFirstName(String firstName) {
        userAccount.setFirstName(firstName);
        return this;
    }

    @Override
    public UserAccountEntityBuilder setLastName(String lastName) {
        userAccount.setLastName(lastName);
        return this;
    }

    @Override
    public UserAccountEntityBuilder setRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        userAccount.setRole(role);
        return this;
    }

    @Override
    public UserAccountEntityBuilder setIsActive(Status status) {
        userAccount.setIsActive(status.isActive());
        return this;
    }

    @Override
    public UserAccountEntityBuilder setRegistrationDateTime(LocalDateTime registrationDateTime) {
        userAccount.setRegistrationDateTime(registrationDateTime);
        return this;
    }
}
