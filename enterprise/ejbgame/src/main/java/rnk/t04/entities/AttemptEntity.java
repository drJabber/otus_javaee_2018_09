package rnk.t04.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_attempt", schema="game")
public class AttemptEntity {
    @Id
    @SequenceGenerator(name="user_attempt_id_seq",sequenceName = "user_attempt_id_seq", schema = "game", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_attempt_id_seq")
    @Column(name="id")
    Integer id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    UserEntity user;

    @Column(name="number")
    Integer number;

    @Column(name = "result")
    Boolean result;

    @Column(name="secret")
    Integer secret;
}
