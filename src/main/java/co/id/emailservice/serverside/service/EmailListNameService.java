package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.EmailListName;
import co.id.emailservice.serverside.repository.EmailListNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmailListNameService {

    private EmailListNameRepository emailListNameRepository;

    @Autowired
    public EmailListNameService(EmailListNameRepository emailListNameRepository) {
        this.emailListNameRepository = emailListNameRepository;
    }

    public List<EmailListName> getAll() {
        return emailListNameRepository.findAll();
    }

    public EmailListName getById(Long id) {
        return emailListNameRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EmailListName Not Found"));
    }

    public EmailListName create(EmailListName emailListName) {
        if (emailListName.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EmailListName already exist");
        }
        if (emailListNameRepository.findByName(emailListName.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EmailListName name already exist");
        }
        return emailListNameRepository.save(emailListName);
    }

    public EmailListName update(Long id, EmailListName emailListName) {
        getById(id);
        emailListName.setId(id);
        return emailListNameRepository.save(emailListName);
    }

    public EmailListName delete(Long id) {
        EmailListName emailListName = getById(id);
        emailListNameRepository.delete(emailListName);
        return emailListName;
    }

}
