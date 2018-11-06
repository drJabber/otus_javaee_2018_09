package rnk.l10.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HashedPasswordEntity{
    private String passwdhash;
    private String passwdsalt;
}
