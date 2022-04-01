package co.id.emailservice.serverside.controller;

import co.id.emailservice.serverside.model.Konten;
import co.id.emailservice.serverside.model.dto.KontenData;
import co.id.emailservice.serverside.service.KontenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/konten")
public class KontenController {

    private KontenService kontenService;

    @Autowired
    public KontenController(KontenService kontenService) {
        this.kontenService = kontenService;
    }

    @GetMapping
    public ResponseEntity<List<Konten>> getAll() {
        return new ResponseEntity(kontenService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Konten> getById(@PathVariable Long id) {
        return new ResponseEntity(kontenService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Konten> create(@RequestBody KontenData kontenData) {
        return new ResponseEntity(kontenService.create(kontenData), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Konten> update(@PathVariable Long id, @RequestBody Konten konten) {
        return new ResponseEntity(kontenService.update(id, konten), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Konten> delete(@PathVariable Long id) {
        return new ResponseEntity(kontenService.delete(id), HttpStatus.OK);
    }
}
