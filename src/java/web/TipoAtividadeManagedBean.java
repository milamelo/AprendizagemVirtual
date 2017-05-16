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
import negocio.entidade.TipoAtividade;
import negocio.service.ServiceFactory;
import negocio.service.TipoAtividadeService;

/**
 *
 * @author Camila
 */
@ManagedBean(name = "tipoAtividadeMB")
@SessionScoped
public class TipoAtividadeManagedBean extends MB {

    private TipoAtividade tipoAtividadeSelecionada = new TipoAtividade();
    private TipoAtividade filtroTipoAtividade = new TipoAtividade();
    private List<TipoAtividade> tipoAtividades = new ArrayList<>();

    public TipoAtividade getTipoAtividadeSelecionada() {
        return tipoAtividadeSelecionada;
    }

    public void setTipoAtividadeSelecionada(TipoAtividade tipoAtividadeSelecionada) {
        this.tipoAtividadeSelecionada = tipoAtividadeSelecionada;
    }

    public TipoAtividade getFiltroTipoAtividade() {
        return filtroTipoAtividade;
    }

    public void setFiltroTipoAtividade(TipoAtividade filtroTipoAtividade) {
        this.filtroTipoAtividade = filtroTipoAtividade;
    }

    public List<TipoAtividade> getTipoAtividades() {
        return tipoAtividades;
    }

    public void setTipoAtividades(List<TipoAtividade> tipoAtividades) {
        this.tipoAtividades = tipoAtividades;
    }

    public TipoAtividadeManagedBean() {
        try {
            listar();
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void remover(final TipoAtividade tipoAtividade) {
        try {
            final TipoAtividadeService tipoAtividadeService = (TipoAtividadeService) ServiceFactory.criarService(ServiceFactory.TIPO_ATIVIDADE);
            tipoAtividadeService.remover(tipoAtividade);
            limparTipoAtividade();
            listar();
            super.addMensagemSucesso("Tipo Atividade removida com sucesso.");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void listar() {
        try {
            final TipoAtividadeService tipoAtividadeService = (TipoAtividadeService) ServiceFactory.criarService(ServiceFactory.TIPO_ATIVIDADE);
            tipoAtividades = tipoAtividadeService.listar(filtroTipoAtividade);
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    private void limparTipoAtividade() {
        tipoAtividadeSelecionada = new TipoAtividade();
    }

    public void prepararTipoAtividade(final TipoAtividade tipoAtividade) {
        try {
            super.limparMensagem();
            tipoAtividadeSelecionada = tipoAtividade;
            super.redirect("/pages/tipoAtividade/tipoAtividadeAlterar.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void alterarTipoAtividade() {
        try {
            super.limparMensagem();
            final TipoAtividadeService tipoAtividadeService = (TipoAtividadeService) ServiceFactory.criarService(ServiceFactory.TIPO_ATIVIDADE);
            tipoAtividadeService.alterar(tipoAtividadeSelecionada);
            limparTipoAtividade();
            listar();
            super.addMensagemSucesso("Tipo Atividade alterada com sucesso.");
            super.redirect("/pages/tipoAtividade/tipoAtividade.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void prepararCadastrar() {
        try {
            super.limparMensagem();
            limparTipoAtividade();
            super.redirect("/pages/tipoAtividade/tipoAtividadeCadastro.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void cadastrarTipoAtividade() {
        try {
            super.limparMensagem();
            final TipoAtividadeService tipoAtividadeService = (TipoAtividadeService) ServiceFactory.criarService(ServiceFactory.TIPO_ATIVIDADE);
            tipoAtividadeService.inserir(tipoAtividadeSelecionada);
            limparTipoAtividade();
            listar();
            super.addMensagemSucesso("Tipo Atividade cadastrada com sucesso.");
            super.redirect("/pages/tipoAtividade/tipoAtividade.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }

    public void limpar() {
        filtroTipoAtividade = new TipoAtividade();
    }

    public void localizar() {
        super.limparMensagem();
        listar();
    }

    public void voltar() {
        try {
            super.limparMensagem();
            listar();
            super.redirect("/pages/tipoAtividade/tipoAtividade.xhtml");
        } catch (Exception e) {
            super.addMensagemErro(e.getMessage());
        }
    }
}
