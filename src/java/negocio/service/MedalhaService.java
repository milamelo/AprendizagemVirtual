/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOMedalha;
import java.util.List;
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

    public List<Medalha> listar(final Medalha medalha) throws ControleException, Exception {
        List<Medalha> medalhas = null;
        try {
            medalhas = daoMedalha.listar(medalha);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. MedalhaService.listar");
        }
        return medalhas;
    }

    public void inserir(final Medalha medalha) throws ControleException, Exception {
        try {
            if (daoMedalha.existePontuacao(medalha)) {
                throw new ControleException("Pontuação já cadastrada.");
            }
            if (daoMedalha.existeNome(medalha)) {
                throw new ControleException("Nome já cadastrado.");
            }
            int retorno = daoMedalha.inserir(medalha);
            if (retorno == 0) {
                throw new ControleException("Medalha não cadastrada.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. MedalhaService.inserir");
        }
    }

    public void alterar(final Medalha medalha) throws ControleException, Exception {
        try {
            if (daoMedalha.existePontuacao(medalha)) {
                throw new ControleException("Pontuação já cadastrada.");
            }
            if (daoMedalha.existeNome(medalha)) {
                throw new ControleException("Nome já cadastrado.");
            }
            int retorno = daoMedalha.atualizar(medalha);
            if (retorno == 0) {
                throw new ControleException("Medalha não atualizada.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. MedalhaService.alterar");
        }
    }

    public void remover(final Medalha medalha) throws ControleException, Exception {
        try {
            int totalUsuariosComMedalha = daoMedalha.countUsuariosComMedalha(medalha);
            if (totalUsuariosComMedalha > 0) {
                throw new ControleException("Impossível remover. Total usuário(s) com medalha: " + totalUsuariosComMedalha + ".");
            }

            int retorno = daoMedalha.remover(medalha);
            if (retorno == 0) {
                throw new ControleException("Medalha não removida.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. MedalhaService.remover");
        }
    }
}
