/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.dao.DAOAula;
import java.util.List;
import negocio.entidade.Aula;
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
}
