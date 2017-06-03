/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.interfaces;

import java.util.List;
import negocio.entidade.Grupo;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public interface IGrupo {

    List<Grupo> listar(final Grupo grupo) throws Exception;

    boolean existeNome(final Grupo grupo) throws Exception;

    int inserir(final Grupo grupo) throws Exception;

    int entrar(final Grupo grupo, final Usuario usuario) throws Exception;

    int sair(final Grupo grupo, final Usuario usuario) throws Exception;

    int atualizar(final Grupo grupo) throws Exception;

    int remover(final Grupo grupo) throws Exception;
}
