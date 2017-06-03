/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.DAOFactory;
import negocio.entidade.GrupoUsuarioMensagem;
import negocio.excecao.ControleException;
import negocio.interfaces.IGrupoUsuarioMensagem;

/**
 *
 * @author Camila
 */
public class GrupoUsuarioMensagemService {

    private final IGrupoUsuarioMensagem iGrupoUsuarioMensagem;

    public GrupoUsuarioMensagemService() throws ControleException {
        this.iGrupoUsuarioMensagem = (IGrupoUsuarioMensagem) DAOFactory.criar(DAOFactory.GRUPO_USUARIO_MENSAGEM);
    }

    public void consultarMensagens(final GrupoUsuarioMensagem grupoUsuarioMensagem) throws ControleException, Exception {
        try {
            iGrupoUsuarioMensagem.consultarMensagens(grupoUsuarioMensagem);
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
            iGrupoUsuarioMensagem.inserirMensagem(grupoUsuarioMensagem);

        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoUsuarioMensagemService.inserirMensagem");
        }
    }

}
