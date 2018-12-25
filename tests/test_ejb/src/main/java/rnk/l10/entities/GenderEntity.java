package rnk.l10.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GenderEntity  implements Serializable {
    String id;
    String gender;
}
