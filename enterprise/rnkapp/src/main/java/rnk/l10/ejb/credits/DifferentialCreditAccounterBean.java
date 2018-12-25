package rnk.l10.ejb.credits;

import rnk.l10.rest.model.AccountingParams;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless(name="DifferentialCreditAccounter")
public class DifferentialCreditAccounterBean implements RnkCreditAccounterV1 {
    @Override
    public List<Double> computePayment(AccountingParams params) {
        List<Double> result=new ArrayList<>();
        int T=params.getNumberOfPeriods();
        double Kr=params.getAmountOfCredit();
        double R=params.getRate();
        for (int i=0; i<T;i++){
            result.add(Kr*(1+R*(T-i))/T);
        }
        return result;

    }
}
