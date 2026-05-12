package br.uema.agenda2.infra.repository;

import br.uema.agenda2.infra.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
