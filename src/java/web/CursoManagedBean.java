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
import negocio.entidade.Curso;
import negocio.service.CursoService;
import negocio.service.ServiceFactory;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "cursoMB")
@SessionScoped
public class CursoManagedBean extends MB {

    private Curso cursoSelecionado = new Curso();
    private Curso filtroCurso = new Curso();
    private List<Curso> cursos = new ArrayList<>();

    public Curso getCursoSelecionado() {
        return cursoSelecionado;
    }

    public void setCursoSelecionado(Curso cursoSelecionado) {
        this.cursoSelecionado = cursoSelecionado;
    }

    public Curso getFiltroCurso() {
        return filtroCurso;
    }

    public void setFiltroCurso(Curso filtroCurso) {
        this.filtroCurso = filtroCurso;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public CursoManagedBean() {
        try {
            listar();
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void limparCurso() {
        cursoSelecionado = new Curso();
    }

    public void limparFiltro() {
        filtroCurso = new Curso();
    }

    public void localizar() {
        super.limparMensagem();
        listar();
    }

    private void listar() {
        try {
            final CursoService cursoService = (CursoService) ServiceFactory.criarService(ServiceFactory.CURSO);
            cursos = cursoService.listar(filtroCurso);
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void prepararCadastrar() {
        try {
            super.limparMensagem();
            limparCurso();
            super.redirect("/pages/curso/cursoCadastro.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void cadastrarCurso() {
        try {
            super.limparMensagem();
            final CursoService cursoService = (CursoService) ServiceFactory.criarService(ServiceFactory.CURSO);
            cursoSelecionado.setUsuario(getUsuarioLogado());
            cursoService.inserir(cursoSelecionado);
            limparCurso();
            listar();
            super.addMensagemSucesso("Curso cadastrado com sucesso.");
            super.redirect("/pages/curso/curso.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public boolean podeEditarNome() {
        boolean retorno = false;
        try {
            if (cursoSelecionado.getUsuario().equals(getUsuarioLogado())
                    && cursoSelecionado.getAlunos().isEmpty()) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }
    
    public void prepararCurso(final Curso curso) {
        try {
            super.limparMensagem();
            cursoSelecionado = curso;
            super.redirect("/pages/curso/cursoAlterar.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void alterarCurso() {
        try {
            super.limparMensagem();
            final CursoService cursoService = (CursoService) ServiceFactory.criarService(ServiceFactory.CURSO);
            cursoService.alterar(cursoSelecionado);
            limparCurso();
            listar();
            super.addMensagemSucesso("Curso alterado com sucesso.");
            super.redirect("/pages/curso/curso.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public boolean podeAlterarCancelar(final Curso curso) {
        boolean retorno = false;
        try {
            if (curso.getUsuario().equals(getUsuarioLogado())
                    && !curso.isCancelado()) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }
    
    public void voltar() {
        try {
            super.limparMensagem();
            super.removerDaSessao("cursoSelecionado");
            listar();
            super.redirect("/pages/curso/curso.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public boolean podeSeInscrever(final Curso curso) {
        boolean retorno = false;
        try {
            if (!curso.getUsuario().equals(getUsuarioLogado())
                    && !curso.isCancelado()
                    && !curso.getAlunos().contains(getUsuarioLogado())) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }
    
    public void seInscrever(final Curso curso) {
        try {
            super.limparMensagem();
            final CursoService cursoService = (CursoService) ServiceFactory.criarService(ServiceFactory.CURSO);
            cursoService.seInscrever(curso, getUsuarioLogado());
            limparCurso();
            listar();
            
            super.addMensagemSucesso("Você se increveu no curso: " + curso.getNome());
            super.redirect("/pages/curso/curso.xhtml");
            
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void cancelar(final Curso curso) {
        try {
            super.limparMensagem();
            final CursoService cursoService = (CursoService) ServiceFactory.criarService(ServiceFactory.CURSO);
            cursoService.cancelar(curso);
            limparCurso();
            listar();
            super.addMensagemSucesso("Curso cancelado com sucesso.");
            super.redirect("/pages/curso/curso.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
    
    public void exibeDetalhes(final Curso curso) {
        try {
            super.limparMensagem();
            this.cursoSelecionado = curso;
            super.guardarNaSessao("cursoSelecionado", curso);
            super.redirect("/pages/curso/cursoDetalhes.xhtml");
            
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
}
