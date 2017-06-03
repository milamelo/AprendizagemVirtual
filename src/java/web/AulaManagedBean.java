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
import negocio.entidade.Aula;
import negocio.entidade.Curso;
import negocio.entidade.Usuario;
import negocio.service.AulaService;
import negocio.service.ServiceFactory;
import negocio.service.UsuarioService;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "aulaMB")
@SessionScoped
public class AulaManagedBean extends MB {

    private Aula filtroAula = new Aula();
    private Aula aulaSelecionada = new Aula();
    private List<Aula> aulas = new ArrayList<>();

    public Aula getFiltroAula() {
        return filtroAula;
    }

    public void setFiltroAula(Aula filtroAula) {
        this.filtroAula = filtroAula;
    }

    public Aula getAulaSelecionada() {
        return aulaSelecionada;
    }

    public void setAulaSelecionada(Aula aulaSelecionada) {
        this.aulaSelecionada = aulaSelecionada;
    }

    public List getAulas() {
        return aulas;
    }

    public void setAulas(List aulas) {
        this.aulas = aulas;
    }

    public void acessarAulas() {
        try {
            super.limparMensagem();
            limparFiltro();
            filtroAula.setCurso((Curso) pegarDaSessao("cursoSelecionado"));
            listar();
            super.redirect("/pages/curso/aula/aula.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void limparAula() {
        this.aulaSelecionada = new Aula();
    }

    public void limparFiltro() {
        this.filtroAula = new Aula();
    }

    public void voltarCursos() {
        try {
            super.limparMensagem();
            super.redirect("/pages/curso/curso.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void localizar() {
        super.limparMensagem();
        listar();
    }

    private void listar() {
        try {
            final AulaService aulaService = (AulaService) ServiceFactory.criarService(ServiceFactory.AULA);
            aulas = aulaService.listar(filtroAula);
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public boolean podeCadastrar() {
        boolean retorno = false;
        try {
            final Curso curso = (Curso) pegarDaSessao("cursoSelecionado");
            if (curso != null
                    && curso.getUsuario().equals(getUsuarioLogado())
                    && !curso.isCancelado()) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }

    public void prepararCadastrar() {
        try {
            super.limparMensagem();
            limparAula();
            super.redirect("/pages/curso/aula/aulaCadastro.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void cadastrarAula() {
        try {
            super.limparMensagem();
            final AulaService aulaService = (AulaService) ServiceFactory.criarService(ServiceFactory.AULA);
            aulaSelecionada.setCurso((Curso) super.pegarDaSessao("cursoSelecionado"));
            aulaService.inserir(aulaSelecionada);
            limparAula();
            listar();
            super.addMensagemSucesso("Aula cadastrada com sucesso.");
            super.redirect("/pages/curso/aula/aula.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public boolean podeAlterarExcluir(final Aula aula) {
        boolean retorno = false;
        try {
            final AulaService aulaService = (AulaService) ServiceFactory.criarService(ServiceFactory.AULA);
            final Curso curso = (Curso) pegarDaSessao("cursoSelecionado");
            if (aula != null 
                    && aula.getCurso() != null
                    && curso != null
                    && curso.equals(aula.getCurso())
                    && curso.getUsuario().equals(getUsuarioLogado())
                    && !curso.isCancelado()
                    && !aulaService.teveAcesso(aula)) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }

    public void prepararAula(final Aula aula) {
        try {
            super.limparMensagem();
            aulaSelecionada = aula;
            super.redirect("/pages/curso/aula/aulaAlterar.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public boolean podeVisualizarAula(final Aula aula) {
        boolean retorno = false;
        try {
            final Curso curso = (Curso) pegarDaSessao("cursoSelecionado");
            if (aula != null 
                    && aula.getCurso() != null
                    && curso != null
                    && curso.equals(aula.getCurso())
                    && !curso.getUsuario().equals(getUsuarioLogado())
                    && !curso.isCancelado()
                    && curso.getAlunos().contains(getUsuarioLogado())) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }
    
    public void visualizarAula(final Aula aula) {
        try {
            super.limparMensagem();
            final AulaService aulaService = (AulaService) ServiceFactory.criarService(ServiceFactory.AULA);
            aulaSelecionada = aula;
            aulaService.visualizarAula(aulaSelecionada, getUsuarioLogado());

            final UsuarioService usuarioService = (UsuarioService) ServiceFactory.criarService(ServiceFactory.USUARIO);
            final Usuario usuario = usuarioService.consultarUsuario(getUsuarioLogado());
            super.guardarNaSessao("usuarioLogado", usuario);
            
            super.redirect("/pages/curso/aula/aulaDetalhes.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void voltar() {
        try {
            super.limparMensagem();
            listar();
            super.redirect("/pages/curso/aula/aula.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public boolean jaAcessada(final Aula aula) {
        boolean retorno = false;
        try {
            final AulaService aulaService = (AulaService) ServiceFactory.criarService(ServiceFactory.AULA);
            if (aulaService.aulaJaAcessadaPeloUsuario(aula, getUsuarioLogado())) {
                retorno = true;
            }
            
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        
        return retorno;
    }
    
    public void alterarAula() {
        try {
            super.limparMensagem();
            final AulaService aulaService = (AulaService) ServiceFactory.criarService(ServiceFactory.AULA);
            aulaSelecionada.setCurso((Curso) super.pegarDaSessao("cursoSelecionado"));
            aulaService.alterar(aulaSelecionada);
            limparAula();
            listar();
            super.addMensagemSucesso("Aula alterarda com sucesso.");
            super.redirect("/pages/curso/aula/aula.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void excluir(final Aula aula) {
        try {
            super.limparMensagem();
            final AulaService aulaService = (AulaService) ServiceFactory.criarService(ServiceFactory.AULA);
            aulaService.excluir(aula);
            limparAula();
            listar();
            super.addMensagemSucesso("Aula excluída com sucesso.");
            super.redirect("/pages/curso/aula/aula.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void voltarDetalhes() {
        try {
            super.limparMensagem();
            super.redirect("/pages/curso/cursoDetalhes.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
}
