package co.id.emailservice.serverside.controller;

import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.ScheduleEmail;
import co.id.emailservice.serverside.model.dto.ParticipantData;
import co.id.emailservice.serverside.model.dto.ScheduleEmailData;
import co.id.emailservice.serverside.service.ScheduleEmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduleEmail")
@AllArgsConstructor
public class ScheduleEmailController {

    private ScheduleEmailService scheduleEmailService;

    @GetMapping
    public ResponseEntity<List<ScheduleEmail>> getAll() {
        return new ResponseEntity(scheduleEmailService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleEmail> getById(@PathVariable Long id) {
        return new ResponseEntity(scheduleEmailService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    //dihapus pathVariabelnya bi ini krn nggaboleh ada business meaning di endpoint
    public ResponseEntity<ScheduleEmail> addSchedule(@RequestBody ScheduleEmailData scheduleEmailData) {
        return new ResponseEntity(scheduleEmailService.addSchedule(scheduleEmailData), HttpStatus.CREATED);
    }

}
