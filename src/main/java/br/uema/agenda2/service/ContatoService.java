package br.uema.agenda2.service;

import br.uema.agenda2.infra.dto.ContactRequest;
import br.uema.agenda2.infra.dto.ContactResponseDTO;
import br.uema.agenda2.infra.dto.FullContactDTOResponse;
import br.uema.agenda2.infra.dto.UserDeletion;
import br.uema.agenda2.infra.entity.Contato;
import br.uema.agenda2.infra.repository.ContatoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Optional<FullContactDTOResponse> getUser(String email){
        Optional<Contato> c = findByEmail(email);
        return c.map(contato -> new FullContactDTOResponse(
                contato.getNome(), contato.getEmail(), contato.getTelefone()
        ));
    }
    private Optional<Contato> findByEmail(String email){
        return repository.findByEmail(email);
    }

    public UserDeletion delete(String email){
        Optional<Contato> userToBeDeleted = findByEmail(email);

        if(userToBeDeleted.isEmpty()) return new UserDeletion("user not found");

        Contato contato = userToBeDeleted.get();

        repository.delete(contato);

        return new UserDeletion("Successfuly deleted");
    }

    @Transactional
    public Optional<FullContactDTOResponse> putUser(String email, ContactRequest request){
        Optional<Contato> contato = findByEmail(email);
        if(contato.isEmpty()) return Optional.empty();

        Contato contatoValue = Contato.builder()
                .telefone(request.telefone())
                .nome(request.name())
                .email(request.email())
                .build();

        repository.save(contatoValue);

        return Optional.of(
                new FullContactDTOResponse(
                        contatoValue.getNome(), contatoValue.getEmail(), contatoValue.getTelefone())
        );

    }

    @Transactional
    public Optional<FullContactDTOResponse> patchUser(String email, Map<String, Object> fields){
        Optional<Contato> contato = findByEmail(email);
        if(contato.isEmpty()) return Optional.empty();

        String nome = merge(contato.get(), fields);

        return Optional.of(
                new FullContactDTOResponse(
                        nome, contato.get().getEmail(), contato.get().getTelefone())
        );
    }
    private String merge(Contato contato, Map<String, Object> fields){
        fields.forEach(
                (k, v)->{
                    Field field = ReflectionUtils.findField(Contato.class, k);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, contato, v);
                }
        );

        repository.save(contato);

        return contato.getNome();
    }
}
