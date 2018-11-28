package rnk.l14.entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name="stats_records",schema="public")
public class StatsEntity {
    @Id
    @Column(name="id")
    private Integer id;

    @Size(max=30)
    @Column(name="token")
    private String token;

    @Column(name="date")
    private Date date;

    @Column(name="urn")
    private String urn;

    @Column(name="user")
    private String user;

    @Column(name="country")
    private String country;

    @Column(name="ip")
    private String ip;

    @Column(name="searchedfor")
    private String searchedFor;
}
