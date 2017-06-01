/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOAtividade;
import java.util.List;
import negocio.entidade.Atividade;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class AtividadeService {

    private final DAOAtividade daoAtividade;

    public AtividadeService() {
        this.daoAtividade = new DAOAtividade();
    }

    public List<Atividade> listar(final Atividade atividade) throws ControleException, Exception {
        List<Atividade> atividades = null;
        try {
            atividades = daoAtividade.listar(atividade);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AtividadeService.listar");
        }
        return atividades;
    }
    
    public boolean atividadeJaAcessadaPeloUsuario(final Atividade atividade, final Usuario usuario) throws ControleException, Exception {
        return daoAtividade.atividadeJaAcessadaPeloUsuario(atividade, usuario);
    }
    
    public void visualizarAtividade(final Atividade atividade, final Usuario usuario) throws ControleException, Exception {
        try {
            if (!atividadeJaAcessadaPeloUsuario(atividade, usuario)) {
                int retorno = daoAtividade.inserirAtividadeUsuario(atividade, usuario);

                if (retorno == 0) {
                    throw new ControleException("Pontuação da aula não computada.");
                }
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AtividadeService.visualizarAtividade");
        }
    }
    
    public boolean teveAcesso(final Atividade atividade) throws ControleException, Exception {
        try {
            return daoAtividade.teveAcesso(atividade);
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AtividadeService.teveAcesso");
        }
    }
    
    public void excluir(final Atividade atividade) throws ControleException, Exception {
        try {
            if (teveAcesso(atividade)) {
                throw new ControleException("Atividade não pode ser excluída.");
            }
            int retorno = daoAtividade.excluir(atividade);
            if (retorno == 0) {
                throw new ControleException("Atividade não excluída.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AtividadeService.excluir");
        }
    }
    
    public void inserir(final Atividade atividade) throws ControleException, Exception {
        try {
            validarAtividade(atividade);
            int retorno = daoAtividade.inserir(atividade);
            if (retorno == 0) {
                throw new ControleException("Atividade não cadastrado.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AtividadeService.inserir");
        }
    }
    
    private void validarAtividade(final Atividade atividade) throws ControleException, Exception {
        if (atividade.getDescricao() != null && atividade.getDescricao().length() > 1500) {
            throw new ControleException("Descrição não pode conter mais de 1500 caracteres.");
        }

        if (daoAtividade.existeTitulo(atividade)) {
            throw new ControleException("Título já cadastrado.");
        }
    }
    
    public void alterar(final Atividade atividade) throws ControleException, Exception {
        try {
            validarAtividade(atividade);
            int retorno = daoAtividade.alterar(atividade);
            if (retorno == 0) {
                throw new ControleException("Atividade não atualizada.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AtividadeService.alterar");
        }
    }
}
