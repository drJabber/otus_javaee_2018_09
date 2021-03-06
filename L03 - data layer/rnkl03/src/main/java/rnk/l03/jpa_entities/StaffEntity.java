package rnk.l03.jpa_entities;

import lombok.Data;
import rnk.l03.json.DepartamentEntityJsonAdapter;
import rnk.l03.json.PositionEntityJsonAdapter;
import rnk.l03.json.RoleEntityJsonAdapter;
import rnk.l03.xml.DepartamentEntityAdapter;
import rnk.l03.xml.PositionEntityAdapter;
import rnk.l03.xml.RoleEntityAdapter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Set;

@Data
@XmlRootElement(name="employee")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="staff",schema="public")
public class StaffEntity {
    @Id
    @SequenceGenerator(name="staff_id_seq",sequenceName = "staff_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="staff_id_seq")
    @Column(name="id",updatable=false)
    @XmlAttribute(required = true)
    private Integer id;

    @NotNull
    @Size(max=500)
    @Column(name="fio")
    @XmlAttribute(required = true)
    private String fio;

    @ManyToOne
    @JoinColumn(name="position_id")
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(PositionEntityAdapter.class)
    @JsonbTypeAdapter(PositionEntityJsonAdapter.class)
    private PositionEntity position;

    @ManyToOne
    @JoinColumn(name="departament_id")
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(DepartamentEntityAdapter.class)
    @JsonbTypeAdapter(value=DepartamentEntityJsonAdapter.class)
    private DepartamentEntity departament;

    @ManyToOne
    @JoinColumn(name="role_id")
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(RoleEntityAdapter.class)
    @JsonbTypeAdapter(RoleEntityJsonAdapter.class)
    private RoleEntity role;


    @Column(name="salary")
    @XmlAttribute(required = true)
    private Integer salary;

    @NotNull
    @Size(max=200)
    @Column(name="login")
    @XmlElement(required = true)
    private String login;

    @NotNull
    @Size(max=500)
    @Column(name="passwd_hash")
    @XmlElement(required = true)
    private String passwd_hash;

    @NotNull
    @Size(max=100)
    @Column(name="passwd_salt")
    @XmlElement(required = true)
    private String passwd_salt;

    @JsonbTransient
    private Integer position_id0;

    @JsonbTransient
    private Integer departament_id0;

    @JsonbTransient
    private Integer role_id0;


    @Override
    public String toString(){
        String s="";
        Set<AuthorityEntity> auths=role.getAuthorities();
        for (AuthorityEntity auth: auths) {
            s=s+auth.getAuthority()+", ";
        }
        if (!s.isEmpty())
        {
            s=s.substring(0,s.length()-2);
        }
        return String.format("%d - %s: %s at %s; role: %s(%s)",id,fio,position.getPosition(),departament.getDepartament(),role.getRole(),s);
    }

}
