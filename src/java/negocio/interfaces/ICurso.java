/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.interfaces;

import java.util.List;
import negocio.entidade.Curso;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public interface ICurso {

    List<Curso> listar(final Curso curso) throws Exception;

    boolean existeNome(final Curso curso) throws Exception;

    int inserir(final Curso curso) throws Exception;

    int atualizar(final Curso curso) throws Exception;

    int seInscrever(final Curso curso, final Usuario usuario) throws Exception;

    int cancelar(final Curso curso) throws Exception;
}
