package rnk.l10.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserInfo implements Serializable {
    private String login;
    private Boolean authenticated;
}
