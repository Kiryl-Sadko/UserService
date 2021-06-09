package com.hes.test.service;

import com.hes.test.dto.UserAccountDto;
import com.hes.test.exception.InvalidDtoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.Set;

public final class Utils {

    private static final Logger LOGGER = LogManager.getLogger(Utils.class);

    public static void validate(UserAccountDto userAccountDto, Validator validator) {
        Set<ConstraintViolation<UserAccountDto>> violations = validator.validate(userAccountDto);
        if (!CollectionUtils.isEmpty(violations)) {
            StringBuilder causes = new StringBuilder();
            violations.stream().iterator().forEachRemaining(violation -> causes
                    .append("'")
                    .append(violation.getPropertyPath().toString())
                    .append("' ")
                    .append(violation.getMessage())
                    .append(";"));
            String message = MessageFormat.format("The dto is invalid, check causes: {0}", causes);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }
}
