package com.hes.test.service;

import com.hes.test.dto.UserAccountDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserAccountService extends CRUDService<UserAccountDto> {

    UserAccountDto findByUserName(String userName);

    List<UserAccountDto> findByUserNameStartsWith(String userName, Pageable pageable);

    List<UserAccountDto> findByRole(String roleName, Pageable pageable);
}
