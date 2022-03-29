package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.EmailListName;
import co.id.emailservice.serverside.model.Konten;
import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.dto.EmailListNameData;
import co.id.emailservice.serverside.repository.EmailListNameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmailListNameService {

    private EmailListNameRepository emailListNameRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public EmailListNameService(EmailListNameRepository emailListNameRepository, UserService userService, ModelMapper modelMapper) {
        this.emailListNameRepository = emailListNameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<EmailListName> getAll() {
        return emailListNameRepository.findAll();
    }

    public EmailListName getById(Long id) {
        return emailListNameRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EmailListName Not Found"));
    }


    public EmailListName create(EmailListNameData emailListNameData) {
        if (emailListNameRepository.findByName(emailListNameData.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EmailListName name already exist");
        }

        EmailListName emailListName = modelMapper.map(emailListNameData, EmailListName.class);
        emailListName.setId(null);
        emailListName.setUser(userService.getById(emailListNameData.getUserId()));

        return emailListNameRepository.save(emailListName);
    }

    public EmailListName update(Long id, EmailListName emailListName) {
        EmailListName e = getById(id);
        emailListName.setId(id);
        emailListName.setUser(e.getUser());
        return emailListNameRepository.save(emailListName);
    }

    public EmailListName delete(Long id) {
        EmailListName emailListName = getById(id);
        emailListNameRepository.delete(emailListName);
        return emailListName;
    }

}
