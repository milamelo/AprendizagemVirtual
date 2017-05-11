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
import negocio.excecao.ControleException;
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
    
    public MedalhaManegedBean () {
        try {
            final MedalhaService medalhaService = (MedalhaService) ServiceFactory.criarService(ServiceFactory.MEDALHA);
            medalhas = medalhaService.listar(medalhaSelecionada);
        } catch(Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

}
