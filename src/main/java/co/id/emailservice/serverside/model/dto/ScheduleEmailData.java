package co.id.emailservice.serverside.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleEmailData {

    //nama attribut hrs sm dengan entity (modelMapper)
    private Long emailListNameId;
    private Long kontenId;
    private String tanggalKirim;

}
