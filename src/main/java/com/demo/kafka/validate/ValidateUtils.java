package com.demo.kafka.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidateUtils {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        System.out.println(validate);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Dog {
        @NoNumbers
        private String name;
        private int age;
    }
}
