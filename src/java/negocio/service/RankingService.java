/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAORanking;
import java.util.List;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class RankingService {

    private final DAORanking daoRanking;

    public RankingService() {
        this.daoRanking = new DAORanking();
    }

    public List<Usuario> listarRanking(final int limite) throws ControleException, Exception {
        List<Usuario> usuarios = null;
        try {
            usuarios = daoRanking.listarRanking(limite);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. RankingService.listarRanking");
        }
        return usuarios;
    }

}
