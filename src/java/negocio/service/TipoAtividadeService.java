/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOTipoAtividade;
import java.util.List;
import negocio.entidade.TipoAtividade;
import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class TipoAtividadeService {

    private final DAOTipoAtividade daoTipoAtividade;

    public TipoAtividadeService() {
        this.daoTipoAtividade = new DAOTipoAtividade();
    }

    public List<TipoAtividade> listar(final TipoAtividade tipoAtividade) throws ControleException, Exception {
        List<TipoAtividade> tipoAtividades = null;
        try {
            tipoAtividades = daoTipoAtividade.listar(tipoAtividade);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. TipoAtividadeService.listar");
        }
        return tipoAtividades;
    }

    public void inserir(final TipoAtividade tipoAtividade) throws ControleException, Exception {
        try {
            if (daoTipoAtividade.existeDescricao(tipoAtividade)) {
                throw new ControleException("Nome já cadastrado.");
            }

            int retorno = daoTipoAtividade.inserir(tipoAtividade);
            if (retorno == 0) {
                throw new ControleException("Tipo Atividade não cadastrada.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. TipoAtividadeService.inserir");
        }
    }

    public void alterar(final TipoAtividade tipoAtividade) throws ControleException, Exception {
        try {
            if (daoTipoAtividade.existeDescricao(tipoAtividade)) {
                throw new ControleException("Nome já cadastrado.");
            }
            int retorno = daoTipoAtividade.atualizar(tipoAtividade);
            if (retorno == 0) {
                throw new ControleException("Tipo Atividade não atualizada.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. TipoAtividadeService.alterar");
        }
    }

    public void remover(final TipoAtividade tipoAtividade) throws ControleException, Exception {
        try {
            int totalAtividadesComTipoAtividade = daoTipoAtividade.countUsuariosComTipoAtividade(tipoAtividade);
            if (totalAtividadesComTipoAtividade > 0) {
                throw new ControleException("Impossível remover. Total atividade(s) com este Tipo de Atividade: " + totalAtividadesComTipoAtividade + ".");
            }

            int retorno = daoTipoAtividade.remover(tipoAtividade);
            if (retorno == 0) {
                throw new ControleException("Tipo Atividade não removida.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. TipoAtividadeService.remover");
        }
    }
}
