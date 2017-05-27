/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOCurso;
import java.util.List;
import negocio.entidade.Curso;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class CursoService {

    private final DAOCurso daoCurso;

    public CursoService() {
        this.daoCurso = new DAOCurso();
    }

    public List<Curso> listar(final Curso curso) throws ControleException, Exception {
        List<Curso> cursos = null;
        try {
            cursos = daoCurso.listar(curso);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. CursoService.listar");
        }
        return cursos;
    }

    public void inserir(final Curso curso) throws ControleException, Exception {
        try {
            validarCurso(curso);
            int retorno = daoCurso.inserir(curso);
            if (retorno == 0) {
                throw new ControleException("Curso não cadastrado.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. CursoService.inserir");
        }
    }

    public void alterar(final Curso curso) throws ControleException, Exception {
        try {
            validarCurso(curso);
            int retorno = daoCurso.atualizar(curso);
            if (retorno == 0) {
                throw new ControleException("Curso não atualizado.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. CursoService.alterar");
        }
    }

    private void validarCurso(final Curso curso) throws ControleException, Exception {
        if (curso.getDescricao() != null && curso.getDescricao().length() > 1500) {
            throw new ControleException("Descrição não pode conter mais de 1500 caracteres.");
        }

        if (daoCurso.existeNome(curso)) {
            throw new ControleException("Nome já cadastrado.");
        }
    }
    
    public void seInscrever(final Curso curso, final Usuario usuario) throws ControleException, Exception {
        try {
            int retorno = daoCurso.seInscrever(curso, usuario);
            if (retorno == 0) {
                throw new ControleException("Você não se inscreveru no curso: " + curso.getNome());
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. CursoService.seInscrever");
        }
    }
    
    public void cancelar(final Curso curso) throws ControleException, Exception {
        try {
            int retorno = daoCurso.cancelar(curso);
            if (retorno == 0) {
                throw new ControleException("Curso não cancelado.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoService.remover");
        }
    }
}
