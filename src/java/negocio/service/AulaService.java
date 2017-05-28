/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOAula;
import java.util.List;
import negocio.entidade.Aula;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class AulaService {

    private final DAOAula daoAula;

    public AulaService() {
        this.daoAula = new DAOAula();
    }

    public List<Aula> listar(final Aula aula) throws ControleException, Exception {
        List<Aula> aulas = null;
        try {
            aulas = daoAula.listar(aula);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AulaService.listar");
        }
        return aulas;
    }

    public void inserir(final Aula aula) throws ControleException, Exception {
        try {
            validarAula(aula);
            int retorno = daoAula.inserir(aula);
            if (retorno == 0) {
                throw new ControleException("Aula não cadastrado.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AulaService.inserir");
        }
    }

    private void validarAula(final Aula aula) throws ControleException, Exception {
        if (aula.getConteudo() != null && aula.getConteudo().length() > 1500) {
            throw new ControleException("Conteúdo não pode conter mais de 1500 caracteres.");
        }

        if (daoAula.existeTitulo(aula)) {
            throw new ControleException("Título já cadastrado.");
        }
    }

    public boolean teveAcesso(final Aula aula) throws ControleException, Exception {
        try {
            return daoAula.teveAcesso(aula);
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AulaService.teveAcesso");
        }
    }

    public void visualizarAula(final Aula aula, final Usuario usuario) throws ControleException, Exception {
        try {
            if (!aulaJaAcessadaPeloUsuario(aula, usuario)) {
                int retorno = daoAula.inserirAulaUsuario(aula, usuario);

                if (retorno == 0) {
                    throw new ControleException("Pontuação da aula não computada.");
                }
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AulaService.visualizarAula");
        }
    }
    
    public boolean aulaJaAcessadaPeloUsuario(final Aula aula, final Usuario usuario) throws ControleException, Exception {
        return daoAula.aulaJaAcessadaPeloUsuario(aula, usuario);
    }
    
    public void alterar(final Aula aula) throws ControleException, Exception {
        try {
            validarAula(aula);
            int retorno = daoAula.alterar(aula);
            if (retorno == 0) {
                throw new ControleException("Aula não atualizada.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AulaService.alterar");
        }
    }
    
    public void excluir(final Aula aula) throws ControleException, Exception {
        try {
            if (teveAcesso(aula)) {
                throw new ControleException("Aula não pode ser excluída.");
            }
            int retorno = daoAula.excluir(aula);
            if (retorno == 0) {
                throw new ControleException("Aula não excluída.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. AulaService.excluir");
        }
    }
}
