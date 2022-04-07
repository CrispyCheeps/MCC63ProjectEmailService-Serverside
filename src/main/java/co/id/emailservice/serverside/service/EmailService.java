package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.config.TemplateEngineConfig;
import co.id.emailservice.serverside.model.*;
import co.id.emailservice.serverside.model.dto.KontenData;
import co.id.emailservice.serverside.model.dto.ScheduleEmailData;
import co.id.emailservice.serverside.repository.EmailListNameRepository;
import co.id.emailservice.serverside.repository.KontenRepository;
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
    private KontenRepository kontenRepository;
    private EmailListNameRepository emailListNameRepository;

    /*
    Behavior eksekusi code ada :
     synchronus = seri (antrian, analogi antrian di dokter)
     asynchronus = paralel (analogi pemesanan makanan di restoran antara waitress dgn cheff)
     */
    @Async //Spy eksekusi tdk berhenti di task method dibawah (loncat ke task berikutnya)
    public String sendTemplateEmail(String subject, Participant participant) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            Konten konten = kontenRepository.findById(kontenId).get();
            Konten konten = kontenRepository.findBySubject(subject);
            String template = konten.getTemplate().getName();

            Context context = new Context();
            context.setVariable("konten", konten);

            String content = springTemplateEngine.process(template, context);
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

    public String sendTemplateEmailToListParticipant(String emailListName, String subject) {
        EmailListName emailList = emailListNameRepository.findByName(emailListName);
        Konten kontenSubject = kontenRepository.findBySubject(subject);
        List<Participant> listParticipantEmail = participantService.getFilterParticipantByEmailListNameId(emailList.getId());
        for (Participant participant : listParticipantEmail) {
            sendTemplateEmail(kontenSubject.getSubject(), participant);
        }
        return "Email terkirim wkwk";
    }

//    public String sendTemplateEmailToListParticipant(Long emailListNameId, Long kontenId) {
//        List<Participant> listParticipantEmail = participantService.getFilterParticipantByEmailListNameId(emailListNameId);
//        for (Participant participant : listParticipantEmail) {
//            sendTemplateEmail(kontenId, participant);
//        }
//        return "Email terkirim wkwk";
//    }

}
