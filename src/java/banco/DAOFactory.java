/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import banco.dao.DAOAtividade;
import banco.dao.DAOAula;
import banco.dao.DAOCurso;
import banco.dao.DAOGrupo;
import banco.dao.DAOGrupoUsuarioMensagem;
import banco.dao.DAOMedalha;
import banco.dao.DAORanking;
import banco.dao.DAOTipoAtividade;
import banco.dao.DAOUsuario;
import negocio.excecao.ControleException;
import negocio.interfaces.IAtividade;
import negocio.interfaces.IAula;
import negocio.interfaces.ICurso;
import negocio.interfaces.IGrupo;
import negocio.interfaces.IGrupoUsuarioMensagem;
import negocio.interfaces.IMedalha;
import negocio.interfaces.IRanking;
import negocio.interfaces.ITipoAtividade;
import negocio.interfaces.IUsuario;

/**
 *
 * @author Camila
 */
public class DAOFactory {

    public static final byte USUARIO = 0;
    public static final byte MEDALHA = 1;
    public static final byte TIPO_ATIVIDADE = 2;
    public static final byte GRUPO = 3;
    public static final byte CURSO = 4;
    public static final byte AULA = 5;
    public static final byte ATIVIDADE = 6;
    public static final byte GRUPO_USUARIO_MENSAGEM = 7;
    public static final byte RANKING = 8;

    private static IUsuario iUsuario;
    private static IMedalha iMedalha;
    private static ITipoAtividade iTipoAtividade;
    private static IGrupo iGrupo;
    private static ICurso iCurso;
    private static IAula iAula;
    private static IAtividade iAtividade;
    private static IGrupoUsuarioMensagem iGrupoUsuarioMensagem;
    private static IRanking iRanking;

    public static Object criar(final byte id) throws ControleException {
        try {
            switch (id) {
                case USUARIO:
                    if (iUsuario == null) {
                        iUsuario = new DAOUsuario();
                    }
                    return iUsuario;

                case MEDALHA:
                    if (iMedalha == null) {
                        iMedalha = new DAOMedalha();
                    }
                    return iMedalha;

                case TIPO_ATIVIDADE:
                    if (iTipoAtividade == null) {
                        iTipoAtividade = new DAOTipoAtividade();
                    }
                    return iTipoAtividade;

                case GRUPO:
                    if (iGrupo == null) {
                        iGrupo = new DAOGrupo();
                    }
                    return iGrupo;

                case CURSO:
                    if (iCurso == null) {
                        iCurso = new DAOCurso();
                    }
                    return iCurso;

                case AULA:
                    if (iAula == null) {
                        iAula = new DAOAula();
                    }
                    return iAula;

                case ATIVIDADE:
                    if (iAtividade == null) {
                        iAtividade = new DAOAtividade();
                    }
                    return iAtividade;

                case GRUPO_USUARIO_MENSAGEM:
                    if (iGrupoUsuarioMensagem == null) {
                        iGrupoUsuarioMensagem = new DAOGrupoUsuarioMensagem();
                    }
                    return iGrupoUsuarioMensagem;

                case RANKING:
                    if (iRanking == null) {
                        iRanking = new DAORanking();
                    }
                    return iRanking;

                default:
                    throw new ControleException("Erro ao criar DAO");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("Erro: DAOFactory.criar");
        }
    }
}
