package rnk.l08.client.validation;

import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;
import rnk.l08.shared.dto.StaffDTO;
import rnk.l08.shared.dto.User;

import javax.validation.Validator;

import static rnk.l08.client.gin.SvcInjector.injector;

public final class ValidatorFactory extends AbstractGwtValidatorFactory {

    /**
     * Validator marker for the Validation Sample project. Only the classes and groups listed
     * in the {@link GwtValidation} annotation can be validated.
     */
    @GwtValidation(value={User.class, StaffDTO.class})
    public interface GwtValidator extends Validator {
    }

    @Override
    public AbstractGwtValidator createValidator() {
        return (AbstractGwtValidator)    injector.getValidator();
    }
}
