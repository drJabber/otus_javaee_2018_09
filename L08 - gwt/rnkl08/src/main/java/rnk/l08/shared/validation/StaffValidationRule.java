package rnk.l08.shared.validation;

import rnk.l08.shared.dto.StaffDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class StaffValidationRule {


    public static final Validator validator= Validation.buildDefaultValidatorFactory().getValidator();
    public static boolean isValid(StaffDTO staff){
        return getErrors(staff).isEmpty();
    }


    public static Set<ConstraintViolation<StaffDTO>> getErrors(StaffDTO staff){
        return validator.validate(staff);
    }

}
