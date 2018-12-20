package rnk.l10.ejb.credits;

import rnk.l10.rest.model.AccountingParams;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface RnkCreditAccounter {
    public List<Double> computePayment(AccountingParams params);
}
