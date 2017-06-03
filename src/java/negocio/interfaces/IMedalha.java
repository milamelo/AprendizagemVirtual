/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.interfaces;

import java.util.List;
import negocio.entidade.Medalha;

/**
 *
 * @author Camila
 */
public interface IMedalha {

    List<Medalha> listar(final Medalha medalha) throws Exception;

    int inserir(final Medalha medalha) throws Exception;

    int atualizar(final Medalha medalha) throws Exception;

    int remover(final Medalha medalha) throws Exception;

    boolean existePontuacao(final Medalha medalha) throws Exception;

    boolean existeNome(final Medalha medalha) throws Exception;

    int countUsuariosComMedalha(final Medalha medalha) throws Exception;
}
