package com.hes.test.repository;

import com.hes.test.config.PersistenceConfig;
import com.hes.test.entity.Role;
import com.hes.test.entity.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = PersistenceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:init.sql", "classpath:data_generation.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    void shouldFindTwoAccounts() {
        List<UserAccount> allAccounts = userAccountRepository.findAll();
        assertEquals(2, allAccounts.size());
    }

    @Test
    void shouldFindUserAccountById() {
        Optional<UserAccount> optionalAccountById = userAccountRepository.findById(1L);
        assertEquals("Kiryl", optionalAccountById.orElseThrow().getUserName());
    }

    @Test
    void shouldSaveNewUserAccount() {
        int sizeBeforeSaving = userAccountRepository.findAll().size();
        UserAccount newAccount = buildNewUserAccount();
        userAccountRepository.save(newAccount);
        int sizeAfterSaving = userAccountRepository.findAll().size();
        assertEquals(++sizeBeforeSaving, sizeAfterSaving);
    }

    @Test
    void shouldRemoveUserAccount() {
        int sizeBeforeRemoving = userAccountRepository.findAll().size();
        userAccountRepository.deleteById(1L);
        int sizeAfterSaving = userAccountRepository.findAll().size();
        assertEquals(--sizeBeforeRemoving, sizeAfterSaving);
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
