package rnk.l10.utils;

//import com.opencsv.CSVReader;
//import com.opencsv.CSVReaderBuilder;
//import com.opencsv.CSVParser;
//import com.opencsv.CSVParserBuilder;
import lombok.Data;
//import rnk.l10.entities.*;

import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;

@Data
public class Loader {

//    private List<AuthorityEntity> authorities;
//    private List<RoleEntity> roles;
//    private List<DepartamentEntity> departaments;
//    private List<PositionEntity> positions;
//    private List<StaffEntity> staff;
//    private List<RoleAuthPair> role_auth;
//
    private void loadAuthorities() throws IOException {
//        authorities.clear();
//        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
//        try(InputStream stream=Loader.class.getResourceAsStream("/authorities.csv");
//            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
//                                          .withSkipLines(1)
//                                          .withCSVParser(parser).build()
//        ){
//            String[] csvLine;
//            while ((csvLine=reader.readNext())!=null){
//                AuthorityEntity a=new AuthorityEntity();
//                a.setAuthority(csvLine[1]);
//                authorities.add(a);
//            }
//        }
    }

    private void loadRoles() throws IOException {
//        roles.clear();
//        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
//        try(InputStream stream=Loader.class.getResourceAsStream("/roles.csv");
//            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
//                    .withSkipLines(1)
//                    .withCSVParser(parser).build()
//        ){
//            String[] csvLine;
//            while ((csvLine=reader.readNext())!=null){
//                RoleEntity r=new RoleEntity();
//                r.setRole(csvLine[0]);
//                roles.add(r);
//            }
//        }
    }

    private void loadRoleAuth() throws IOException {
//        role_auth.clear();
//        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
//        try(InputStream stream=Loader.class.getResourceAsStream("/role_auth.csv");
//            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
//                    .withSkipLines(1)
//                    .withCSVParser(parser).build()
//        ){
//            String[] csvLine;
//            while ((csvLine=reader.readNext())!=null){
//                RoleAuthPair p=new RoleAuthPair();
//                p.setFirst(Integer.parseInt(csvLine[0]));
//                p.setSecond(Integer.parseInt(csvLine[1]));
//
//                role_auth.add(p);
//            }
//        }
    }


    private void loadPositions() throws IOException {
//        positions.clear();
//        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
//        try(InputStream stream=Loader.class.getResourceAsStream("/positions.csv");
//            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
//                    .withSkipLines(1)
//                    .withCSVParser(parser).build()
//        ){
//            String[] csvLine;
//            while ((csvLine=reader.readNext())!=null){
//                PositionEntity p=new PositionEntity();
//
//                p.setPosition(csvLine[0]);
//                p.setDefault_dept_id0(Integer.parseInt(csvLine[1]));
//
//                String h=csvLine[2];
//                if (h.isEmpty()){
//                    p.setHead_id0(-1);
//                }else
//                {
//                    p.setHead_id0(Integer.parseInt(h));
//                }
//                p.setDefault_role_id0(Integer.parseInt(csvLine[3]));
//                p.setDefault_salary(Integer.parseInt(csvLine[4]));
//
//                positions.add(p);
//            }
//        }
    }

    private void loadDepartaments() throws IOException {
//        departaments.clear();
//        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
//        try(InputStream stream=Loader.class.getResourceAsStream("/departaments.csv");
//            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
//                    .withSkipLines(1)
//                    .withCSVParser(parser).build()
//        ){
//            String[] csvLine;
//            while ((csvLine=reader.readNext())!=null){
//                DepartamentEntity d=new DepartamentEntity();
//                d.setDepartament(csvLine[0]);
//
//                String h=csvLine[1];
//                if (h.isEmpty()){
//                    d.setHead_dept_id0(-1);
//                }else
//                {
//                    d.setHead_dept_id0(Integer.parseInt(h));
//                }
//
//                h=csvLine[2];
//                if (h.isEmpty()){
//                    d.setHead_of_dept_id0(-1);
//                }else
//                {
//                    d.setHead_of_dept_id0(Integer.parseInt(h));
//                }
//
//                d.setTown(csvLine[3]);
//
//                departaments.add(d);
//            }
//        }
    }

    private void loadStaff() throws IOException {
//        staff.clear();
//        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
//        try(InputStream stream=Loader.class.getResourceAsStream("/staff.csv");
//            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
//                    .withSkipLines(1)
//                    .withCSVParser(parser).build()
//        ){
//            String[] csvLine;
//            while ((csvLine=reader.readNext())!=null){
//                StaffEntity s=new StaffEntity();
//                s.setFio(csvLine[0]);
//                s.setPosition_id0(Integer.parseInt(csvLine[1]));
//                s.setDepartament_id0(Integer.parseInt(csvLine[2]));
//                s.setSalary(Integer.parseInt(csvLine[3]));
//                s.setRole_id0(Integer.parseInt(csvLine[4]));
//                s.setLogin(csvLine[5]);
//                s.setPasswd_hash(csvLine[6]);
//                s.setPasswd_salt(csvLine[7]);
//                staff.add(s);
//            }
//        }
    }


    public Loader(){
//        authorities=new ArrayList<AuthorityEntity>();
//        roles=new ArrayList<RoleEntity>();
//        role_auth=new ArrayList<RoleAuthPair>();
//        positions=new ArrayList<PositionEntity>();
//        departaments=new ArrayList<DepartamentEntity>();
//        staff=new ArrayList<StaffEntity>();
    }

    public void loadAll() throws IOException{
//        loadAuthorities();
//        loadRoles();
//        loadRoleAuth();
//        loadDepartaments();
//        loadPositions();
//        loadStaff();
//
    }

}
