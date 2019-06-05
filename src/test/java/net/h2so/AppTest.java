package net.h2so;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfigManagerTest.class})
public class AppTest {

    @Autowired
    private LockService lockService;

    @Test
    public void simpleTest1() throws Exception {
        lockService.getValue("22");
    }

    @Test
    public void simpleTest2() throws Exception {
        ObjectParam objectParam = new ObjectParam("id", "name", false);
        lockService.getValue(objectParam);
    }

}
