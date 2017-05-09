/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;
import negocio.service.ServiceFactory;
import negocio.service.UsuarioService;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioManagedBean extends MB {

    private Usuario usuario = new Usuario();
    private String confirmarSenha;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public void novoCadastro() throws Exception {
        limparUsuario();
        super.limparMensagem();
        super.redirect("/novoCadastro.xhtml");
    }

    public void salvarNovoCadastro() {
        try {
            super.limparMensagem();
            if (usuario.getSenha().equals(confirmarSenha)) {
                final UsuarioService usuarioService = (UsuarioService) ServiceFactory.criarService(ServiceFactory.USUARIO);
                usuarioService.cadastrarUsuario(usuario);
                limparUsuario();
                super.addMensagemSucesso("Usuário cadastrado com sucesso.");
                super.redirect("/novoCadastro.xhtml");
            } else {
                super.addMensagemErro("Senhas não conferem");
                super.redirect("/novoCadastro.xhtml");
            }
        } catch (ControleException e) {
            super.addMensagemErro(e.getMessage());
        } catch (Exception e) {

        }
    }
    
    private void limparUsuario() {
        usuario = new Usuario();
    }

}
