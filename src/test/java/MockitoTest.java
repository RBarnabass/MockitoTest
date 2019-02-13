import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @BeforeClass public static void before() {

        MockitoAnnotations.initMocks(MockitoTest.class);
    }

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Mock List<String> mockitoList;
    @Mock List<Integer> mockList;

    @SuppressWarnings("unchecked")
    @Test public void simpleOldMockWithVerifyTest() {

        List mockList = mock(ArrayList.class);
        mockList.add(1);
        verify(mockList).add(1);
    }

    @SuppressWarnings("unchecked")
    @Test public void simpleOldMockWithSizeTest() {

        List mockList = mock(ArrayList.class);
        mockList.add(1);
        assertEquals(0, mockList.size());
    }

    @Test public void annotatedMockTest() {

        mockList.add(1);
        verify(mockList).add(1);
        assertEquals(0, mockList.size());
    }

    @Test public void usingTimesAndAnyIntMethodsTest() {

        mockList.add(1);
        mockList.add(2);

        verify(mockList, times(2)).add(anyInt());
    }

    @Test public void usingWhenAndReturnMethodsTest() {

        mockList.add(1);
        verify(mockList).add(1);
        assertEquals(0, mockList.size());

        when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }

    @SuppressWarnings("unchecked")
    @Test public void severalReturns() {

        when(mockitoList.size()).thenReturn(100).thenReturn(200).thenReturn(300);

        assertEquals(100, mockitoList.size());
        assertEquals(200, mockitoList.size());
        assertEquals(300, mockitoList.size());
        assertEquals(300, mockitoList.size());


        reset(mockitoList);
        assertEquals(0, mockitoList.size());
    }

    @Test(expected = RuntimeException.class)
    public void usingReturnWithExceptionTest() {

        mockList.add(1);
        mockList.add(2);
        verify(mockList, times(2)).add(anyInt());

        when(mockList.size()).thenThrow(new RuntimeException());
        mockList.size();
    }

    @Test public void anyStringAndExceptionExpectWithRuleTest() {

        when(mockitoList.add(anyString())).thenThrow(new RuntimeException());
        thrown.expect(RuntimeException.class);
        mockitoList.add(anyString());
    }

    @Test public void voidMethodDoAnswerExceptionTest() {

        doAnswer(invocation -> new RuntimeException()).when(mockitoList).add(0, "hello");

        mockitoList.add(0, "hello");
    }

    @Test(expected = RuntimeException.class)
    public void voidMethodTest() {

        doThrow(new RuntimeException()).when(mockitoList).clear();
        mockitoList.clear();

        verify(mockitoList, atLeastOnce()).clear();
    }
}
