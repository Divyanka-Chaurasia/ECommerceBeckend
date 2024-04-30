package com.project.app.validate;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidater.class)
public @interface ImageNameValid {
	String message() default "invalid image name";

	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };

}
