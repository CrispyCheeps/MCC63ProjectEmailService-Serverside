package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.config.TemplateEngineConfig;
import co.id.emailservice.serverside.model.EmailListName;
import co.id.emailservice.serverside.model.Konten;
import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.Template;
import co.id.emailservice.serverside.model.dto.KontenData;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender emailSender;
    private SpringTemplateEngine springTemplateEngine;
    private ParticipantService participantService;
    private EmailListNameService emailListNameService;
    private ModelMapper modelMapper;

    /*
            Behavior eksekusi code ada :
             synchronus = seri (antrian, analogi antrian di dokter)
             asynchronus = paralel (analogi pemesanan makanan di restoran antara waitress dgn cheff)
             */
    @Async //Spy eksekusi tdk berhenti di task method dibawah (loncat ke task berikutnya)
    public String sendTemplateEmail(KontenData kontenData, Participant participant) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            Konten konten = modelMapper.map(kontenData, Konten.class);

//            String content = "Dear [[name]],<br>"
//                    + "Please click the link below to verify your registration:<br>"
//                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
//                    + "Thank you,<br>";
//            content = content.replace("[[name]]", employee.getFullName());
//            String verifyURL = "http://localhost:8088/api/employee/verify?username=" + user.getUsername() + "&code=" + user.getVerificationCode();
//
//            content = content.replace("[[URL]]", verifyURL);
            Context context = new Context();
            context.setVariable("konten", konten);

            String content = springTemplateEngine.process("template1", context);
            //ambil dari list scheduling trus dilooping trus dikasih scheduled annotation biar bisa discheduling
            helper.setTo(participant.getEmail());
            //mungkin subject ini bisa ditambah disimpen didb tp gatau taruh mn
            helper.setSubject(konten.getSubject());
            helper.setText(content, true);
            emailSender.send(message);

        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "Check Your Email to Verify Your Account";
    }

//    public EmailData sendListOfEmail(SendToAllData sendToAllData){
//        EmailData emailData = new EmailData();
//        emailData.setSubject(sendToAllData.getSubject());
//        emailData.setBody(sendToAllData.getBody());
//        sendToAllData.getToEmail().forEach( data -> {
//            emailData.setToEmail(data);
//            sendSimpleEmail(emailData);
//        });
//        return emailData;
//    }

    public String sendTemplateEmailToListParticipant(Long emailListNameId, KontenData kontenData) {
        List<Participant> listParticipantEmail = participantService.getFilterParticipantByEmailListNameId(emailListNameId);
        for (Participant participant : listParticipantEmail) {
            sendTemplateEmail(kontenData, participant);
        }
        return "Email terkirim wkwk";
    }


}
