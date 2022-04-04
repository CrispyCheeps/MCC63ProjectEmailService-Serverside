package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.ScheduleEmail;
import co.id.emailservice.serverside.model.dto.KontenData;
import co.id.emailservice.serverside.model.dto.ParticipantData;
import co.id.emailservice.serverside.model.dto.ScheduleEmailData;
import co.id.emailservice.serverside.repository.ScheduleEmailRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
public class ScheduleEmailService {

    private ScheduleEmailRepository scheduleEmailRepository;
    private EmailService emailService;
    private ModelMapper modelMapper;
    private KontenService kontenService;
    private EmailListNameService emailListNameService;

    public List<ScheduleEmail> getAll() {
        return scheduleEmailRepository.findAll();
    }

    public ScheduleEmail getById(Long id) {
        return scheduleEmailRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not Found"));
    }

    public ScheduleEmail addSchedule(ScheduleEmailData scheduleEmailData) {
        ScheduleEmail scheduleEmail = new ScheduleEmail();
        scheduleEmail.setEmailListName(emailListNameService.getById(scheduleEmailData.getEmailListNameId()));
        scheduleEmail.setKonten(kontenService.getById(scheduleEmailData.getKontenId()));
        if(scheduleEmailData.getTanggalKirim() == null) {
            emailService.sendTemplateEmailToListParticipant(scheduleEmailData.getEmailListNameId(), scheduleEmailData.getKontenId());
            scheduleEmail.setTanggalKirim(LocalDateTime.now());
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // sampai menitnya doang
            LocalDateTime dateTime = LocalDateTime.parse(scheduleEmailData.getTanggalKirim(), dateTimeFormatter);
            scheduleEmail.setTanggalKirim(dateTime);
        }
        return scheduleEmailRepository.save(scheduleEmail);
    }

//    @Scheduled(cron = "* * * * * ?")
//    public void runScheduler() {
//        LocalDateTime dateTimeNow = LocalDateTime.now();
////        System.out.println("before: " + dateTimeNow);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // sampai menitnya doang
//        String formatDateTimeNow = dateTimeNow.format(dateTimeFormatter);
////        System.out.println("string dateTimeNow: " + formatDateTimeNow);
//        LocalDateTime dateTimeNowAfterFormat = LocalDateTime.parse(formatDateTimeNow, dateTimeFormatter);
////        System.out.println("after: " + dateTimeNowAfter);
//
//        ScheduleEmail scheduledDateTime = scheduleEmailRepository.findByTanggalKirim(dateTimeNowAfterFormat);
//        if (scheduledDateTime != null) {
//            System.out.println(scheduledDateTime.getId()); // emailListNameId
//            System.out.println(scheduledDateTime.getKonten().getId()); // kontenId
//            emailService.sendTemplateEmailToListParticipant(scheduledDateTime.getId(), scheduledDateTime.getKonten().getId());
//        }

//        Optional<ScheduleEmail> scheduledDateTime = scheduleEmailRepository.findByTanggalKirim(dateTimeNow);
//        if (scheduledDateTime.isPresent()) {
//            System.out.println(scheduledDateTime.get().getId());
//            System.out.println(scheduledDateTime.get().getKonten().getId());
//            emailService.sendTemplateEmailToListParticipant(scheduledDateTime.get().getId(), scheduledDateTime.get().getKonten().getId());
//        }
//    }

}