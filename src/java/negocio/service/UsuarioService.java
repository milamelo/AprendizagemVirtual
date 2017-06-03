/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.DAOFactory;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;
import negocio.interfaces.IUsuario;

/**
 *
 * @author Camila
 */
public class UsuarioService {

    final private IUsuario iUsuario;

    public UsuarioService() throws ControleException {
        this.iUsuario = (IUsuario) DAOFactory.criar(DAOFactory.USUARIO);
    }

    public void cadastrarUsuario(final Usuario usuario) throws ControleException, Exception {
        if (iUsuario.existeCpf(usuario.getCpf())) {
            throw new ControleException("CPF já cadastrado.");
        }
        if (iUsuario.existeEmail(usuario.getEmail())) {
            throw new ControleException("Email já cadastrado.");
        }

        int retorno = iUsuario.inserir(usuario);
        if (retorno == 0) {
            throw new ControleException("Usuário não cadastrado.");
        }
    }

    public Usuario alterarUsuario(final Usuario usuario) throws ControleException, Exception {
        int retorno = iUsuario.alterar(usuario);
        if (retorno == 0) {
            throw new ControleException("Usuário não alterado.");
        }
        return iUsuario.consultarUsuario(usuario);
    }

    public Usuario consultarUsuario(final Usuario usuario) throws ControleException, Exception {
        return iUsuario.consultarUsuario(usuario);
    }

}
