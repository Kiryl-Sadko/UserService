package com.hes.test.controller;

import com.hes.test.dto.RoleDto;
import com.hes.test.dto.UserAccountDto;
import com.hes.test.security.AuthenticationRequest;
import com.hes.test.security.JwtTokenProvider;
import com.hes.test.service.UserAccountService;
import com.hes.test.service.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserAccountService userAccountService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public AuthenticationController(AuthenticationManager authenticationManager, UserAccountService userAccountService,
                                    JwtTokenProvider jwtTokenProvider,
                                    PasswordEncoder passwordEncoder,
                                    Validator validator) {
        this.authenticationManager = authenticationManager;
        this.userAccountService = userAccountService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            String userName = authenticationRequest.getUserName();
            String password = authenticationRequest.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            ResponseEntity<String> responseEntity = checkUserActivation(userName);
            if (responseEntity != null) return responseEntity;

            String token = jwtTokenProvider.createToken(userName);

            Map<Object, Object> response = new HashMap<>();
            response.put("UserName", userName);
            response.put("Token", token);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid login/password combination", HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity<String> checkUserActivation(String userName) {
        UserAccountDto accountByUserName = userAccountService.findByUserName(userName);
        if (!accountByUserName.getStatus().isActive()) {
            return new ResponseEntity<>("The user is inactive, you should contact the administrator",
                    HttpStatus.LOCKED);
        }
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserAccountDto userAccountDto) {
        Utils.validate(userAccountDto, validator);
        String encodedPassword = passwordEncoder.encode(userAccountDto.getPassword());
        userAccountDto.setPassword(encodedPassword);
        userAccountDto.setRoleDto(RoleDto.USER);
        userAccountService.save(userAccountDto);
        String message = MessageFormat.format("User {0} has been successfully signup!", userAccountDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
