/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import negocio.entidade.Medalha;
import negocio.service.MedalhaService;
import negocio.service.ServiceFactory;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "medalhaMB")
@SessionScoped
public class MedalhaManegedBean extends MB {

    private Medalha medalhaSelecionada = new Medalha();
    private List<Medalha> medalhas = new ArrayList<>();

    public Medalha getMedalhaSelecionada() {
        return medalhaSelecionada;
    }

    public void setMedalhaSelecionada(Medalha medalhaSelecionada) {
        this.medalhaSelecionada = medalhaSelecionada;
    }

    public List<Medalha> getMedalhas() {
        return medalhas;
    }

    public void setMedalhas(List<Medalha> medalhas) {
        this.medalhas = medalhas;
    }

    public MedalhaManegedBean() {
        try {
            final MedalhaService medalhaService = (MedalhaService) ServiceFactory.criarService(ServiceFactory.MEDALHA);
            medalhas = medalhaService.listar(medalhaSelecionada);
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void remover(final Medalha medalha) {
        try {
            final MedalhaService medalhaService = (MedalhaService) ServiceFactory.criarService(ServiceFactory.MEDALHA);
            medalhaService.remover(medalha);
            limparMedalha();
            listar();
            super.addMensagemSucesso("Medalha removida com sucesso.");
            super.redirect("/pages/medalha/medalha.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void listar() {
        try {
            final MedalhaService medalhaService = (MedalhaService) ServiceFactory.criarService(ServiceFactory.MEDALHA);
            medalhas = medalhaService.listar(medalhaSelecionada);
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void limparMedalha() {
        medalhaSelecionada = new Medalha();
    }

    public void prepararMedalha(final Medalha medalha) {
        try {
            medalhaSelecionada = medalha;
            super.redirect("/pages/medalha/medalhaAlterar.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void alterarMedalha() {
        try {
            super.limparMensagem();
            final MedalhaService medalhaService = (MedalhaService) ServiceFactory.criarService(ServiceFactory.MEDALHA);
            medalhaService.alterar(medalhaSelecionada);
            limparMedalha();
            listar();
            super.addMensagemSucesso("Medalha alterada com sucesso.");
            super.redirect("/pages/medalha/medalha.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void prepararCadastrar() {
        try {
            limparMedalha();
            super.redirect("/pages/medalha/medalhaCadastro.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void cadastrarMedalha() {
        try {
            super.limparMensagem();
            final MedalhaService medalhaService = (MedalhaService) ServiceFactory.criarService(ServiceFactory.MEDALHA);
            medalhaService.inserir(medalhaSelecionada);
            limparMedalha();
            listar();
            super.addMensagemSucesso("Medalha cadastrada com sucesso.");
            super.redirect("/pages/medalha/medalha.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
}
