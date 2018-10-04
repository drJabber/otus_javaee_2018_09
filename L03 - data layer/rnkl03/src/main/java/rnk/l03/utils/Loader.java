package rnk.l03.utils;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Loader {

    private ArrayList<String> authorities;

    private void loadAuthorities() throws IOException {
        authorities.clear();
        InputStream stream=Loader.class.getResourceAsStream("/authorities.csv");
        try(
            CSVReader reader=new CSVReader(new InputStreamReader(stream),';')){
            String[] csvLine;

            reader.readNext();//skip header
            while ((csvLine=reader.readNext())!=null){
                authorities.add(csvLine[1]);
            }
        }
    }

    public Loader(){
        authorities=new ArrayList<String>();
    }

    public void loadAll() throws IOException{
        loadAuthorities();
    }

    public ArrayList<String> getAuthorities(){
        return authorities;
    }
}
