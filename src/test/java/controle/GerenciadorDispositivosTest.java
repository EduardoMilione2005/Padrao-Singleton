package controle;

import controle.exception.DispositivoNaoEncontradoException;
import controle.model.Dispositivo;
import controle.model.TipoDispositivo;
import controle.singleton.GerenciadorDispositivos;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GerenciadorDispositivos (Singleton)")
class GerenciadorDispositivosTest {

    private GerenciadorDispositivos gerenciador;

    @BeforeEach
    void setUp() {
        gerenciador = GerenciadorDispositivos.getInstancia();
        gerenciador.limpar();
    }

    @Test
    @DisplayName("Deve retornar sempre a mesma instância")
    void deveSer_mesmaSingleton() {
        GerenciadorDispositivos g1 = GerenciadorDispositivos.getInstancia();
        GerenciadorDispositivos g2 = GerenciadorDispositivos.getInstancia();
        assertSame(g1, g2, "As instâncias devem ser idênticas.");
    }

    @Test
    @DisplayName("Deve registrar dispositivo com sucesso")
    void deveRegistrarDispositivo() {
        Dispositivo d = new Dispositivo("tv01", "Smart TV", TipoDispositivo.TELEVISAO);
        gerenciador.registrar(d);
        assertEquals(1, gerenciador.listarTodos().size());
    }

    @Test
    @DisplayName("Deve lançar exceção ao registrar dispositivo duplicado")
    void deveLancarExcecao_RegistroDuplicado() {
        Dispositivo d = new Dispositivo("tv01", "Smart TV", TipoDispositivo.TELEVISAO);
        gerenciador.registrar(d);
        assertThrows(IllegalArgumentException.class,
                () -> gerenciador.registrar(new Dispositivo("tv01", "Outro", TipoDispositivo.TELEVISAO)));
    }

    @Test
    @DisplayName("Deve lançar exceção ao registrar dispositivo nulo")
    void deveLancarExcecao_DispositivoNulo() {
        assertThrows(IllegalArgumentException.class, () -> gerenciador.registrar(null));
    }

    @Test
    @DisplayName("Deve buscar dispositivo existente")
    void deveBuscarDispositivo() {
        Dispositivo d = new Dispositivo("ac01", "Ar-Cond", TipoDispositivo.AR_CONDICIONADO);
        gerenciador.registrar(d);
        Dispositivo encontrado = gerenciador.buscar("ac01");
        assertEquals("ac01", encontrado.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar dispositivo inexistente")
    void deveLancarExcecao_BuscarInexistente() {
        assertThrows(DispositivoNaoEncontradoException.class,
                () -> gerenciador.buscar("inexistente"));
    }

    @Test
    @DisplayName("Deve remover dispositivo existente")
    void deveRemoverDispositivo() {
        gerenciador.registrar(new Dispositivo("lmp01", "Lâmpada", TipoDispositivo.ILUMINACAO));
        gerenciador.remover("lmp01");
        assertTrue(gerenciador.listarTodos().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover dispositivo inexistente")
    void deveLancarExcecao_RemoverInexistente() {
        assertThrows(DispositivoNaoEncontradoException.class,
                () -> gerenciador.remover("naoexiste"));
    }

    @Test
    @DisplayName("Listar deve retornar todos os dispositivos")
    void deveListarTodos() {
        gerenciador.registrar(new Dispositivo("d1", "D1", TipoDispositivo.SOM));
        gerenciador.registrar(new Dispositivo("d2", "D2", TipoDispositivo.TELEVISAO));
        assertEquals(2, gerenciador.listarTodos().size());
    }

    @Test
    @DisplayName("Listar deve retornar coleção não modificável")
    void deveRetornarColecaoNaoModificavel() {
        gerenciador.registrar(new Dispositivo("d1", "D1", TipoDispositivo.SOM));
        assertThrows(UnsupportedOperationException.class,
                () -> gerenciador.listarTodos().clear());
    }
}
