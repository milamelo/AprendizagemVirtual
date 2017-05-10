/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOMedalha;
import negocio.entidade.Medalha;
import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class MedalhaService {

    private final DAOMedalha daoMedalha;

    public MedalhaService() {
        this.daoMedalha = new DAOMedalha();
    }

    public void inserir(final Medalha medalha) throws ControleException, Exception {
        if (daoMedalha.existePontuacao(medalha.getPontuacaoNecessaria())) {
            throw new ControleException("Pontuação já cadastrada.");
        }
        if (daoMedalha.existeNome(medalha.getNome())) {
            throw new ControleException("Nome já cadastrado.");
        }
        int retorno = daoMedalha.inserir(medalha);
        if (retorno == 0) {
            throw new ControleException("Medalha não cadastrada.");
        }
    }

    public void alterar(final Medalha medalha) throws ControleException, Exception {
        if (daoMedalha.existePontuacao(medalha.getPontuacaoNecessaria())) {
            throw new ControleException("Pontuação já cadastrada.");
        }
        if (daoMedalha.existeNome(medalha.getNome())) {
            throw new ControleException("Nome já cadastrado.");
        }
        int retorno = daoMedalha.atualizar(medalha);
        if (retorno == 0) {
            throw new ControleException("Medalha não atualizada.");
        }
    }

    public void remover(final Medalha medalha) throws ControleException, Exception {
        int totalUsuariosComMedalha = daoMedalha.countUsuariosComMedalha(medalha);
        if (totalUsuariosComMedalha > 0) {
            throw new ControleException("Impossível remover. Total usuário(s) com medalha: " + totalUsuariosComMedalha + ".");
        }

        int retorno = daoMedalha.remover(medalha);
        if (retorno == 0) {
            throw new ControleException("Medalha não removida.");
        }
    }
}
