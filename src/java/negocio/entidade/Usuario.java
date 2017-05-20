/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidade;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Camila
 */
public class Usuario {

    private Integer id;
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private BigDecimal pontuacaoAcumulada;
    private Medalha medalha;
    private boolean permissaoEspecial;
    private Timestamp dataInclusao;
    private Timestamp ultimoAcesso;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public BigDecimal getPontuacaoAcumulada() {
        return pontuacaoAcumulada;
    }

    public void setPontuacaoAcumulada(BigDecimal pontuacaoAcumulada) {
        this.pontuacaoAcumulada = pontuacaoAcumulada;
    }

    public Medalha getMedalha() {
        return medalha;
    }

    public void setMedalha(Medalha medalha) {
        this.medalha = medalha;
    }

    public boolean isPermissaoEspecial() {
        return permissaoEspecial;
    }

    public void setPermissaoEspecial(boolean permissaoEspecial) {
        this.permissaoEspecial = permissaoEspecial;
    }

    public Timestamp getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Timestamp dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Timestamp getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Timestamp ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
