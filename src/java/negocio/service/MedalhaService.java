/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.DAOFactory;
import java.util.List;
import negocio.entidade.Medalha;
import negocio.excecao.ControleException;
import negocio.interfaces.IMedalha;

/**
 *
 * @author Camila
 */
public class MedalhaService {

    private final IMedalha iMedalha;

    public MedalhaService() throws ControleException {
        this.iMedalha = (IMedalha) DAOFactory.criar(DAOFactory.MEDALHA);
    }

    public List<Medalha> listar(final Medalha medalha) throws ControleException, Exception {
        List<Medalha> medalhas = null;
        try {
            medalhas = iMedalha.listar(medalha);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. MedalhaService.listar");
        }
        return medalhas;
    }

    public void inserir(final Medalha medalha) throws ControleException, Exception {
        try {
            validarMedalha(medalha);
            int retorno = iMedalha.inserir(medalha);
            if (retorno == 0) {
                throw new ControleException("Medalha não cadastrada.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. MedalhaService.inserir");
        }
    }

    private void validarMedalha(final Medalha medalha) throws Exception {
        if (iMedalha.existePontuacao(medalha)) {
            throw new ControleException("Pontuação já cadastrada.");
        }
        if (iMedalha.existeNome(medalha)) {
            throw new ControleException("Nome já cadastrado.");
        }
    }

    public void alterar(final Medalha medalha) throws ControleException, Exception {
        try {
            int totalUsuariosComMedalha = iMedalha.countUsuariosComMedalha(medalha);
            if (totalUsuariosComMedalha > 0) {
                throw new ControleException("Impossível alterar. Total usuário(s) com medalha: " + totalUsuariosComMedalha + ".");
            }
            validarMedalha(medalha);
            int retorno = iMedalha.atualizar(medalha);
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
            int totalUsuariosComMedalha = iMedalha.countUsuariosComMedalha(medalha);
            if (totalUsuariosComMedalha > 0) {
                throw new ControleException("Impossível remover. Total usuário(s) com medalha: " + totalUsuariosComMedalha + ".");
            }

            int retorno = iMedalha.remover(medalha);
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
