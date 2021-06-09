package com.hes.test.repository;

import com.hes.test.entity.Role;
import com.hes.test.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserName(String userName);

    Page<UserAccount> findByUserNameStartingWith(String userName, Pageable pageable);

    Page<UserAccount> findByRole(Role role, Pageable pageable);
}
