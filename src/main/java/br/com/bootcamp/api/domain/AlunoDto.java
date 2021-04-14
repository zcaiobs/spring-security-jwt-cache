package br.com.bootcamp.api.domain;

import org.springframework.data.domain.Page;

public class AlunoDto {
    private String nome;
    private String email;

    public AlunoDto(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public static Page<AlunoDto> converter(Page<Aluno> pageAluno) {
        return pageAluno.map(AlunoDto::converter);
    }

    public static AlunoDto converter(Aluno aluno) {
        return new AlunoDto(aluno.getNome(), aluno.getEmail());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
