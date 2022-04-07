package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.ScheduleEmail;
import co.id.emailservice.serverside.model.dto.KontenData;
import co.id.emailservice.serverside.model.dto.ParticipantData;
import co.id.emailservice.serverside.model.dto.ScheduleEmailData;
import co.id.emailservice.serverside.repository.EmailListNameRepository;
import co.id.emailservice.serverside.repository.KontenRepository;
import co.id.emailservice.serverside.repository.ScheduleEmailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduleEmailService {

    private ScheduleEmailRepository scheduleEmailRepository;
    private EmailService emailService;
    private ModelMapper modelMapper;
    private KontenService kontenService;
    private EmailListNameService emailListNameService;
    private EmailListNameRepository emailListNameRepository;
    private KontenRepository kontenRepository;

    public List<ScheduleEmail> getAll() {
        return scheduleEmailRepository.findAll();
    }

    public ScheduleEmail getById(Long id) {
        return scheduleEmailRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not Found"));
    }


    public ScheduleEmail addSchedule(ScheduleEmailData scheduleEmailData) {
        ScheduleEmail scheduleEmail = new ScheduleEmail();
        scheduleEmail.setEmailListName(emailListNameRepository.findByName(scheduleEmailData.getEmailListName()));
        scheduleEmail.setKonten(kontenRepository.findBySubject(scheduleEmailData.getSubject()));
//        scheduleEmail.setEmailListName(emailListNameService.getById(scheduleEmailData.getEmailListNameId()));
//        scheduleEmail.setKonten(kontenService.getById(scheduleEmailData.getKontenId()));
        if(scheduleEmailData.getTanggalKirim() == null) {
//            emailService.sendTemplateEmailToListParticipant(scheduleEmailData.getEmailListNameId(), scheduleEmailData.getKontenId());
            emailService.sendTemplateEmailToListParticipant(scheduleEmailData.getEmailListName(), scheduleEmailData.getSubject());
            scheduleEmail.setTanggalKirim(LocalDateTime.now());
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // sampai menitnya doang
            LocalDateTime dateTime = LocalDateTime.parse(scheduleEmailData.getTanggalKirim(), dateTimeFormatter);
            scheduleEmail.setTanggalKirim(dateTime);
        }
        return scheduleEmailRepository.save(scheduleEmail);
    }

    @Async
    @Scheduled(fixedRate = 60000L)
    public void runScheduler() {
        LocalDateTime dateTimeNow = LocalDateTime.now();
//        System.out.println("before: " + dateTimeNow);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // sampai menitnya doang
        String formatDateTimeNow = dateTimeNow.format(dateTimeFormatter);

        /*
        if (scheduleEmailRepository.findByTanggalKirim(dateTimeNow) != dateTimeNow) {
            System.out.println("data ada");
        } else {
            System.out.println("data tidak ada");
        }

        List<ScheduleEmail> scheduledDateTime = scheduleEmailRepository.findByTanggalKirim(dateTimeNow);
        for (ScheduleEmail scheduleEmail : scheduledDateTime) {

        }
        System.out.println(scheduledDateTime);

        if (scheduledDateTime != null) {
            System.out.println(scheduledDateTime.getId()); // emailListNameId
            System.out.println(scheduledDateTime.getKonten().getId()); // kontenId
            emailService.sendTemplateEmailToListParticipant(scheduledDateTime.getId(), scheduledDateTime.getKonten().getId());
        }

        Optional<ScheduleEmail> scheduledDateTime = scheduleEmailRepository.findByTanggalKirim(dateTimeNow);
        if (scheduledDateTime.isPresent()) {
            System.out.println(scheduledDateTime.get().getId());
            System.out.println(scheduledDateTime.get().getKonten().getId());
            emailService.sendTemplateEmailToListParticipant(scheduledDateTime.get().getId(), scheduledDateTime.get().getKonten().getId());
        }

         */
        log.info(formatDateTimeNow);
        List<ScheduleEmail> scheduleEmails = scheduleEmailRepository.findByDate(formatDateTimeNow);
        log.info(scheduleEmails.toString());
        if (!scheduleEmails.isEmpty()) {
            for (ScheduleEmail scheduleEmail : scheduleEmails) {
                emailService.sendTemplateEmailToListParticipant(scheduleEmail.getEmailListName().getName(),
                        scheduleEmail.getKonten().getSubject());
                System.out.println("email terkirim wkwk");
            }
        }
//        for (ScheduleEmail scheduleEmail : scheduleEmails) {
//            scheduleEmail.getTanggalKirim();
//        }
//        Logger logger = LoggerFactory.getLogger("SampleLogger");
//        logger.info("check", scheduleEmail);

    }

}

