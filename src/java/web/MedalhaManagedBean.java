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
public class MedalhaManagedBean extends MB {

    private Medalha medalhaSelecionada = new Medalha();
    private Medalha filtroMedalha = new Medalha();
    private List<Medalha> medalhas = new ArrayList<>();

    public Medalha getMedalhaSelecionada() {
        return medalhaSelecionada;
    }

    public void setMedalhaSelecionada(Medalha medalhaSelecionada) {
        this.medalhaSelecionada = medalhaSelecionada;
    }

    public Medalha getFiltroMedalha() {
        return filtroMedalha;
    }

    public void setFiltroMedalha(Medalha filtroMedalha) {
        this.filtroMedalha = filtroMedalha;
    }

    public List<Medalha> getMedalhas() {
        return medalhas;
    }

    public void setMedalhas(List<Medalha> medalhas) {
        this.medalhas = medalhas;
    }

    public MedalhaManagedBean() {
        try {
            listar();
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
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void listar() {
        try {
            final MedalhaService medalhaService = (MedalhaService) ServiceFactory.criarService(ServiceFactory.MEDALHA);
            medalhas = medalhaService.listar(filtroMedalha);
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void limparMedalha() {
        medalhaSelecionada = new Medalha();
    }

    public void prepararMedalha(final Medalha medalha) {
        try {
            limparMensagem();
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
            limparMensagem();
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

    public void limpar() {
        filtroMedalha = new Medalha();
    }

    public void localizar() {
        limparMensagem();
        listar();
    }

}
