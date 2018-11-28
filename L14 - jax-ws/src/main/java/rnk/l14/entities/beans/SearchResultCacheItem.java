package rnk.l14.entities.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import rnk.l14.entities.StaffEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResultCacheItem {
    String jpa_query;
    List<StaffEntity> result;
}
