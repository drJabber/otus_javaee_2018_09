package rnk.l12.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class StatsEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="id")
    private Integer id;
    private String token;
    private Date date;
    private String user;
    private String country;
    private String ip;
    private String searched_for;
}
