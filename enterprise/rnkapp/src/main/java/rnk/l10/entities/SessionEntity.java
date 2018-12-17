package rnk.l10.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@Entity
@Table(name="session",schema="public")
public class SessionEntity {
    @Id
    @Size(max=60)
    @Column(name="id",updatable=false)
    private String id;

    @ManyToOne
    @NotNull
    @JoinColumn(name="staff_id")
    private StaffEntity staff;

    @Column(name="started")
    private Timestamp started;

    @Column(name="expire")
    private Timestamp expire;

    @Override
    public String toString(){
	String s=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")).format(expire);
        return String.format("%s : %s, expires at %s",staff.getFio(),id,expire);
    }

}
