package br.uema.agenda2.controller;

import br.uema.agenda2.infra.dto.ContactRequest;
import br.uema.agenda2.infra.dto.ContactResponseDTO;
import br.uema.agenda2.infra.dto.FullContactDTOResponse;
import br.uema.agenda2.infra.dto.UserDeletion;
import br.uema.agenda2.infra.entity.Contato;
import br.uema.agenda2.service.ContatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/contatos")
@AllArgsConstructor
public class ContatoController {
    private ContatoService contatoService;
    @PostMapping
    public ResponseEntity<ContactResponseDTO> createContact(@RequestBody ContactRequest contactRequest){
        return ResponseEntity.ok(
                contatoService.create(contactRequest));
    }
    @GetMapping
    public ResponseEntity<List<Contato>> getAll(){
        return ResponseEntity.ok(contatoService.listALl());
    }

    @GetMapping("/{email}")
    public ResponseEntity<FullContactDTOResponse> getContact(@PathVariable String email){
        Optional<FullContactDTOResponse> response = contatoService.getUser(email);

        return response.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.badRequest().build());
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<UserDeletion> deleteContact(@PathVariable String email){
        UserDeletion deletion = contatoService.delete(email);

        return  ResponseEntity.ok(deletion);
    }
    @PutMapping("/{email}")
    public ResponseEntity<FullContactDTOResponse> putContact(@PathVariable  String email, @RequestBody ContactRequest request){
        Optional<FullContactDTOResponse> response = contatoService.putUser(email, request);
        return response.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.badRequest().build());
    }
    @PatchMapping("/{email}")
    public ResponseEntity<FullContactDTOResponse> patchContact(@PathVariable String email, @RequestBody Map<String, Object> fields){
        Optional<FullContactDTOResponse> response = contatoService.patchUser(email, fields);
        return response.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.badRequest().build());
    }
}
