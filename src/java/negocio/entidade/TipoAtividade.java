/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidade;

/**
 *
 * @author Camila
 */
public class TipoAtividade {

    private Integer id;
    private String descricao;
    private Integer multiplicidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getMultiplicidade() {
        return multiplicidade;
    }

    public void setMultiplicidade(Integer multiplicidade) {
        this.multiplicidade = multiplicidade;
    }

}
