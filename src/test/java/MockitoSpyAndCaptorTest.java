import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSpyAndCaptorTest {

    @Spy List<Integer> spiedList = new ArrayList<>();
    @Captor ArgumentCaptor<Integer> argCaptor;

    @BeforeClass public static void before() {

        MockitoAnnotations.initMocks(MockitoSpyAndCaptorTest.class);
    }

    @Test public void simpleSpyTest() {

        spiedList.add(1);
        spiedList.add(2);
        verify(spiedList).add(1);
        verify(spiedList).add(2);

        assertEquals(2, spiedList.size());

        when(spiedList.size()).thenReturn(100);
        assertEquals(100, spiedList.size());
    }
    @Test public void simpleCaptorTest() {

        Random random = new Random();
        int number = random.nextInt();

        spiedList.add(number);
        verify(spiedList).add(argCaptor.capture());
        assertEquals(1, spiedList.size());

        assertEquals(number, argCaptor.getValue().intValue());
    }

    @Test public void doNothingTest() {

        spiedList.add(1);
        spiedList.add(2);
        assertEquals(2, spiedList.size());

        doNothing().when(spiedList).clear();
        spiedList.clear();

        assertEquals(2, spiedList.size());
    }
}
