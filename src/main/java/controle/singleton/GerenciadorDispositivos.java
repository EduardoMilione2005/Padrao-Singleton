package controle.singleton;

import controle.exception.DispositivoNaoEncontradoException;
import controle.model.Dispositivo;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GerenciadorDispositivos {



    private static GerenciadorDispositivos instancia;


    private GerenciadorDispositivos() {
        dispositivos = new LinkedHashMap<>();
    }

    public static synchronized GerenciadorDispositivos getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorDispositivos();
        }
        return instancia;
    }


    private final Map<String, Dispositivo> dispositivos;


    public void registrar(Dispositivo d) {
        if (d == null) throw new IllegalArgumentException("Dispositivo não pode ser nulo.");
        if (dispositivos.containsKey(d.getId()))
            throw new IllegalArgumentException("Já existe um dispositivo com id: " + d.getId());
        dispositivos.put(d.getId(), d);
    }

    public Dispositivo buscar(String id) {
        Dispositivo d = dispositivos.get(id);
        if (d == null) throw new DispositivoNaoEncontradoException(id);
        return d;
    }

    public void remover(String id) {
        if (!dispositivos.containsKey(id)) throw new DispositivoNaoEncontradoException(id);
        dispositivos.remove(id);
    }

    public Collection<Dispositivo> listarTodos() {
        return Collections.unmodifiableCollection(dispositivos.values());
    }

    public void limpar() {
        dispositivos.clear();
    }


    static void resetarInstancia() {
        instancia = null;
    }
}
