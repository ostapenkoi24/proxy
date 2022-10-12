package models;

import driverConfig.BaseClass;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestProxy extends BaseClass {
    @BeforeClass
    public static void initBeforeTestMobile(){
      AGENTS.AgentTyope(UserAgents.IPHONE);
    }

    @Test
    public void test1(){
        driver.get("https://www.youtube.com/");
    }

    @AfterClass
    public static void endAfter(){
        driver.close();
    }
}
