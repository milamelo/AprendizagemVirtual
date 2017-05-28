/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOUsuario;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class UsuarioService {
    
    final private DAOUsuario daoUsuario;
    
    public UsuarioService() {
        this.daoUsuario = new DAOUsuario();
    }
    
    public void cadastrarUsuario(final Usuario usuario) throws ControleException, Exception {
        if (daoUsuario.existeCpf(usuario.getCpf())) {
            throw new ControleException("CPF já cadastrado.");
        }
        if (daoUsuario.existeEmail(usuario.getEmail())) {
            throw new ControleException("Email já cadastrado.");
        }
        
        int retorno = daoUsuario.inserir(usuario);
        if (retorno == 0) {
            throw new ControleException("Usuário não cadastrado.");
        }
    }
    
    public Usuario alterarUsuario(final Usuario usuario) throws ControleException, Exception {
        int retorno = daoUsuario.alterar(usuario);
        if (retorno == 0) {
            throw new ControleException("Usuário não alterado.");
        }
        return daoUsuario.consultarUsuario(usuario);
    }
    
    public Usuario consultarUsuario(final Usuario usuario) throws ControleException, Exception {
        return daoUsuario.consultarUsuario(usuario);
    }
    
}
