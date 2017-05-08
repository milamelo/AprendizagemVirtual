/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Camila
 */
@SessionScoped
public class MB {

    private String mensagem;
    private boolean exibeMsgErro = false;
    private boolean exibeMsgSucesso = false;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isExibeMsgErro() {
        return exibeMsgErro;
    }

    public void setExibeMsgErro(boolean exibeMsgErro) {
        this.exibeMsgErro = exibeMsgErro;
    }

    public boolean isExibeMsgSucesso() {
        return exibeMsgSucesso;
    }

    public void setExibeMsgSucesso(boolean exibeMsgSucesso) {
        this.exibeMsgSucesso = exibeMsgSucesso;
    }
    
    public void redirect(String caminho) throws Exception {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, caminho));
        extContext.redirect(url);
    }

    public void addMensagemErro(final String mensagem) {
        this.mensagem = mensagem;
        this.exibeMsgErro = true;
        this.exibeMsgSucesso = false;
    }
    
    public void addMensagemSucesso(final String mensagem) {
        this.mensagem = mensagem;
        this.exibeMsgSucesso = true;
        this.exibeMsgErro = false;
    }

    public void limparMensagem() {
        this.mensagem = null;
        this.exibeMsgErro = false;
        this.exibeMsgSucesso = false;
    }

    public HttpSession pegarSessao() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public void guardarNaSessao(String key, Object obj) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, obj);
    }

    public Object pegarDaSessao(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public void removerDaSessao(String atributeName) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(atributeName);
    }

    public void invalidarSessao() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

}
