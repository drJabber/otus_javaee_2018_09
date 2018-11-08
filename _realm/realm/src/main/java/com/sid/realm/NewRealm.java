package com.sid.realm;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.log4j.Logger;
/*
 * @author siddhartha
 */
public class NewRealm extends RealmBase {
 
 private String username;
 private String password;
 private static final Logger logger = Logger.getLogger(NewRealm.class);

 @Override
 public Principal authenticate(String username, String credentials) {

 this.username = username;
 this.password = credentials;
 logger.info("Authentication is taking place with userid: "+username);
 /* authentication just check the username and password is same*/
 if (this.username.equals(this.password)) {
   return getPrincipal(username);
  }else{
   return null;
  }
 }
 @Override
 protected String getName() {
  return username;
 }

 @Override
 protected String getPassword(String username) {
  return password;
 }

 @Override
 protected Principal getPrincipal(String string) {
  List<String> roles = new ArrayList<String>();
  roles.add("TomcatAdmin");  // Adding role "TomcatAdmin" role to the user
  logger.info("Realm: "+this);
  Principal principal = new GenericPrincipal(username, password,roles);
  logger.info("Principal: "+principal);
  return principal;
 }
}