package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.config.TemplateEngineConfig;
import co.id.emailservice.serverside.model.*;
import co.id.emailservice.serverside.model.dto.KontenData;
import co.id.emailservice.serverside.model.dto.ScheduleEmailData;
import co.id.emailservice.serverside.repository.ScheduleEmailRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender emailSender;
    private SpringTemplateEngine springTemplateEngine;
    private ParticipantService participantService;
    private EmailListNameService emailListNameService;
    private ModelMapper modelMapper;
    private ScheduleEmailRepository scheduleEmailRepository;
    private KontenService kontenService;

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

    public ScheduleEmail addSchedule(Long emailListNameId, Long kontenId, ScheduleEmailData scheduleEmailData) {
        ScheduleEmail scheduleEmail = modelMapper.map(scheduleEmailData, ScheduleEmail.class);
        scheduleEmail.setId(null);
        scheduleEmail.setEmailListName(emailListNameService.getById(emailListNameId));
        scheduleEmail.setKonten(kontenService.getById(kontenId));
        return scheduleEmailRepository.save(scheduleEmail);
    }

    public String sendTemplateEmailToListParticipant(Long emailListNameId, KontenData kontenData) {
        List<Participant> listParticipantEmail = participantService.getFilterParticipantByEmailListNameId(emailListNameId);
        for (Participant participant : listParticipantEmail) {
            sendTemplateEmail(kontenData, participant);
        }
        return "Email terkirim wkwk";
    }

    public String sendNow(Long emailListNameId, KontenData kontenData){
        Konten konten = modelMapper.map(kontenData, Konten.class);
        Date now = new Date();
        ScheduleEmail scheduleEmail = new ScheduleEmail();
        scheduleEmail.setId(emailListNameId);
        scheduleEmail.setEmailListName(emailListNameService.getById(emailListNameId));
        scheduleEmail.setKonten(kontenService.getById(konten.getId()));
        scheduleEmail.setTanggalKirim(now);
        scheduleEmailRepository.save(scheduleEmail);

        sendTemplateEmailToListParticipant(emailListNameId, kontenData);

        return "Email terkirim wkwk";
    }

}
