package driverConfig;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DriverFactory {
    public static WebDriver getDriver(BROWSER brows) {
        WebDriver driver = null;

        switch (brows) {
            case CHROME:
                driver = initChrome();
                break;
            case FIREFOX:
                driver = initFireFox();
                break;
            case CHROMEPROXY:
                driver = initChromeProxy();
                break;
            case CHROMEPROXYHAR:
                driver=initProxyChromeHar();
                break;
            case LOGWITHOPTIONS:
                driver=inichroWithLogOptions();
        }
        return driver;
    }

    private static WebDriver inichroWithLogOptions() {

        LoggingPreferences prefs = new LoggingPreferences();
        prefs.enable(LogType.BROWSER, Level.WARNING);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
        ChromeOptions options = new ChromeOptions();
        options.merge(capabilities);
        return new ChromeDriver(options);
          }

    private static WebDriver initProxyChromeHar() {
        BrowserMobProxyServer server = new BrowserMobProxyServer();
        server.setTrustAllServers(true);
        server.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        server.start();
        server.newHar("google");


        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(server);
        String hostIp = null;
        try {
            hostIp = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        seleniumProxy.setHttpProxy(hostIp + ":" + server.getPort());
        seleniumProxy.setSslProxy(hostIp + ":" + server.getPort());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        capabilities.setAcceptInsecureCerts(true);////

        ChromeOptions options = new ChromeOptions();
        options.merge(capabilities);

        WebDriver driver = new ChromeDriver(options);
        BaseClass.server = server;
        return driver;
    }

    private static WebDriver initChromeProxy()  {
        BrowserMobProxyServer server = new BrowserMobProxyServer();
        server.setTrustAllServers(true);
        server.start();


        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(server);
        String hostIp = null;
        try {
            hostIp = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        seleniumProxy.setHttpProxy(hostIp + ":" + server.getPort());
        seleniumProxy.setSslProxy(hostIp + ":" + server.getPort());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        capabilities.setAcceptInsecureCerts(true);////

        ChromeOptions options = new ChromeOptions();
        options.merge(capabilities);

        WebDriver driver = new ChromeDriver(options);
        BaseClass.server = server;
        return driver;
    }


    private static WebDriver initFireFox() {
        return new FirefoxDriver();
    }

    private static WebDriver initChrome() {
        Properties props = System.getProperties();
        props.setProperty("webdriver.chrome.driver", "C:\\driversweb\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}
