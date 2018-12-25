package rnk.l10.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleAuthPair  implements Serializable {
    private Integer first;
    private Integer second;

}
