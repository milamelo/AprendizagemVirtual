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
import negocio.entidade.Atividade;
import negocio.entidade.Curso;
import negocio.entidade.TipoAtividade;
import negocio.entidade.Usuario;
import negocio.service.AtividadeService;
import negocio.service.ServiceFactory;
import negocio.service.TipoAtividadeService;
import negocio.service.UsuarioService;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "atividadeMB")
@SessionScoped
public class AtividadeManagedBean extends MB {

    private Atividade filtroAtividade = new Atividade();
    private Atividade atividadeSelecionada = new Atividade();
    private List<Atividade> atividades = new ArrayList<>();

    public Atividade getFiltroAtividade() {
        return filtroAtividade;
    }

    public void setFiltroAtividade(Atividade filtroAtividade) {
        this.filtroAtividade = filtroAtividade;
    }

    public Atividade getAtividadeSelecionada() {
        return atividadeSelecionada;
    }

    public void setAtividadeSelecionada(Atividade atividadeSelecionada) {
        this.atividadeSelecionada = atividadeSelecionada;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public void limparFiltro() {
        this.filtroAtividade = new Atividade();
    }

    public void acessarAtividades() {
        try {
            super.limparMensagem();
            limparFiltro();
            filtroAtividade.setCurso((Curso) pegarDaSessao("cursoSelecionado"));
            listar();
            super.redirect("/pages/curso/atividade/atividade.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void listar() {
        try {
            final AtividadeService atividadeService = (AtividadeService) ServiceFactory.criarService(ServiceFactory.ATIVIDADE);
            atividades = atividadeService.listar(filtroAtividade);
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
            limparAtividade();
            super.redirect("/pages/curso/atividade/atividadeCadastro.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void limparAtividade() {
        this.atividadeSelecionada = new Atividade();
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

    public boolean jaAcessada(final Atividade aula) {
        boolean retorno = false;
        try {
            final AtividadeService atividadeService = (AtividadeService) ServiceFactory.criarService(ServiceFactory.ATIVIDADE);
            if (atividadeService.atividadeJaAcessadaPeloUsuario(aula, getUsuarioLogado())) {
                retorno = true;
            }

        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }

        return retorno;
    }

    public boolean podeVisualizarAtividade(final Atividade atividade) {
        boolean retorno = false;
        try {
            final Curso curso = (Curso) pegarDaSessao("cursoSelecionado");
            if (atividade != null
                    && atividade.getCurso() != null
                    && curso != null
                    && curso.equals(atividade.getCurso())
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

    public void visualizarAtividade(final Atividade atividade) {
        try {
            super.limparMensagem();
            final AtividadeService atividadeService = (AtividadeService) ServiceFactory.criarService(ServiceFactory.ATIVIDADE);
            atividadeSelecionada = atividade;
            atividadeService.visualizarAtividade(atividadeSelecionada, getUsuarioLogado());

            final UsuarioService usuarioService = (UsuarioService) ServiceFactory.criarService(ServiceFactory.USUARIO);
            final Usuario usuario = usuarioService.consultarUsuario(getUsuarioLogado());
            super.guardarNaSessao("usuarioLogado", usuario);

            super.redirect("/pages/curso/atividade/atividadeDetalhes.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public boolean podeAlterarExcluir(final Atividade atividade) {
        boolean retorno = false;
        try {
            final AtividadeService atividadeService = (AtividadeService) ServiceFactory.criarService(ServiceFactory.ATIVIDADE);
            final Curso curso = (Curso) pegarDaSessao("cursoSelecionado");
            if (atividade != null
                    && atividade.getCurso() != null
                    && curso != null
                    && curso.equals(atividade.getCurso())
                    && curso.getUsuario().equals(getUsuarioLogado())
                    && !curso.isCancelado()
                    && !atividadeService.teveAcesso(atividade)) {
                retorno = true;
            }
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return retorno;
    }

    public void prepararAtividade(final Atividade atividade) {
        try {
            super.limparMensagem();
            atividadeSelecionada = atividade;
            super.redirect("/pages/curso/atividade/atividadeAlterar.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void excluir(final Atividade atividade) {
        try {
            super.limparMensagem();
            final AtividadeService atividadeService = (AtividadeService) ServiceFactory.criarService(ServiceFactory.ATIVIDADE);
            atividadeService.excluir(atividade);
            limparAtividade();
            listar();
            super.addMensagemSucesso("Atividade excluída com sucesso.");
            super.redirect("/pages/curso/atividade/atividade.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public List<TipoAtividade> tipoAtividades() {
        List<TipoAtividade> tipoAtividades = null;
        try {
            final TipoAtividadeService tipoAtividadeService = (TipoAtividadeService) ServiceFactory.criarService(ServiceFactory.TIPO_ATIVIDADE);
            tipoAtividades = tipoAtividadeService.listarTodos();
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
        return tipoAtividades;
    }

    public void cadastrarAtividade() {
        try {
            super.limparMensagem();
            final AtividadeService atividadeService = (AtividadeService) ServiceFactory.criarService(ServiceFactory.ATIVIDADE);
            atividadeSelecionada.setCurso((Curso) super.pegarDaSessao("cursoSelecionado"));
            atividadeService.inserir(atividadeSelecionada);
            limparAtividade();
            listar();
            super.addMensagemSucesso("Atividade cadastrada com sucesso.");
            super.redirect("/pages/curso/atividade/atividade.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void voltar() {
        try {
            super.limparMensagem();
            listar();
            super.redirect("/pages/curso/atividade/atividade.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void alterarAtividade() {
        try {
            super.limparMensagem();
            final AtividadeService atividadeService = (AtividadeService) ServiceFactory.criarService(ServiceFactory.ATIVIDADE);
            atividadeSelecionada.setCurso((Curso) super.pegarDaSessao("cursoSelecionado"));
            atividadeService.alterar(atividadeSelecionada);
            limparAtividade();
            listar();
            super.addMensagemSucesso("Atividade alterarda com sucesso.");
            super.redirect("/pages/curso/atividade/atividade.xhtml");
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
