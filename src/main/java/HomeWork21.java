import driverConfig.BaseClass;
import models.AGENTS;
import models.UserAgents;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utils.WatcherByClassTest;

import java.util.concurrent.TimeUnit;

public class HomeWork21 extends BaseClass {
    @Rule
    public WatcherByClassTest watcherByClassTest= new WatcherByClassTest();

    @BeforeClass
    public static void before(){
        AGENTS.AgentTyope(UserAgents.SETUSERRU);
        driver.get("https://www.youtube.com/");



    }

    @Test
    public void youTubeScreen(){


        WebElement element;
        element = driver.findElement(By.name("search_query"));


                element.sendKeys("no war");
                element.submit();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);





    }
    @AfterClass
    public static void endAfter(){
        driver.close();
        server.stop();
    }


}
