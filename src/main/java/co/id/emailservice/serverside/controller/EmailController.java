package co.id.emailservice.serverside.controller;

import co.id.emailservice.serverside.model.Konten;
import co.id.emailservice.serverside.model.dto.KontenData;
import co.id.emailservice.serverside.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("{emailListNameId}/sendAll/{kontenId}")
    public ResponseEntity<String> sendTemplateEmailToListParticipant(@PathVariable Long emailListNameId, @PathVariable Long kontenId) {
        return new ResponseEntity(emailService.sendTemplateEmailToListParticipant(emailListNameId, kontenId), HttpStatus.OK);
    }

}
