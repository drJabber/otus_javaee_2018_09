package rnk.l10.ejb.snils;

import javax.ejb.Remote;

@Remote
public interface Validator {
    Boolean check(String snils);
}
