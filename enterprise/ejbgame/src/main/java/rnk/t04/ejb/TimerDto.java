package rnk.t04.ejb;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TimerDto implements Serializable {
    private String login;
}