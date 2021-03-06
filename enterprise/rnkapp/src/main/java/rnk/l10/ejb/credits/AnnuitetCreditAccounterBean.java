package rnk.l10.ejb.credits;

import rnk.l10.rest.model.AccountingParams;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless(name="AnnuitetCreditAccounter")
public class AnnuitetCreditAccounterBean implements RnkCreditAccounterV2 {
    @RolesAllowed({"Accountant"})
    @Override
    public List<Double> computePayment(AccountingParams params) {
        List<Double> result=new ArrayList<>();
        int T=params.getNumberOfPeriods();
        double Kr=params.getAmountOfCredit();
        double R=params.getRate();
        double P=Kr*R/(1-1/Math.pow((1+R),T));
        for (int i=0; i<T;i++){
            result.add(P);
        }
        return result;

    }
}
