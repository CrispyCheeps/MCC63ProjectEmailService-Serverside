package co.id.emailservice.serverside.controller;

import co.id.emailservice.serverside.model.EmailListName;
import co.id.emailservice.serverside.model.dto.EmailListNameData;
import co.id.emailservice.serverside.service.EmailListNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emailListName")
public class EmailListNameController {

    private EmailListNameService emailListNameService;

    @Autowired
    public EmailListNameController(EmailListNameService emailListNameService) {
        this.emailListNameService = emailListNameService;
    }

    @GetMapping
    public ResponseEntity<List<EmailListName>> getAll() {
        return new ResponseEntity(emailListNameService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailListName> getById(@PathVariable Long id) {
        return new ResponseEntity(emailListNameService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmailListName> create(@RequestBody EmailListNameData emailListNameData) {
        return new ResponseEntity(emailListNameService.create(emailListNameData), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailListName> update(@PathVariable Long id, @RequestBody EmailListName emailListName) {
        return new ResponseEntity(emailListNameService.update(id, emailListName), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmailListName> delete(@PathVariable Long id) {
        return new ResponseEntity(emailListNameService.delete(id), HttpStatus.OK);
    }

}
