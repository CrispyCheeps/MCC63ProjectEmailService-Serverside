package co.id.emailservice.serverside.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantData {
    private String name;
    private String email;
    private Long emailListNameId;
}
