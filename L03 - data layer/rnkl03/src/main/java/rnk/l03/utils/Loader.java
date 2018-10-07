package rnk.l03.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Data
public class Loader {

    private ArrayList<String> authorities;
    private ArrayList<String> roles;
    private ArrayList<String[]> departaments;
    private ArrayList<String[]> positions;
    private ArrayList<String[]> staff;
    private ArrayList<String[]> role_auth;

    private void loadAuthorities() throws IOException {
        authorities.clear();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try(InputStream stream=Loader.class.getResourceAsStream("/authorities.csv");
            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
                                          .withSkipLines(1)
                                          .withCSVParser(parser).build()
        ){
            String[] csvLine;
            while ((csvLine=reader.readNext())!=null){
                authorities.add(csvLine[1]);
            }
        }
    }

    private void loadRoles() throws IOException {
        roles.clear();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try(InputStream stream=Loader.class.getResourceAsStream("/roles.csv");
            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
                    .withSkipLines(1)
                    .withCSVParser(parser).build()
        ){
            String[] csvLine;
            while ((csvLine=reader.readNext())!=null){
                roles.add(csvLine[0]);
            }
        }
    }

    private void loadRoleAuth() throws IOException {
        role_auth.clear();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try(InputStream stream=Loader.class.getResourceAsStream("/role_auth.csv");
            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
                    .withSkipLines(1)
                    .withCSVParser(parser).build()
        ){
            String[] csvLine;
            while ((csvLine=reader.readNext())!=null){
                role_auth.add(csvLine);
            }
        }
    }


    private void loadPositions() throws IOException {
        positions.clear();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try(InputStream stream=Loader.class.getResourceAsStream("/positions.csv");
            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
                    .withSkipLines(1)
                    .withCSVParser(parser).build()
        ){
            String[] csvLine;
            while ((csvLine=reader.readNext())!=null){
                positions.add(csvLine);
            }
        }
    }

    private void loadDepartaments() throws IOException {
        departaments.clear();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try(InputStream stream=Loader.class.getResourceAsStream("/departaments.csv");
            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
                    .withSkipLines(1)
                    .withCSVParser(parser).build()
        ){
            String[] csvLine;
            while ((csvLine=reader.readNext())!=null){
                departaments.add(csvLine);
            }
        }
    }

    private void loadStaff() throws IOException {
        staff.clear();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try(InputStream stream=Loader.class.getResourceAsStream("/staff.csv");
            CSVReader reader=new CSVReaderBuilder(new InputStreamReader(stream))
                    .withSkipLines(1)
                    .withCSVParser(parser).build()
        ){
            String[] csvLine;
            while ((csvLine=reader.readNext())!=null){
                staff.add(csvLine);
            }
        }
    }


    public Loader(){
        authorities=new ArrayList<String>();
        roles=new ArrayList<String>();
        role_auth=new ArrayList<String[]>();
        positions=new ArrayList<String[]>();
        departaments=new ArrayList<String[]>();
        staff=new ArrayList<String[]>();
    }

    public void loadAll() throws IOException{
        loadAuthorities();
        loadRoles();
        loadRoleAuth();
        loadDepartaments();
        loadPositions();
        loadStaff();

    }

}
