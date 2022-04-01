package co.id.emailservice.serverside.model.dto;

import co.id.emailservice.serverside.model.EmailListName;
import co.id.emailservice.serverside.model.Konten;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponseData {
    private String email;
    private List<EmailListName> emailListName;
    private List<Konten> konten;
    //kalau ga ada previlege cuma ada role
    private List<String> authorities;
}
