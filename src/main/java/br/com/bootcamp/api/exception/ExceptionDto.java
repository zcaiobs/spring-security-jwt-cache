package br.com.bootcamp.api.exception;

public class ExceptionDto {
    private final String campo;
    private final String erro;

    public ExceptionDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
