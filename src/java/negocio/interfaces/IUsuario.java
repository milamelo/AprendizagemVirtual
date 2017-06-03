/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.interfaces;

import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public interface IUsuario {

    Usuario logar(final Usuario usuario) throws Exception;

    boolean existeCpf(final String cpf) throws Exception;

    boolean existeEmail(final String email) throws Exception;

    int inserir(final Usuario usuario) throws Exception;

    int atualizarUltimoAcesso(final Usuario usuario) throws Exception;

    int alterar(final Usuario usuario) throws Exception;

    Usuario consultarUsuario(final Usuario usuario) throws Exception;
}
