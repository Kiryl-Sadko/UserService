package com.hes.test.service.impl;

import com.hes.test.config.ServiceConfig;
import com.hes.test.converter.UserAccountConverter;
import com.hes.test.dto.UserAccountDto;
import com.hes.test.entity.Role;
import com.hes.test.entity.UserAccount;
import com.hes.test.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = ServiceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:init.sql", "classpath:data_generation.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class UserAccountServiceImplTest {

    @Autowired
    private UserAccountService service;
    @Autowired
    private UserAccountConverter converter;

    @Test
    void shouldReturnAllUserAccounts() {
        List<UserAccountDto> userAccountDtos = service.findAll();
        assertEquals(2, userAccountDtos.size());
    }

    @Test
    void shouldFindUserAccountById() {
        UserAccountDto userAccountDtoById = service.findById(1L);
        assertEquals("Kiryl", userAccountDtoById.getUserName());
    }

    @Test
    void shouldFindUserAccountByUserName() {
        UserAccountDto userAccountDto = service.findByUserName("Kiryl");
        assertEquals("Kiryl", userAccountDto.getUserName());
    }

    @Test
    void shouldThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(10L));
    }

    @Test
    void shouldDeleteUserAccountById() {
        int sizeBeforeRemoving = service.findAll().size();
        service.deleteById(1L);
        int sizeAfterRemoving = service.findAll().size();
        assertEquals(--sizeBeforeRemoving, sizeAfterRemoving);
    }

    @Test
    void shouldSaveNewUserAccount() {
        int sizeBeforeSaving = service.findAll().size();
        UserAccountDto userAccountDto = buildNewUserAccountDto(converter);
        Long savedId = service.save(userAccountDto);
        int sizeAfterSaving = service.findAll().size();
        assertEquals(++sizeBeforeSaving, sizeAfterSaving);

        UserAccountDto userAccountDtoById = service.findById(savedId);
        assertEquals(userAccountDto.getUserName(), userAccountDtoById.getUserName());
    }

    @Test
    void shouldUpdateUserAccount() {
        UserAccountDto userAccountBeforeUpdating = service.findById(1L);
        String nameBeforeUpdating = userAccountBeforeUpdating.getUserName();
        String newUserName = "UpdatedName";
        userAccountBeforeUpdating.setUserName(newUserName);
        UserAccountDto updatedUserAccount = service.update(userAccountBeforeUpdating);
        String nameAfterUpdating = updatedUserAccount.getUserName();
        assertNotEquals(nameBeforeUpdating, nameAfterUpdating);
        assertEquals(newUserName, nameAfterUpdating);
    }

    private UserAccountDto buildNewUserAccountDto(UserAccountConverter converter) {
        UserAccount newUserAccount = buildNewUserAccount();
        return converter.convertToDto(newUserAccount);
    }

    private UserAccount buildNewUserAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName("TestUser");
        userAccount.setFirstName("firstName");
        userAccount.setLastName("lastName");
        userAccount.setPassword("123");
        userAccount.setIsActive(true);
        userAccount.setRole(buildRole());
        userAccount.setRegistrationDateTime(LocalDateTime.now());
        return userAccount;
    }

    private Role buildRole() {
        Role role = new Role();
        role.setId(2L);
        role.setName("USER");
        return role;
    }
}