package rnk.l08.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(prefix="")
public class SessionInfo{
    private Integer isValid;
    private Timestamp expire;
}
