package rnk.l14.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.Data;

import javax.servlet.ServletException;

@Data
public class ArticleEntity {
    private String link;
    private String text;

    public JsonElement toJson() throws ServletException {
        try{
            GsonBuilder gb = new GsonBuilder();
            Gson gson = gb.create();
            return gson.toJsonTree(this);
        }catch(Exception ex){
            throw new ServletException(ex.getMessage());
        }
    }

}
