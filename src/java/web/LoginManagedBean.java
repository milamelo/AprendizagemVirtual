/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import negocio.entidade.Usuario;
import negocio.service.LoginService;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginManagedBean extends MB {

    private Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void logar() throws Exception {
        final LoginService loginService = new LoginService();
        super.limparMensagem();
        usuario = loginService.logar(usuario);
        if (usuario == null) {
            usuario = new Usuario();
            super.addMensagemErro("Email e/ou Senha inválido.");
            super.redirect("/index.xhtml");
        } else {
            super.guardarNaSessao("usuarioLogado", usuario);
            super.redirect("/pages/index2.xhtml");
        }
    }

}
