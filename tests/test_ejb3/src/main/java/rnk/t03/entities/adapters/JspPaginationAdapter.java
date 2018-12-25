package rnk.t03.entities.adapters;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import java.util.List;

public class JspPaginationAdapter implements PaginatedList{
    private final List<Object> list;
    private Integer page;
    private Integer page_size;
    private Integer full_size;
    private String sort;
    private String direction;


    public JspPaginationAdapter(List list, Integer page, Integer page_size, Integer full_size, String sort, String direction){
        this.list=list;
        this.page=page;
        this.page_size=page_size;
        this.full_size=full_size;
        this.sort=sort==null?"":sort;
        this.direction=direction==null?"":direction.toLowerCase();
    }

    public List<Object> getList(){
        return list;
    }

    public int getPageNumber(){
        return this.page ;
    }

    public int getObjectsPerPage(){
        return this.page_size ;
    }

    public int getFullListSize(){
        return this.full_size;
    }

    public String getSortCriterion(){
        return this.sort;
    }

    public SortOrderEnum getSortDirection(){
        if (this.direction.equals("asc")){
            return SortOrderEnum.ASCENDING;
        }else{
            return SortOrderEnum.DESCENDING;
        }
    }

    public String getSearchId(){
        return null;
    }
}