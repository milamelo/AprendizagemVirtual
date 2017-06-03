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
public class LoginService {

    final private IUsuario iUsuario;

    public LoginService() throws ControleException {
        this.iUsuario = (IUsuario) DAOFactory.criar(DAOFactory.USUARIO);
    }

    public Usuario logar(final Usuario usuario) throws Exception {
        try {
            final Usuario usu = this.iUsuario.logar(usuario);
            if (usu != null) {
                this.iUsuario.atualizarUltimoAcesso(usu);
            }
            return usu;
        } catch (Exception e) {
            throw new Exception("Erro: LoginService.logar \n" + e.getMessage());
        }
    }
}
