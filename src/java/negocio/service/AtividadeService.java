/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.DAOFactory;
import java.util.List;
import negocio.entidade.Atividade;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;
import negocio.interfaces.IAtividade;

/**
 *
 * @author Camila
 */
public class AtividadeService {

    private final IAtividade iAtividade;

    public AtividadeService() throws ControleException {
        this.iAtividade = (IAtividade) DAOFactory.criar(DAOFactory.ATIVIDADE);
    }

    public List<Atividade> listar(final Atividade atividade) throws ControleException, Exception {
        List<Atividade> atividades = null;
        try {
            atividades = iAtividade.listar(atividade);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AtividadeService.listar");
        }
        return atividades;
    }

    public boolean atividadeJaAcessadaPeloUsuario(final Atividade atividade, final Usuario usuario) throws ControleException, Exception {
        return iAtividade.atividadeJaAcessadaPeloUsuario(atividade, usuario);
    }

    public void visualizarAtividade(final Atividade atividade, final Usuario usuario) throws ControleException, Exception {
        try {
            if (!atividadeJaAcessadaPeloUsuario(atividade, usuario)) {
                int retorno = iAtividade.inserirAtividadeUsuario(atividade, usuario);

                if (retorno == 0) {
                    throw new ControleException("Pontuação da atividade não computada.");
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
            return iAtividade.teveAcesso(atividade);
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
            int retorno = iAtividade.excluir(atividade);
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
            int retorno = iAtividade.inserir(atividade);
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

        if (iAtividade.existeTitulo(atividade)) {
            throw new ControleException("Título já cadastrado.");
        }
    }

    public void alterar(final Atividade atividade) throws ControleException, Exception {
        try {
            validarAtividade(atividade);
            int retorno = iAtividade.alterar(atividade);
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
