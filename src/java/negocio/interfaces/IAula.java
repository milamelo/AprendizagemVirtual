/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.interfaces;

import java.util.List;
import negocio.entidade.Aula;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public interface IAula {

    List<Aula> listar(final Aula aula) throws Exception;

    boolean existeTitulo(final Aula aula) throws Exception;

    int inserir(final Aula aula) throws Exception;

    boolean teveAcesso(final Aula aula) throws Exception;

    boolean aulaJaAcessadaPeloUsuario(final Aula aula, final Usuario usuario) throws Exception;

    int inserirAulaUsuario(final Aula aula, final Usuario usuario) throws Exception;

    int alterar(final Aula aula) throws Exception;

    int excluir(final Aula aula) throws Exception;
}
