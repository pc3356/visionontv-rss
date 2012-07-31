/**
 * 
 */
package tv.visionon.rss.domain;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.EqualsMethodTester;

public class EntryMetaInfoTests {


    @Test
    public void gettersAndSettersShouldFunctionCorrectly() {
        new BeanTester().testBean(EntryMetaInfo.class);
    }

    @Test
    public void equalsMethodShouldObeyContract() {
        new EqualsMethodTester().testEqualsMethod(EntryMetaInfo.class, "id");
    }

}
