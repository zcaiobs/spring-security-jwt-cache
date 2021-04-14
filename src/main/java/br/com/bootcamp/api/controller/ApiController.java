package br.com.bootcamp.api.controller;

import br.com.bootcamp.api.domain.Aluno;
import br.com.bootcamp.api.domain.AlunoDto;
import br.com.bootcamp.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    AlunoRepository alunoRepository;

    @GetMapping
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/findall")
    @Cacheable(value = "findAllAluno")
    public ResponseEntity<Page<AlunoDto>> findAllAluno(
            @PageableDefault(page = 0, size = 5, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok().body(AlunoDto.converter(alunoRepository.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> findAluno(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        return aluno.map(value -> ResponseEntity.ok().body(AlunoDto.converter(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @CacheEvict(value = "findAllAluno", allEntries = true)
    public ResponseEntity<AlunoDto> saveAluno(@RequestBody @Valid Aluno aluno, UriComponentsBuilder uriComponentsBuilder) {
        return ResponseEntity.created(uriComponentsBuilder.path("/api/{id}")
                .buildAndExpand(alunoRepository.save(aluno).getId()).toUri())
                .body(AlunoDto.converter(aluno));
    }
}
