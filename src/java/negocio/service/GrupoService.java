/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import banco.DAOFactory;
import java.util.List;
import negocio.entidade.Grupo;
import negocio.entidade.Usuario;
import negocio.excecao.ControleException;
import negocio.interfaces.IGrupo;

/**
 *
 * @author Camila
 */
public class GrupoService {

    private final IGrupo iGrupo;

    public GrupoService() throws ControleException {
        this.iGrupo = (IGrupo) DAOFactory.criar(DAOFactory.GRUPO);
    }

    public List<Grupo> listar(final Grupo grupo) throws ControleException, Exception {
        List<Grupo> grupos = null;
        try {
            grupos = iGrupo.listar(grupo);
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoService.listar");
        }
        return grupos;
    }

    public void inserir(final Grupo grupo) throws ControleException, Exception {
        try {
            if (iGrupo.existeNome(grupo)) {
                throw new ControleException("Nome já cadastrado.");
            }
            int retorno = iGrupo.inserir(grupo);
            if (retorno == 0) {
                throw new ControleException("Grupo não cadastrado.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoService.inserir");
        }
    }

    public void entrar(final Grupo grupo, final Usuario usuario) throws ControleException, Exception {
        try {
            int retorno = iGrupo.entrar(grupo, usuario);
            if (retorno == 0) {
                throw new ControleException("Você não entrou no grupo: " + grupo.getNome());
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoService.entrar");
        }
    }

    public void sair(final Grupo grupo, final Usuario usuario) throws ControleException, Exception {
        try {
            int retorno = iGrupo.sair(grupo, usuario);
            if (retorno == 0) {
                throw new ControleException("Você não saiu do grupo: " + grupo.getNome());
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoService.sair");
        }
    }

    public void alterar(final Grupo grupo) throws ControleException, Exception {
        try {
            if (iGrupo.existeNome(grupo)) {
                throw new ControleException("Nome já cadastrado.");
            }
            int retorno = iGrupo.atualizar(grupo);
            if (retorno == 0) {
                throw new ControleException("Grupo não atualizado.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoService.alterar");
        }
    }

    public void remover(final Grupo grupo) throws ControleException, Exception {
        try {
            if (grupo.getUsuarios().size() > 1) {
                throw new ControleException("Impossível remover. Total usuário(s) no grupo: " + grupo.getUsuarios().size() + ".");
            }

            int retorno = iGrupo.remover(grupo);
            if (retorno == 0) {
                throw new ControleException("Grupo não removido.");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("ERRO INESPERADO. GrupoService.remover");
        }
    }
}
