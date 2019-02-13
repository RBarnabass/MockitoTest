import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoInjectedMockTest {

    @Mock Map<String, String> wordMap;
    @InjectMocks MyDictionary dictionary = new MyDictionary();

    @BeforeClass public static void before() {

        MockitoAnnotations.initMocks(MockitoInjectedMockTest.class);
    }

    @Test public void injectedMockTest() {

        when(wordMap.get("aWord")).thenReturn("aMeaning");
        assertEquals("aMeaning", dictionary.getMeaning("aWord"));
    }

    public class MyDictionary {

        Map<String, String> words;

        MyDictionary() {

            words = new HashMap<>();
        }

        public void add(final String word, final String meaning) {

            words.put(word, meaning);
        }
        String getMeaning(final String word) {

            return words.get(word);
        }
    }
}
