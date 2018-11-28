package rnk.l14.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import rnk.l14.entities.json.DepartamentEntityJsonAdapter;
import rnk.l14.entities.json.PositionEntityJsonAdapter;
import rnk.l14.entities.json.RoleEntityJsonAdapter;
import rnk.l14.entities.xml.DepartamentEntityAdapter;
import rnk.l14.entities.xml.PositionEntityAdapter;
import rnk.l14.entities.xml.RoleEntityAdapter;
import rnk.l14.utils.Finder;

import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
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
    @Column(name = "birth_date", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @XmlAttribute(required = true)
    private Date birthDate;

    @NotNull
    @Column(name = "gender")
    @XmlAttribute(required = true)
    private String gender;

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

//    public StaffEntity(StaffDTO dto) throws ServletException {
//        Finder finder=new Finder();
//        this.id=dto.getId();
//        this.fio=dto.getFio();
//        this.position=finder.findPosition(dto.getPosition());
//        this.departament=finder.findDepartament(dto.getDepartament());
//        this.role=finder.findRole(dto.getRole());
//        this.salary=dto.getSalary();
//        this.login=dto.getLogin();
//        if (dto.getCreatePassword()==1){
//            PasswordHelper helper=new PasswordHelper();
//            HashedPasswordEntity hp=helper.hashPassword(dto.getPassword());
//            this.passwd_hash=hp.getPasswdhash();
//            this.passwd_salt=hp.getPasswdsalt();
//        }else{
//            this.passwd_hash=dto.getPasswd_hash();
//            this.passwd_salt=dto.getPasswd_salt();
//        }
//    }
//
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
