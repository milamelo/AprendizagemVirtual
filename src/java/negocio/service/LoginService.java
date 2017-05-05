/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOUsuario;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public class LoginService {

    final private DAOUsuario daoUsuario;

    public LoginService() {
        this.daoUsuario = new DAOUsuario();
    }

    public Usuario logar(final Usuario usuario) throws Exception {
        try {
            return this.daoUsuario.logar(usuario);
        } catch (Exception e) {
            throw new Exception("Erro: LoginService.logar \n" + e.getMessage());
        }
    }
}
