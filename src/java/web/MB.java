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
    private boolean exibeMsg = false;
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isExibeMsg() {
        return exibeMsg;
    }

    public void setExibeMsg(boolean exibeMsg) {
        this.exibeMsg = exibeMsg;
    }
    
    public void redirect(String caminho) throws Exception {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, caminho));
        extContext.redirect(url);
    }

    public void addMensagem(final String mensagem) {
        this.mensagem = mensagem;
        this.exibeMsg = true;
    }
    
    public void limparMensagem() {
        this.mensagem = null;
        this.exibeMsg = false;
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
