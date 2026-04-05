package controle;

import controle.controller.RegistroController;
import controle.exception.DispositivoNaoEncontradoException;
import controle.model.Dispositivo;
import controle.model.TipoDispositivo;
import controle.singleton.GerenciadorDispositivos;
import org.junit.jupiter.api.*;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RegistroController")
class RegistroControllerTest {

    private RegistroController registro;

    @BeforeEach
    void setUp() {
        GerenciadorDispositivos.getInstancia().limpar();
        registro = new RegistroController();
    }

    @Test
    @DisplayName("Deve cadastrar dispositivo e retorná-lo")
    void deveCadastrarDispositivo() {
        Dispositivo d = registro.cadastrar("som01", "Caixa de Som", TipoDispositivo.SOM);
        assertNotNull(d);
        assertEquals("som01",        d.getId());
        assertEquals("Caixa de Som", d.getNome());
        assertEquals(TipoDispositivo.SOM, d.getTipo());
    }

    @Test
    @DisplayName("Deve listar dispositivos cadastrados")
    void deveListarDispositivos() {
        registro.cadastrar("d1", "D1", TipoDispositivo.TELEVISAO);
        registro.cadastrar("d2", "D2", TipoDispositivo.ILUMINACAO);
        Collection<Dispositivo> lista = registro.listarTodos();
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Deve remover dispositivo existente")
    void deveRemoverDispositivo() {
        registro.cadastrar("vent01", "Ventilador", TipoDispositivo.VENTILADOR);
        registro.remover("vent01");
        assertTrue(registro.listarTodos().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover dispositivo inexistente")
    void deveLancarExcecao_RemoverInexistente() {
        assertThrows(DispositivoNaoEncontradoException.class,
                () -> registro.remover("naoexiste"));
    }

    @Test
    @DisplayName("Dispositivo deve iniciar desligado e nível zero")
    void deveIniciarDesligado() {
        Dispositivo d = registro.cadastrar("lmp01", "Lâmpada", TipoDispositivo.ILUMINACAO);
        assertFalse(d.isLigado());
        assertEquals(0, d.getNivel());
    }
}
