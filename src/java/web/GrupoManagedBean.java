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
import negocio.entidade.GrupoUsuarioMensagem;
import negocio.entidade.Usuario;
import negocio.service.GrupoService;
import negocio.service.ServiceFactory;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "grupoMB")
@SessionScoped
public class GrupoManagedBean extends MB {

    private Grupo grupoSelecionado = new Grupo();
    private Grupo filtroGrupo = new Grupo();
    private List<Grupo> grupos = new ArrayList<>();
    private GrupoUsuarioMensagem grupoUsuarioMensagemSelecionada = new GrupoUsuarioMensagem();

    public Grupo getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(Grupo grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
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

    public GrupoUsuarioMensagem getGrupoUsuarioMensagemSelecionada() {
        return grupoUsuarioMensagemSelecionada;
    }

    public void setGrupoUsuarioMensagemSelecionada(GrupoUsuarioMensagem grupoUsuarioMensagemSelecionada) {
        this.grupoUsuarioMensagemSelecionada = grupoUsuarioMensagemSelecionada;
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
            grupos = grupoService.listar(filtroGrupo);
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
    
    private void limparGrupo() {
        grupoSelecionado = new Grupo();
    }
    
    public void limparFiltro() {
        filtroGrupo = new Grupo();
    }
    
    public void localizar() {
        super.limparMensagem();
        listar();
    }

    public void prepararCadastrar() {
        try {
            super.limparMensagem();
            limparGrupo();
            super.redirect("/pages/grupo/grupoCadastro.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void cadastrarGrupo() {
        try {
            super.limparMensagem();
            final GrupoService grupoService = (GrupoService) ServiceFactory.criarService(ServiceFactory.GRUPO);
            grupoSelecionado.setUsuario(getUsuarioLogado());
            grupoService.inserir(grupoSelecionado);
            limparGrupo();
            listar();
            super.addMensagemSucesso("Grupo cadastrado com sucesso.");
            super.redirect("/pages/grupo/grupo.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void entrar(final Grupo grupo) {
        try {
            super.limparMensagem();
            final GrupoService grupoService = (GrupoService) ServiceFactory.criarService(ServiceFactory.GRUPO);
            grupoService.entrar(grupo, getUsuarioLogado());
            limparGrupo();
            listar();
            super.addMensagemSucesso("Você entrou no grupo: " + grupo.getNome());
            super.redirect("/pages/grupo/grupo.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void sair(final Grupo grupo) {
        try {
            super.limparMensagem();
            final GrupoService grupoService = (GrupoService) ServiceFactory.criarService(ServiceFactory.GRUPO);
            grupoService.sair(grupo, getUsuarioLogado());
            limparGrupo();
            listar();
            super.addMensagemSucesso("Você saiu do grupo: " + grupo.getNome());
            super.redirect("/pages/grupo/grupo.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void prepararGrupo(final Grupo grupo) {
        try {
            super.limparMensagem();
            grupoSelecionado = grupo;
            super.redirect("/pages/grupo/grupoAlterar.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void alterarGrupo() {
        try {
            super.limparMensagem();
            final GrupoService grupoService = (GrupoService) ServiceFactory.criarService(ServiceFactory.GRUPO);
            grupoService.alterar(grupoSelecionado);
            limparGrupo();
            listar();
            super.addMensagemSucesso("Grupo alterado com sucesso.");
            super.redirect("/pages/grupo/grupo.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void remover(final Grupo grupo) {
        try {
            super.limparMensagem();
            final GrupoService grupoService = (GrupoService) ServiceFactory.criarService(ServiceFactory.GRUPO);
            grupoService.remover(grupo);
            limparGrupo();
            listar();
            super.addMensagemSucesso("Grupo removido com sucesso.");
            super.redirect("/pages/grupo/grupo.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void voltar() {
        try {
            super.limparMensagem();
            listar();
            super.redirect("/pages/grupo/grupo.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void prepararGrupoMensagens(final Grupo grupo) {
        try {
            super.limparMensagem();
            limparGrupoUsuarioMensagemSelecionada();
            final GrupoService grupoService = (GrupoService) ServiceFactory.criarService(ServiceFactory.GRUPO);
            
            this.grupoUsuarioMensagemSelecionada.setGrupo(grupo);
            this.grupoUsuarioMensagemSelecionada.setUsuario(getUsuarioLogado());
            this.grupoSelecionado = grupo;
            
            grupoService.consultarMensagens(grupoSelecionado);
            super.redirect("/pages/grupo/grupoMensagens.xhtml");
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
            final GrupoService grupoService = (GrupoService) ServiceFactory.criarService(ServiceFactory.GRUPO);
            grupoService.inserirMensagem(grupoUsuarioMensagemSelecionada);
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
