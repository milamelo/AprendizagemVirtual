/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.DAOFactory;
import java.util.List;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;
import negocio.interfaces.IRanking;

/**
 *
 * @author Camila
 */
public class RankingService {

    private final IRanking iRanking;

    public RankingService() throws ControleException {
        this.iRanking = (IRanking) DAOFactory.criar(DAOFactory.RANKING);
    }

    public List<Usuario> listarRanking(final int limite) throws ControleException, Exception {
        List<Usuario> usuarios = null;
        try {
            usuarios = iRanking.listarRanking(limite);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. RankingService.listarRanking");
        }
        return usuarios;
    }

}
