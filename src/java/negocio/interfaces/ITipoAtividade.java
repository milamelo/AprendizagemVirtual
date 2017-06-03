/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.interfaces;

import java.util.List;
import negocio.entidade.TipoAtividade;

/**
 *
 * @author Camila
 */
public interface ITipoAtividade {

    List<TipoAtividade> listar(final TipoAtividade tipoAtividade) throws Exception;

    int inserir(final TipoAtividade tipoAtividade) throws Exception;

    int atualizar(final TipoAtividade tipoAtividade) throws Exception;

    int remover(final TipoAtividade tipoAtividade) throws Exception;

    boolean existeDescricao(final TipoAtividade tipoAtividade) throws Exception;

    boolean existeMultiplicidade(final TipoAtividade tipoAtividade) throws Exception;

    int countAtividadesComTipoAtividade(final TipoAtividade tipoAtividade) throws Exception;

    List<TipoAtividade> listarTodos() throws Exception;
}
