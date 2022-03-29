package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.Template;
import co.id.emailservice.serverside.model.dto.TemplateData;
import co.id.emailservice.serverside.repository.TemplateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TemplateService {

    private TemplateRepository templateRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TemplateService(TemplateRepository templateRepository, ModelMapper modelMapper) {
        this.templateRepository = templateRepository;
        this.modelMapper = modelMapper;
    }

    public List<Template> getAll() {
        return templateRepository.findAll();
    }

    public Template getById(Long id) {
        return templateRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found"));
    }

    public Template create(TemplateData templateData) {
        Template template = modelMapper.map(templateData, Template.class);
        template.setId(null);
        if (template.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Template already exist");
        }
        return templateRepository.save(template);
    }

    public Template update(Long id, Template template) {
        getById(id);
        template.setId(id);
        return templateRepository.save(template);
    }

    public Template delete(Long id) {
        Template template = getById(id);
        templateRepository.delete(template);
        return template;
    }
}
