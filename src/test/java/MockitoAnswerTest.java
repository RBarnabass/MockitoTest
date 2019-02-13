import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoAnswerTest {

    @BeforeClass public static void before() {

        MockitoAnnotations.initMocks(MockitoAnswerTest.class);
    }

    @Spy List<String> mockList = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Test public void simpleAnswerTest() {

        List<String> listMock = mock(List.class, new CustomAnswer());

        boolean added = listMock.add("hello");

        verify(listMock).add(anyString());
        assertThat(added, is(false));
    }
    @Test public void realMethodTest() {

        when(mockList.add(anyString())).thenReturn(false).thenCallRealMethod();

        assertFalse(mockList.add(anyString()));
        assertTrue(mockList.add(anyString()));
    }



    class CustomAnswer implements Answer<Boolean> {

        @Override public Boolean answer(InvocationOnMock invocation) throws Throwable {

            return false;
        }
    }
}
