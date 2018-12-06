package rnk.l10.rest;

import rnk.l10.rest.model.AccountingParams;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.util.List;

public interface RnkCreditAccounter {
    public List<Double> computePayment(@Valid @BeanParam AccountingParams params);
}
