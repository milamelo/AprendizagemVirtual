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
public class Aula extends Postagem {

    private String conteudo;

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    
    @Override
    public double calcularPontuacao() {
        return super.getPontuacao() * 1.3;
    }
    
}
