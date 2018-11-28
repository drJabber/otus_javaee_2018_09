package rnk.l10.utils;

public class StaffUtils{
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    
    Double getMaxSalary() throws RnkWebServiceException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("select max(staff.salary) from StaffEntity staff ");

            transaction.commit();
        }catch(Exeption ex){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    };

    Double getAvgSalary() throws RnkWebServiceException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("select avg(staff.salary) from StaffEntity staff ");
            
            return q.getResultList();

            transaction.commit();
        }catch(Exeption ex){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    };

    String getPersonWithMaxSalary()throws RnkWebServiceException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("get_max_salary_fio")
                    .registerStoredProcedureParameter(0,
                            String.class, ParameterMode.OUT);
            q.execute();

            String mx= q.getOutputParameterValue(0).toString();

            return mx;

            transaction.commit();
        }catch(Exeption ex){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    };
}

public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            if (request.getParameterMap().containsKey("get_max_salary_fio")){
                StoredProcedureQuery q = em
                        .createStoredProcedureQuery("get_max_salary_fio")
                        .registerStoredProcedureParameter(0,
                                String.class, ParameterMode.OUT);
                q.execute();

                String mx= q.getOutputParameterValue(0).toString();

                response.setCharacterEncoding("utf-8");
                response.getWriter().println(mx.split(" ")[0]);


            }else
            {
                Query q = em.createQuery(
                        "select staff "+
                                "from StaffEntity staff "+
                                "order by staff.id desc");
                List<StaffEntity> result = q.getResultList();

                response.setCharacterEncoding("utf-8");
                try (PrintWriter pw = response.getWriter()){
                    for (StaffEntity e : result){
                        String s="";
                        RoleEntity role=e.getRole();
                        Set<AuthorityEntity> auths=role.getAuthorities();
                        for (AuthorityEntity auth: auths) {
                            s=s+auth.getAuthority()+", ";
                        }
                        if (!s.isEmpty())
                        {
                            s=s.substring(0,s.length()-2);
                        }
                        pw.println(String.format("%d - %s: %s at %s; role: %s(%s)",e.getId(),e.getFio(),e.getPosition().getPosition(),e.getDepartament().getDepartament(),role.getRole(),s));
                    }
                }
            }
            transaction.commit();
        }
        catch (Exception e){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    }

    private byte[] get_salt() throws NoSuchAlgorithmException {
        return SecureRandom.getInstance("SHA1PRNG").generateSeed(32);
    }
