package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.dto.ParticipantData;
import co.id.emailservice.serverside.repository.EmailListNameRepository;
import co.id.emailservice.serverside.repository.ParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantService {

    private ParticipantRepository participantRepository;
    private EmailListNameService emailListNameService;
    private ModelMapper modelMapper;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, EmailListNameService emailListNameService, ModelMapper modelMapper) {
        this.participantRepository = participantRepository;
        this.emailListNameService = emailListNameService;
        this.modelMapper = modelMapper;
    }

    public List<Participant> getAll() {
        return participantRepository.findAll();
    }

    public Participant getById(Long id) {
        return participantRepository.findById(id).orElseThrow(()
        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not Found"));
    }

    public Participant create(ParticipantData participantData) {
        Participant participant = modelMapper.map(participantData, Participant.class);
        participant.setId(null);
        participant.setEmailListName(emailListNameService.getById(participantData.getEmailListNameId()));
        return participantRepository.save(participant);
    }

    public Participant update(Long id, Participant participant) {
        Participant p = getById(id);
        participant.setId(id);
        participant.setEmailListName(p.getEmailListName());
        return participantRepository.save(participant);
    }

    public Participant delete(Long id) {
        Participant participant = getById(id);
        participantRepository.delete(participant);
        return participant;
    }

}
