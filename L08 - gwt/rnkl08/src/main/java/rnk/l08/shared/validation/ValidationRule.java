package rnk.l08.shared.validation;

import rnk.l08.shared.dto.StaffDTO;
import rnk.l08.shared.dto.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationRule {


    public static final Validator validator= Validation.buildDefaultValidatorFactory().getValidator();

    public static boolean isValid(StaffDTO staff){
        return getErrors(staff).isEmpty();
    }
    public static boolean isValid(User user){
        return getErrors(user).isEmpty();
    }


    public static Set<ConstraintViolation<User>> getErrors(User user){
        return validator.validate(user);
    }
    public static Set<ConstraintViolation<StaffDTO>> getErrors(StaffDTO staff){
        return validator.validate(staff);
    }

}
