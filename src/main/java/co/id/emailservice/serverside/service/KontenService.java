package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.Konten;
import co.id.emailservice.serverside.model.dto.KontenData;
import co.id.emailservice.serverside.repository.KontenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class KontenService {

    private KontenRepository kontenRepository;
    private ModelMapper modelMapper;
    private TemplateService templateService;

    @Autowired
    public KontenService(KontenRepository kontenRepository, ModelMapper modelMapper,
                         TemplateService templateService) {
        this.kontenRepository = kontenRepository;
        this.modelMapper = modelMapper;
        this.templateService = templateService;
    }

    public List<Konten> getAll() {
        return kontenRepository.findAll();
    }

    public Konten getById(Long id) {
        return kontenRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Konten not found"));
    }

    public Konten create(KontenData kontenData) {
        Konten konten = modelMapper.map(kontenData, Konten.class);
        konten.setId(null);
        konten.setTemplate(templateService.getById(kontenData.getTemplate()));
        if (konten.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Konten ID already exist");
        }
        return kontenRepository.save(konten);
    }

    /*Kemungkinan waktu edit2 kontennya pake method update ini
      karena kan udh ada kontennya di dlm template td, tinggal user edit2 isi konten aja
     */
    public Konten update(Long id, Konten konten) {
        getById(id); //biar gausa buat method lg kan udh ada method panggil id diatas
        konten.setId(id);
        return kontenRepository.save(konten);
    }

    public Konten delete(Long id) {
        Konten region = getById(id);
        kontenRepository.delete(region);
        return region;
    }
}
