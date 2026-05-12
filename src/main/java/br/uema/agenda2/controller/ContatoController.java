package br.uema.agenda2.controller;

import br.uema.agenda2.infra.dto.ContactRequest;
import br.uema.agenda2.infra.dto.ContactResponseDTO;
import br.uema.agenda2.infra.entity.Contato;
import br.uema.agenda2.service.ContatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
