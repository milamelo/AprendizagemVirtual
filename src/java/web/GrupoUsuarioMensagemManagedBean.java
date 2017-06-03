/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import negocio.entidade.Grupo;
import negocio.entidade.GrupoUsuarioMensagem;
import negocio.entidade.Usuario;
import negocio.service.GrupoUsuarioMensagemService;
import negocio.service.ServiceFactory;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "grupoUsuarioMensagemMB")
@SessionScoped
public class GrupoUsuarioMensagemManagedBean extends MB {

    private GrupoUsuarioMensagem grupoUsuarioMensagemSelecionada = new GrupoUsuarioMensagem();

    public GrupoUsuarioMensagem getGrupoUsuarioMensagemSelecionada() {
        return grupoUsuarioMensagemSelecionada;
    }

    public void setGrupoUsuarioMensagemSelecionada(GrupoUsuarioMensagem grupoUsuarioMensagemSelecionada) {
        this.grupoUsuarioMensagemSelecionada = grupoUsuarioMensagemSelecionada;
    }

    public void prepararGrupoMensagens(final Grupo grupo) {
        try {
            super.limparMensagem();
            limparGrupoUsuarioMensagemSelecionada();
            final GrupoUsuarioMensagemService grupoUsuarioMensagemService = (GrupoUsuarioMensagemService) ServiceFactory.criarService(ServiceFactory.GRUPO_USUARIO_MENSAGEM);

            this.grupoUsuarioMensagemSelecionada.setGrupo(grupo);
            this.grupoUsuarioMensagemSelecionada.setUsuario(getUsuarioLogado());

            grupoUsuarioMensagemService.consultarMensagens(grupoUsuarioMensagemSelecionada);
            super.redirect("/pages/grupo/mensagem/grupoMensagens.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public boolean isUsuarioLogado(final Usuario usuario) {
        boolean retorno = false;
        try {
            retorno = getUsuarioLogado().equals(usuario);
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }

    private void limparGrupoUsuarioMensagemSelecionada() {
        this.grupoUsuarioMensagemSelecionada = new GrupoUsuarioMensagem();
    }

    public void enviarMensagem() {
        try {
            final GrupoUsuarioMensagemService grupoUsuarioMensagemService = (GrupoUsuarioMensagemService) ServiceFactory.criarService(ServiceFactory.GRUPO_USUARIO_MENSAGEM);
            grupoUsuarioMensagemService.inserirMensagem(grupoUsuarioMensagemSelecionada);
            prepararGrupoMensagens(grupoUsuarioMensagemSelecionada.getGrupo());
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public boolean podeEnviarMensagem(final Grupo grupo) {
        boolean retorno = false;
        try {
            if (grupo.getUsuarios().contains(getUsuarioLogado())) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }

}
