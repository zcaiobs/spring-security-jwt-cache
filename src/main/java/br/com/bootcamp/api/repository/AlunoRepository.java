package br.com.bootcamp.api.repository;

import br.com.bootcamp.api.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
