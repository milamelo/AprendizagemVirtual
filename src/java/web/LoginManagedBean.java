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
import negocio.service.ServiceFactory;

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
        final LoginService loginService = (LoginService) ServiceFactory.criarService(ServiceFactory.LOGIN);
        super.limparMensagem();
        usuario = loginService.logar(usuario);
        if (usuario == null) {
            usuario = new Usuario();
            super.addMensagemErro("E-mail e/ou Senha inválido.");
            super.redirect("/index.xhtml");
        } else {
            super.guardarNaSessao("usuarioLogado", usuario);
            super.redirect("/pages/logado.xhtml");
        }
    }

    public void sair() throws Exception {
        super.limparMensagem();
        usuario = (Usuario) pegarDaSessao("usuarioLogado");
        if (usuario != null) {
            super.invalidarSessao();
            super.redirect("/index.xhtml");
        }
    }

}
