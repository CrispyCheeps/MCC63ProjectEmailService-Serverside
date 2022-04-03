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
    @Scheduled(fixedRate = 60000L)
    public void runScheduler() {
        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // sampai menitnya doang
//        LocalDateTime formatDateTime = now.format(dateTimeFormatter);

        ScheduleEmail scheduleEmail = scheduleEmailRepository.findByTanggalKirim(now);
        System.out.println(scheduleEmail.getKonten().getId());

//        if(scheduleEmailRepository.findByTanggalKirim(now) != null){
//
////            emailService.sendTemplateEmailToListParticipant(scheduleEmail.getEmailListName().getId(), scheduleEmail.getKonten().getId());
//        }


    }


}
