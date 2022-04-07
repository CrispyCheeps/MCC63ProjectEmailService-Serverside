package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.EmailListName;
import co.id.emailservice.serverside.model.Konten;
import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.dto.EmailListNameData;
import co.id.emailservice.serverside.repository.EmailListNameRepository;
import co.id.emailservice.serverside.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class EmailListNameService {

    private EmailListNameRepository emailListNameRepository;
    private UserService userService;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

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
        emailListName.setUser(userRepository.findByEmail(emailListNameData.getUserName()).get());

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
