package rnk.l10.ejb.credits;

import rnk.l10.rest.model.AccountingParams;

import javax.ejb.Remote;
import java.util.List;

public interface RnkCreditAccounterV1 {
    public List<Double> computePayment(AccountingParams params);
}
