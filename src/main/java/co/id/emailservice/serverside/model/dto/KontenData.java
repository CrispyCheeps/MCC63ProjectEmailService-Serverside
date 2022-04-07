package co.id.emailservice.serverside.model.dto;

import lombok.Data;
import java.text.SimpleDateFormat;

@Data
public class KontenData {
    private String subject;
    private String body;
    private String footer;
    private String attachment;
    //tidak bisa mengandung nama attribut "id" karena di service akan bingung setId nya (konten.getId, userId, atau templateId)
    private String userName;
    private Long template;
}
