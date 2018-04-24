package es.ulpgc.eite.clean.hello;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class helloModelUnitTest {
    private HelloModel _helloModel;

    @Before
    public void setUp() throws Exception {
        _helloModel = new HelloModel();

        //  Para los tests unitarios del modelo no necesitamos crear el presentador.
        //  Por eso llamamos a onCreate con un presentador nulo.
        _helloModel.onCreate(null);
    }

    //  En general no es necesario hacer tests de los getters/setters. Aqui los
    //  tienes como recordatorio.

    @Test
    public void testGetHelloButtonLabel() throws Exception {
        assertEquals("Say Hello", _helloModel.getSayHelloButtonLabel());
    }

    @Test
    public void testGetGoToByeLabel() throws Exception {
        assertEquals("Go To Bye", _helloModel.getGoToByeButtonLabel());
    }

    @Test
    public void testGetHelloMessage() throws Exception {
        assertEquals("Hello World!", _helloModel.getHelloMessage());
    }

    //  En este ejemplo no podemos hacer más tests porque son métodos dependen
    //  del presentador.
}