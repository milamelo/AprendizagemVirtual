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
import negocio.entidade.Usuario;
import negocio.service.RankingService;
import negocio.service.ServiceFactory;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "rankingMB")
@SessionScoped
public class RankingManagedBean extends MB {

    private int limite;
    private List<Usuario> usuarios = new ArrayList<>();

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void listarRanking() {
        try {
            final RankingService rankingService = (RankingService) ServiceFactory.criarService(ServiceFactory.RANKING);
            this.usuarios = rankingService.listarRanking(limite);
            super.redirect("/pages/ranking/ranking.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
}
