package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.helper.ExcelHelper;
import co.id.emailservice.serverside.model.Participant;
import co.id.emailservice.serverside.model.User;
import co.id.emailservice.serverside.model.dto.ParticipantData;
import co.id.emailservice.serverside.repository.ParticipantRepository;
import co.id.emailservice.serverside.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ParticipantService {

    private ParticipantRepository participantRepository;
    private EmailListNameService emailListNameService;
    private ModelMapper modelMapper;
    private UserRepository userRepository;


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

    public void addParticipantsFromExcel (MultipartFile file, Long emailListNameId) {
        try {
            List<ParticipantData> participantsData = ExcelHelper.excelToParticipants(file.getInputStream());
            List<Participant> participants = modelMapper.map(participantsData, new TypeToken<List<Participant>>() {}.getType());
            for (Participant participant : participants) {
                participant.setId(null);
                participant.setEmailListName(emailListNameService.getById(emailListNameId));
            }
            participantRepository.saveAll(participants);

        }catch (IOException e){
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<Participant> getFilterEmailParticipantByEmailListNameId(Long id){
        return participantRepository.filterEmailParticipantByEmailListNameId(id);
    }

    public List<Participant> getFilterParticipantByEmailListNameId(Long id){
        return participantRepository.filterParticipantByEmailListNameId(id);
    }

}
