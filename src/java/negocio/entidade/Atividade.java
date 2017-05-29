/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidade;

import java.time.LocalDate;

/**
 *
 * @author Camila
 */
public class Atividade extends Postagem {
    
    private TipoAtividade tipoAtividade = new TipoAtividade();
    private String descricao;
    private LocalDate dataEntrega;

    public TipoAtividade getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(TipoAtividade tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    @Override
    public double calcularPontuacao() {
        double retorno;
        
        if (LocalDate.now().isAfter(dataEntrega)){
            retorno = super.getPontuacao() * (this.tipoAtividade.getMultiplicidade() / 2);
        } else {
            retorno = super.getPontuacao() * this.tipoAtividade.getMultiplicidade();
        }
        
        return retorno;
    }
    
}
