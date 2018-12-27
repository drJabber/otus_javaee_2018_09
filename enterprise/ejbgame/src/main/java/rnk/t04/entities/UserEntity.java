package rnk.t04.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user", schema="game")
public class UserEntity {
    @Id
    @SequenceGenerator(name="user_id_seq",sequenceName = "user_id_seq", schema = "game", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @Column(name="id")
    Integer id;

    @NotNull
    @Column(name="login")
    String login;

    @NotNull
    @Column(name="suspended")
    Boolean suspended;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    List<AttemptEntity> attempts=new ArrayList<>();
}
