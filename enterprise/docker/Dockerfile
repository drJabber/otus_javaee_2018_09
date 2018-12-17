FROM payara/server-full:5.184

#setup
COPY ./postgresql-42.2.5.jar /opt/payara/appserver/glassfish/domains/production/lib/postgresql.jar
COPY domain.xml /opt/payara/appserver/glassfish/domains/production/config/domain.xml

EXPOSE 42333