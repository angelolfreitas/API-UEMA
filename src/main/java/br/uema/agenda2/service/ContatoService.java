package br.uema.agenda2.service;

import br.uema.agenda2.infra.dto.ContactRequest;
import br.uema.agenda2.infra.dto.ContactResponseDTO;
import br.uema.agenda2.infra.entity.Contato;
import br.uema.agenda2.infra.repository.ContatoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class ContatoService {
    private ContatoRepository repository;

    public List<Contato> listALl(){
        return  repository.findAll();
    }
    public ContactResponseDTO create(ContactRequest contactRequest){
        Contato contact = Contato.builder()
                .telefone(contactRequest.telefone())
                .email(contactRequest.email())
                .nome(contactRequest.name())
                .build();
        repository.save(contact);
        return new ContactResponseDTO(contactRequest.name());
    }
}
