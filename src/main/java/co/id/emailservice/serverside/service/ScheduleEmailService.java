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

    //mskin String date aja bi scheduleEmailData
    /*
    void aja ini bi, krn ga ada kebutuhan.
    ketika null set tgl kirim mjd new date / localDateTime now -> simpan ke db

    void scheduleEmail () {
    1.ketika user tdk menset tgl kirim / null -> kirim email, tgl kirim di-set localDateTime now -> save
    2. ketika tgl kirim nya ada -> save db without send email.
    }


     */
    public ScheduleEmail addSchedule(ScheduleEmailData scheduleEmailData) {
//        ScheduleEmail scheduleEmail = modelMapper.map(scheduleEmailData, ScheduleEmail.class);
        ScheduleEmail scheduleEmail = new ScheduleEmail();
        scheduleEmail.setId(scheduleEmailData.getEmailListNameId());
        //perlu diconvert dr string -> date ya bi yaa
//        scheduleEmail.setTanggalKirim(scheduleEmailData.getTanggalKirim());
        //harus objek bii
        scheduleEmail.setEmailListName(emailListNameService.getById(scheduleEmailData.getEmailListNameId()));
        scheduleEmail.setKonten(kontenService.getById(scheduleEmailData.getKontenId()));
        return scheduleEmailRepository.save(scheduleEmail);
    }

    // Non-Periodic Scheduler
    /*
    Msh blm lengkap, msh ada atribut yg perlu ditambahin (interval)
     */
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
