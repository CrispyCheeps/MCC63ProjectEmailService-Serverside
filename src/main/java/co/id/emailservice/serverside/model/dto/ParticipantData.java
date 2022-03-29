package co.id.emailservice.serverside.model.dto;

import lombok.Data;

@Data
public class ParticipantData {
    private String name;
    private String email;
    private Long emailListNameId;
}
