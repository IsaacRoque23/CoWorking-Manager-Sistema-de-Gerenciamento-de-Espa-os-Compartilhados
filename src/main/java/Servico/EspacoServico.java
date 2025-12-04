package Servico;

import Dados.EspacoDAO;
import Excecoes.EspacoJaExisteException;
import Excecoes.EspacoNaoEncontradoException;
import Modelo.Espaco;
import java.util.List;

public class EspacoServico {

    private EspacoDAO dao = new EspacoDAO();


    public void cadastrar(Espaco espaco) throws EspacoJaExisteException, Exception {
        List<Espaco> lista = dao.carregar();

        for (Espaco e : lista) {
            if (e.getId() == espaco.getId()) {
                throw new EspacoJaExisteException("Já existe um espaço com o ID " + espaco.getId());
            }
        }

        lista.add(espaco);
        dao.salvar(lista);
    }



    public List<Espaco> listar() throws Exception {
        return dao.carregar();
    }


    public void editar(Espaco espacoEditado) throws EspacoNaoEncontradoException, Exception {
        List<Espaco> lista = dao.carregar();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == espacoEditado.getId()) {
                lista.set(i, espacoEditado);
                dao.salvar(lista);
                return;
            }
        }

        throw new EspacoNaoEncontradoException("Espaço com ID " + espacoEditado.getId() + " não encontrado.");
    }


    public void remover(int id) throws EspacoNaoEncontradoException, Exception {
        List<Espaco> lista = dao.carregar();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                dao.salvar(lista);
                return;
            }
        }

        throw new EspacoNaoEncontradoException("Espaço com ID " + id + " não encontrado.");
    }
         }

