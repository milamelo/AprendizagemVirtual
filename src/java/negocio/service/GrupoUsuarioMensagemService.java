/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOGrupoUsuarioMensagem;
import negocio.entidade.GrupoUsuarioMensagem;
import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class GrupoUsuarioMensagemService {

    private final DAOGrupoUsuarioMensagem daoGrupoUsuarioMensagem;

    public GrupoUsuarioMensagemService() {
        this.daoGrupoUsuarioMensagem = new DAOGrupoUsuarioMensagem();
    }

    public void consultarMensagens(final GrupoUsuarioMensagem grupoUsuarioMensagem) throws ControleException, Exception {
        try {
            daoGrupoUsuarioMensagem.consultarMensagens(grupoUsuarioMensagem);
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoUsuarioMensagemService.consultarMensagens");
        }
    }

    public void inserirMensagem(final GrupoUsuarioMensagem grupoUsuarioMensagem) throws ControleException, Exception {
        try {
            if (grupoUsuarioMensagem.getMensagem() != null && grupoUsuarioMensagem.getMensagem().length() > 1500) {
                throw new ControleException("Mensagem não pode conter mais de 200 caracteres.");
            }
            daoGrupoUsuarioMensagem.inserirMensagem(grupoUsuarioMensagem);

        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoUsuarioMensagemService.inserirMensagem");
        }
    }

}
