package co.id.emailservice.serverside.controller;

import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.dto.ParticipantData;
import co.id.emailservice.serverside.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    private ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<List<Participant>> getAll() {
        return new ResponseEntity(participantService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> getById(@PathVariable Long id) {
        return new ResponseEntity(participantService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Participant> create(@RequestBody ParticipantData participantData) {
        return new ResponseEntity(participantService.create(participantData), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participant> update(@PathVariable Long id, @RequestBody Participant participant) {
        return new ResponseEntity(participantService.update(id, participant), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Participant> delete(@PathVariable Long id) {
        return new ResponseEntity(participantService.delete(id), HttpStatus.OK);
    }

}
