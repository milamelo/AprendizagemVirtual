/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import negocio.entidade.Usuario;
import negocio.service.ServiceFactory;
import negocio.service.UsuarioService;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioManagedBean extends MB {

    private Usuario usuarioSelecionado = new Usuario();
    private String confirmarSenha;
    private String senhaAtual;

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public void novoCadastro() throws Exception {
        limparUsuario();
        super.limparMensagem();
        super.redirect("/novoCadastro.xhtml");
    }

    public void salvarNovoCadastro() {
        try {
            super.limparMensagem();
            if (usuarioSelecionado.getSenha().equals(confirmarSenha)) {
                final UsuarioService usuarioService = (UsuarioService) ServiceFactory.criarService(ServiceFactory.USUARIO);
                usuarioService.cadastrarUsuario(usuarioSelecionado);
                limparUsuario();
                super.addMensagemSucesso("Usuário cadastrado com sucesso.");
                super.redirect("/novoCadastro.xhtml");
            } else {
                super.addMensagemErro("Senhas não conferem");
                super.redirect("/novoCadastro.xhtml");
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void limparUsuario() {
        usuarioSelecionado = new Usuario();
    }

    public void telaPerfil() {
        try {
            super.limparMensagem();
            prepararUsuario();
            super.redirect("/pages/perfil/perfil.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void prepararUsuario() {
        limparUsuario();
        final Usuario usuario = (Usuario) super.pegarDaSessao("usuarioLogado");
        usuarioSelecionado.setId(usuario.getId());
        usuarioSelecionado.setCpf(usuario.getCpf());
        usuarioSelecionado.setNome(usuario.getNome());
        usuarioSelecionado.setEmail(usuario.getEmail());
        usuarioSelecionado.setSenha(usuario.getSenha());
        usuarioSelecionado.setPontuacaoAcumulada(usuario.getPontuacaoAcumulada());
        usuarioSelecionado.setMedalha(usuario.getMedalha());
        usuarioSelecionado.setPermissaoEspecial(usuario.isPermissaoEspecial());
        usuarioSelecionado.setDataInclusao(usuario.getDataInclusao());
        usuarioSelecionado.setUltimoAcesso(usuario.getUltimoAcesso());
    }

    public void prepararAlterar() {
        try {
            super.limparMensagem();
            prepararUsuario();
            super.redirect("/pages/perfil/usuarioAlterar.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    private void alterarUsuario(final String sucesso) {
        try {
            super.limparMensagem();
            final UsuarioService usuarioService = (UsuarioService) ServiceFactory.criarService(ServiceFactory.USUARIO);
            usuarioSelecionado = usuarioService.alterarUsuario(usuarioSelecionado);
            super.guardarNaSessao("usuarioLogado", usuarioSelecionado);
            super.addMensagemSucesso(sucesso);
            super.redirect("/pages/perfil/perfil.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    public void alterarUsuario() {
        try {
            alterarUsuario("Usuário alterado com sucesso.");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void prepararAlterarSenha() {
        try {
            super.limparMensagem();
            prepararUsuario();
            senhaAtual = new String();
            super.redirect("/pages/perfil/usuarioAlterarSenha.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void alterarSenhaUsuario() {
        try {
            super.limparMensagem();
            final Usuario usuarioLogado = (Usuario) super.pegarDaSessao("usuarioLogado");

            if (senhaAtual.equals(usuarioLogado.getSenha())) {
                if (usuarioSelecionado.getSenha().equals(confirmarSenha)) {
                    alterarUsuario("Senha alterada com sucesso.");
                } else {
                    super.addMensagemErro("Senhas não conferem");
                    super.redirect("/pages/perfil/usuarioAlterarSenha.xhtml");
                }
            } else {
                super.addMensagemErro("Senha atual não confere");
                super.redirect("/pages/perfil/usuarioAlterarSenha.xhtml");
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void voltar() {
        try {
            super.limparMensagem();
            prepararUsuario();
            super.redirect("/pages/perfil/perfil.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
}
