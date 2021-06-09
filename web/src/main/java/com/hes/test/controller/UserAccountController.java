package com.hes.test.controller;

import com.hes.test.dto.UserAccountDto;
import com.hes.test.service.UserAccountService;
import com.hes.test.service.Utils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    public UserAccountController(UserAccountService userAccountService, Validator validator, PasswordEncoder passwordEncoder) {
        this.userAccountService = userAccountService;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserAccountDto>> showUserAccountList(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @RequestParam(name = "direct", defaultValue = "asc", required = false) String direction,
            @RequestParam(name = "sort", defaultValue = "id", required = false) String sortProperty,
            @RequestParam(name = "name_filter", defaultValue = "", required = false) String userName,
            @RequestParam(name = "role_filter", defaultValue = "", required = false) String role) {

        Pageable pageable = preparePageable(page, size, direction, sortProperty);
        List<UserAccountDto> resultList = userAccountService.findAll(pageable);

        if (!userName.isBlank() && !role.isBlank()) {
            resultList = filterByUserName(userName, pageable)
                    .stream()
                    .filter(userAccountDto -> userAccountDto.getRoleDto().getName().equalsIgnoreCase(role))
                    .collect(Collectors.toList());

        } else if (!userName.isBlank()) {
            resultList = filterByUserName(userName, pageable);

        } else if (!role.isBlank()) {
            resultList = filterByRole(role, pageable);
        }
        return new ResponseEntity<>(resultList, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDto> showUserAccountDetails(@PathVariable(name = "id") Long id) {
        UserAccountDto userAccountDto = userAccountService.findById(id);
        return new ResponseEntity<>(userAccountDto, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccountDto> updateUserAccount(@PathVariable(name = "id") Long id,
                                                            @RequestBody UserAccountDto userAccountDto) {
        userAccountDto.setId(id);
        Utils.validate(userAccountDto, validator);
        encodePassword(userAccountDto);
        UserAccountDto updatedUserAccount = userAccountService.update(userAccountDto);
        return new ResponseEntity<>(updatedUserAccount, ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<UserAccountDto> createUserAccount(@RequestBody UserAccountDto userAccountDto) {
        Utils.validate(userAccountDto, validator);
        encodePassword(userAccountDto);
        Long id = userAccountService.save(userAccountDto);
        UserAccountDto savedUserAccountDto = userAccountService.findById(id);
        return new ResponseEntity<>(savedUserAccountDto, CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable(name = "id") Long id) {
        userAccountService.deleteById(id);
        return new ResponseEntity<>(MessageFormat.format("User has been successfully deleted, id={0}", id),
                ACCEPTED);
    }

    private void encodePassword(UserAccountDto userAccountDto) {
        String password = userAccountDto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        userAccountDto.setPassword(encodedPassword);
    }

    private Pageable preparePageable(int page, int size, String direction, String sortProperty) {
        Sort.Direction direc = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(direc, sortProperty);
        return PageRequest.of(page, size, sort);
    }

    private List<UserAccountDto> filterByRole(String role, Pageable pageRequest) {
        return userAccountService.findByRole(role, pageRequest);
    }

    private List<UserAccountDto> filterByUserName(String userName, Pageable pageRequest) {
        return userAccountService.findByUserNameStartsWith(userName, pageRequest);
    }
}
