package com.hes.test.repository;

import com.hes.test.config.PersistenceConfig;
import com.hes.test.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = PersistenceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:init.sql", "classpath:data_generation.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldFindThreeRoles() {
        List<Role> allRoles = roleRepository.findAll();
        assertEquals(3, allRoles.size());
    }

    @Test
    void shouldFindRoleById() {
        Optional<Role> optionalRoleById = roleRepository.findById(1L);
        assertEquals("ADMIN", optionalRoleById.orElseThrow().getName());
    }

    @Test
    void shouldSaveNewRole() {
        int sizeBeforeSaving = roleRepository.findAll().size();
        Role newRole = new Role();
        newRole.setName("New role");
        roleRepository.save(newRole);
        int sizeAfterSaving = roleRepository.findAll().size();
        assertEquals(++sizeBeforeSaving, sizeAfterSaving);
    }

    @Test
    void shouldRemoveRole() {
        int sizeBeforeRemoving = roleRepository.findAll().size();
        roleRepository.deleteById(3L);
        int sizeAfterSaving = roleRepository.findAll().size();
        assertEquals(--sizeBeforeRemoving, sizeAfterSaving);
    }
}