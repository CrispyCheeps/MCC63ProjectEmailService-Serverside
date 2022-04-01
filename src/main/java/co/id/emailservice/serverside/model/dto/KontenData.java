package co.id.emailservice.serverside.model.dto;

import lombok.Data;
import java.text.SimpleDateFormat;

@Data
public class KontenData {
    private String subject;
    private String body;
    private String footer;
    private String attachment;
    private String scheduleEmail;
    //tidak bisa mengandung nama attribut "id" karena di service akan bingung setId nya (konten.getId, userId, atau templateId)
    private Long user;
    private Long template;
}
