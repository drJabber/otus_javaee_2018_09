package rnk.l10.rest;

import rnk.l10.rest.model.AccountingParams;

import java.util.List;

public interface RnkCreditAccounter {
    List<Double> computePayment(AccountingParams params);
//    List<Double> computeAnnuityPayment(AccountingParams params);
}
