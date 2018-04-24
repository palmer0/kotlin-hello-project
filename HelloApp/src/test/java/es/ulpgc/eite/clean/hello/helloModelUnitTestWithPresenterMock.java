package es.ulpgc.eite.clean.hello;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class helloModelUnitTestWithPresenterMock {
    private HelloModel _helloModel;
    private Hello.ModelToPresenter _presenterMock;
    private InOrder _inOrderMock;

    @Before
    public void setUp() throws Exception {
        // Creamos un mock del presentador
        _presenterMock = mock (Hello.ModelToPresenter.class);
        _inOrderMock = inOrder(_presenterMock);

        _helloModel = new HelloModel();
        _helloModel.onCreate(_presenterMock);

        //  Desactivamos en el modelo los servicios de Android (porque no están
        //  disponibles cuando se ejecutan los tests con JUnit).
        _helloModel.disableAndroidServices();
    }

    @Test
    public void testStartHelloGetMessageTask() throws Exception {
        _helloModel.resetHelloGetMessageTask();
        _helloModel.startHelloGetMessageTask();

        verify (_presenterMock, times (1)).onHelloGetMessageTaskFinished(anyString());
        _inOrderMock.verify (_presenterMock).onHelloGetMessageTaskFinished("Hello World!");
    }

}