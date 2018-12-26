package rnk.t04.ejb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAttempt implements Serializable {
    private Integer attemptNumber;
    private Boolean result;
    private Integer secret;
}
