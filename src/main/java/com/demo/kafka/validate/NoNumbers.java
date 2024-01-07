package com.demo.kafka.validate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface NoNumbers {
    String message() default "Field should not contain numeric characters";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
