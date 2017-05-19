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
import negocio.entidade.Grupo;
import negocio.service.GrupoService;
import negocio.service.ServiceFactory;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "grupoMB")
@SessionScoped
public class GrupoManagedBean extends MB {

    private Grupo grupoSelecionada = new Grupo();
    private Grupo filtroGrupo = new Grupo();
    private List<Grupo> grupos = new ArrayList<>();

    public Grupo getGrupoSelecionada() {
        return grupoSelecionada;
    }

    public void setGrupoSelecionada(Grupo grupoSelecionada) {
        this.grupoSelecionada = grupoSelecionada;
    }

    public Grupo getFiltroGrupo() {
        return filtroGrupo;
    }

    public void setFiltroGrupo(Grupo filtroGrupo) {
        this.filtroGrupo = filtroGrupo;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public GrupoManagedBean() {
        try {
            listar();
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void listar() {
        try {
            final GrupoService grupoService = (GrupoService) ServiceFactory.criarService(ServiceFactory.GRUPO);
//            grupos = grupoService.listar(filtroGrupo);
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public boolean podeEntrar(final Grupo grupo) {
        boolean retorno = false;
        try {
            if (!grupo.getUsuario().equals(getUsuarioLogado())
                    && !grupo.getUsuarios().contains(getUsuarioLogado())) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }

    public boolean podeSair(final Grupo grupo) {
        boolean retorno = false;
        try {
            if (!grupo.getUsuario().equals(getUsuarioLogado())
                    && grupo.getUsuarios().contains(getUsuarioLogado())) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }

    public boolean podeAlterarRemover(final Grupo grupo) {
        boolean retorno = false;
        try {
            if (grupo.getUsuario().equals(getUsuarioLogado())
                    && grupo.getUsuarios().size() == 1) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }


}
