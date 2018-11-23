package rnk.l10.servlets.ws;

import com.google.gson.JsonArray;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Cache {

    public enum State {NEW,UPDATED};

    private Map<String,JsonArray> cached;
    private Map<String,State> cachedState;
    private Map<String,LoadResult> cachedResult;

    private State newsState=State.NEW;
    private State currenciesState=State.NEW;
    private State statsState=State.NEW;


    public Cache(){
        cached =new HashMap<>();
        cached.put("news",null);
        cached.put("currencies",null);
        cached.put("stats",null);

        cachedState=new HashMap<>();
        cachedState.put("news",State.NEW);
        cachedState.put("currencies",State.NEW);
        cachedState.put("stats",State.NEW);

        cachedResult=new HashMap<>();
        cachedResult.put("news",null);
        cachedResult.put("currencies",null);
        cachedResult.put("stats",null);

    }

    public void reset(){
        cached.put("news",null);
        cached.put("currencies",null);
        cached.put("stats",null);
    }

    public void resesState(){
        cachedState.put("news",State.NEW);
        cachedState.put("currencies",State.NEW);
        cachedState.put("stats",State.NEW);

        cachedResult.put("news",null);
        cachedResult.put("currencies",null);
        cachedResult.put("stats",null);
    }
}
