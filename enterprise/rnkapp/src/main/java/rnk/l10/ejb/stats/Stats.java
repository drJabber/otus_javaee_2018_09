package rnk.l10.ejb.stats;

import org.apache.log4j.Logger;

import javax.ejb.*;
import javax.persistence.*;
import javax.servlet.ServletException;

@Singleton
@Startup
@Lock(LockType.WRITE)
public class Stats implements IStats{
    private static final Logger logger = Logger.getLogger(Stats.class.getName());

    @PersistenceContext(unitName = "RNK_PU")
    private EntityManager em;

    public Stats(){
        logger.warn("visited stats");
    }

    @Override
    public Integer store(String token, Object stats) throws ServletException{
        logger.info("before store_stats stores data");
        StoredProcedureQuery q = em
                .createStoredProcedureQuery("public.add_stats")
                .registerStoredProcedureParameter("p_token",String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_value",String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("o_id", Integer.class, ParameterMode.OUT);

        q.setParameter("p_token",token);
        q.setParameter("p_value",stats);
        q.execute();

        Object stats_id=q.getOutputParameterValue("o_id");

            logger.info("after store_stats stores data");
        return Integer.parseInt(stats_id.toString());
    }

    @Override
    public void toggle(String subject, String sw) throws ServletException{
        StoredProcedureQuery q = em
                .createStoredProcedureQuery("public.switch_stats")
                .registerStoredProcedureParameter("p_token",String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_switch",String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("o_result",String.class, ParameterMode.OUT)
                ;

        q.setParameter("p_token",subject);
        q.setParameter("p_switch",sw);
        q.execute();

        String r= q.getOutputParameterValue("o_result").toString();
        if (r.equals("fail")){
            throw new ServletException(String.format("switch % fails", subject));
        }
    }

    @Override
    public String get(String subject) throws ServletException{
        StoredProcedureQuery q = em
                .createStoredProcedureQuery("public.get_stats_switch")
                .registerStoredProcedureParameter("p_token",String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("o_switch", String.class, ParameterMode.OUT);

        q.setParameter("p_token",subject);
        q.execute();

        return q.getOutputParameterValue("o_switch").toString();
    }

    @Override
    public String control(String service_password,String password, String command, String subject) throws  ServletException{
        if (service_password.equals(password)){
            switch (command){
                case "stats_on":{
                    toggle(subject,"on");
                    return "on";
                }
                case "stats_off":{
                    toggle(subject,"off");
                    return "off";
                }
                case "stats_get":{
                    return get(subject);
                }
                default:{
                    return "fail";
                }
            }
        }else{
            return "fail";
        }
    }
}
