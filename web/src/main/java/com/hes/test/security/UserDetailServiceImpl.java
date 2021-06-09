package com.hes.test.security;

import com.hes.test.dto.UserAccountDto;
import com.hes.test.service.UserAccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("UserDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserAccountService userAccountService;

    public UserDetailServiceImpl(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountDto userAccountDto = userAccountService.findByUserName(username);
        return UserDetailsImpl.fromUserAccount(userAccountDto);
    }
}
