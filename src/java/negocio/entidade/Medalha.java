/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidade;

import java.math.BigDecimal;

/**
 *
 * @author Camila
 */
public class Medalha {

    private Integer id;
    private String nome;
    private BigDecimal pontuacaoNecessaria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPontuacaoNecessaria() {
        return pontuacaoNecessaria;
    }

    public void setPontuacaoNecessaria(BigDecimal pontuacaoNecessaria) {
        this.pontuacaoNecessaria = pontuacaoNecessaria;
    }
}
