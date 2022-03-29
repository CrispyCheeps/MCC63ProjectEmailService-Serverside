package co.id.emailservice.serverside.controller;

import co.id.emailservice.serverside.model.Template;
import co.id.emailservice.serverside.model.dto.TemplateData;
import co.id.emailservice.serverside.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
public class TemplateController {

    private TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public ResponseEntity<List<Template>> getAll() {
        return new ResponseEntity(templateService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Template> getById(@PathVariable Long id) {
        return new ResponseEntity<>(templateService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Template> create(@RequestBody TemplateData templateData) {
        return new ResponseEntity(templateService.create(templateData), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Template> update(@PathVariable Long id, @RequestBody Template template) {
        return new ResponseEntity(templateService.update(id, template), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Template> delete(@PathVariable Long id) {
        return new ResponseEntity(templateService.delete(id), HttpStatus.OK);
    }
}
