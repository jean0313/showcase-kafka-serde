package com.demo.kafka.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.Set;

public class ValidateUtils {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        System.out.println(validate);
    }


    public static void main(String[] args) {
        Dog pop = new Dog("pop", 5);
        validate(pop);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Dog {
        @Size(max = 10, min = 3)
        private String name;
        @Max(10)
        private int age;
    }
}
