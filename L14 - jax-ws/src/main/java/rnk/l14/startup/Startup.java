package rnk.l14.startup;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Startup{
    private Integer initialized=0;
    private Integer destroyed=0;
};

