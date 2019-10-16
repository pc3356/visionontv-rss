package tv.visionon.rss.domain;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.EqualsMethodTester;

public class FeedMetaInfoTests {

    @Test
    public void gettersAndSettersShouldFunctionCorrectly() {
        new BeanTester().testBean(FeedMetaInfo.class);
    }

    @Test
    public void equalsMethodShouldObeyContract() {
        new EqualsMethodTester().testEqualsMethod(FeedMetaInfo.class, "id");
    }
}
