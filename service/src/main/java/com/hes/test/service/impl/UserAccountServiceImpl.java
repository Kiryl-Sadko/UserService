package com.hes.test.service.impl;

import com.hes.test.converter.UserAccountConverter;
import com.hes.test.dto.UserAccountDto;
import com.hes.test.entity.Role;
import com.hes.test.entity.UserAccount;
import com.hes.test.exception.InvalidDtoException;
import com.hes.test.exception.SuchEntityAlreadyExistException;
import com.hes.test.repository.UserAccountRepository;
import com.hes.test.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger LOGGER = LogManager.getLogger(UserAccountServiceImpl.class);
    private final UserAccountRepository repository;
    private final Validator validator;
    private final UserAccountConverter converter;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository repository, Validator validator, UserAccountConverter converter) {
        this.repository = repository;
        this.validator = validator;
        this.converter = converter;
    }

    @Override
    public List<UserAccountDto> findAll(Pageable pageable) {
        List<UserAccount> userAccounts = repository.findAll(pageable).getContent();
        return getUserAccountDtoList(userAccounts);
    }

    @Override
    public List<UserAccountDto> findAll() {
        List<UserAccount> userAccounts = repository.findAll();
        return getUserAccountDtoList(userAccounts);
    }

    @Override
    public UserAccountDto findById(Long id) {
        Optional<UserAccount> optionalUserAccount = repository.findById(id);
        UserAccount userAccount = optionalUserAccount
                .orElseThrow(EntityNotFoundException::new);
        return converter.convertToDto(userAccount);
    }

    @Override
    public void deleteById(Long id) {
        Optional<UserAccount> optionalUserAccount = repository.findById(id);
        if (optionalUserAccount.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("UserAccount with id={} has been successfully deleted", id);
        } else {
            String message = MessageFormat.format("UserAccount with id={0} hasn't found", id);
            LOGGER.error(message);
            throw new EntityNotFoundException(message);
        }
    }

    @Override
    public UserAccountDto update(UserAccountDto dto) {
        Long id = dto.getId();
        if (id == null) {
            LOGGER.info("UserAccount with id={0} not found. The UserAccount will be created instead of updating");
            id = this.save(dto);
            return findById(id);
        }

        validate(dto, validator);
        UserAccount userAccountFromDto = converter.convertToEntity(dto);
        updateRelationWithRole(userAccountFromDto);
        UserAccount savedUserAccount = repository.save(userAccountFromDto);
        return converter.convertToDto(savedUserAccount);
    }

    @Override
    public Long save(UserAccountDto dto) {
        validate(dto, validator);
        UserAccount userAccountFromDto = converter.convertToEntity(dto);
        updateRelationWithRole(userAccountFromDto);
        checkForExistenceInDB(userAccountFromDto);
        UserAccount savedUserAccount = repository.save(userAccountFromDto);
        LOGGER.info("UserAccount with id={} has been successfully saved in the data base",
                savedUserAccount.getId());
        return savedUserAccount.getId();
    }

    private List<UserAccountDto> getUserAccountDtoList(List<UserAccount> userAccounts) {
        List<UserAccountDto> resultList = new ArrayList<>();
        userAccounts.forEach(userAccount -> resultList
                .add(converter.convertToDto(userAccount)));
        return resultList;
    }

    private void updateRelationWithRole(UserAccount userAccount) {
        if (userAccount.getRole() != null) {
            Role role = userAccount.getRole();
            role.linkWithUserAccount(userAccount);
        }
    }

    private void validate(UserAccountDto dto, Validator validator) {
        Set<ConstraintViolation<UserAccountDto>> violations = validator.validate(dto);
        if (!CollectionUtils.isEmpty(violations)) {
            StringBuilder causes = new StringBuilder();
            violations.stream().iterator().forEachRemaining(violation -> causes
                    .append("'")
                    .append(violation.getPropertyPath().toString())
                    .append("' ")
                    .append(violation.getMessage())
                    .append(";"));
            String message = MessageFormat.format("The userAccount is invalid, check causes: {0}", causes);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }

    private void checkForExistenceInDB(UserAccount userAccount) {
        Long id = userAccount.getId();
        if (id != null) {
            Optional<UserAccount> optional = repository.findById(id);
            if (optional.isPresent()) {
                String message = MessageFormat
                        .format("The userAccount with id = {0} already exists.", id);
                LOGGER.error(message);
                throw new SuchEntityAlreadyExistException(message);
            }
        }
    }
}
