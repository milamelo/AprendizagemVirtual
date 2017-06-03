/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.interfaces;

import negocio.entidade.GrupoUsuarioMensagem;

/**
 *
 * @author Camila
 */
public interface IGrupoUsuarioMensagem {

    void consultarMensagens(final GrupoUsuarioMensagem grupoUsuarioMensagem) throws Exception;

    int inserirMensagem(final GrupoUsuarioMensagem grupoUsuarioMensagem) throws Exception;
}
