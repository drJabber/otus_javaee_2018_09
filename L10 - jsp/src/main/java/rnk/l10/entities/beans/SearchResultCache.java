package rnk.l10.entities.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import rnk.l10.entities.StaffEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SearchResultCache {
    Map<String, List<StaffEntity>> cache;

    public SearchResultCache(){
        this.cache=new HashMap<>();
    }

    public List<StaffEntity> getQueryResult(String query){
        return cache.get(query);
    }

    public void addQueryResult(String query, List<StaffEntity> result){
        cache.put(query,result);
    }

    public void removeQueryResult(String query){
        cache.remove(query);
    }

    public void invalidateAll(){
        cache.clear();
    }

    public void invalidateStaff(Integer staffId){
        cache.clear();
    }
}
