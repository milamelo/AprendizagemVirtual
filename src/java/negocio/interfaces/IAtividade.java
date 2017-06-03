/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.interfaces;

import java.util.List;
import negocio.entidade.Atividade;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public interface IAtividade {

    List<Atividade> listar(final Atividade atividade) throws Exception;

    boolean atividadeJaAcessadaPeloUsuario(final Atividade atividade, final Usuario usuario) throws Exception;

    int inserirAtividadeUsuario(final Atividade atividade, final Usuario usuario) throws Exception;

    boolean teveAcesso(final Atividade atividade) throws Exception;

    int excluir(final Atividade atividade) throws Exception;

    boolean existeTitulo(final Atividade atividade) throws Exception;

    int inserir(final Atividade atividade) throws Exception;

    int alterar(final Atividade atividade) throws Exception;
}
