package driverConfig;

import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

public class BaseClass {
    public static WebDriver driver;
    public static BrowserMobProxyServer server;


    @BeforeClass
    public static void cinit() {
        driver = DriverFactory.getDriver(BROWSER.CHROMEPROXY);

    }

    @AfterClass
    public static void clobse() {
        driver.close();
        server.stop();
    }

    public static WebDriver getDriver() {
        return driver;
    }

}
