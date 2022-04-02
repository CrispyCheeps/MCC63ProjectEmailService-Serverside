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

    public ScheduleEmail addSchedule(Long emailListNameId, Long kontenId, ScheduleEmailData scheduleEmailData) {
        ScheduleEmail scheduleEmail = modelMapper.map(scheduleEmailData, ScheduleEmail.class);
        scheduleEmail.setId(null);
        scheduleEmail.setEmailListName(emailListNameService.getById(emailListNameId));
        scheduleEmail.setKonten(kontenService.getById(kontenId));
        return scheduleEmailRepository.save(scheduleEmail);
    }

    // Non-Periodic Scheduler
//    @Scheduled
//    public void runScheduler(Long emailListNameId, KontenData kontenData) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(dtf.format(now));
//
//        List<ScheduleEmail> scheduleEmailList = scheduleEmailRepository.findAll();
//
//        for (ScheduleEmail scheduleEmail : scheduleEmailList) {
//            if (scheduleEmail.getTanggalKirim().equals(now)) {
//                emailService.sendTemplateEmailToListParticipant(emailListNameId, kontenData);
//            }
//        }
//
//    }



}
