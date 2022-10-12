package utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static driverConfig.BaseClass.getDriver;


public class WatcherByClassTest extends TestWatcher {
    @Override
    protected void failed(Throwable e, Description description) {
        Allure.getLifecycle().addAttachment(
                description.getMethodName(),//Name String
                "image/png",
                "png",
                ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)
        );

    }

    @Override
    protected void succeeded(Description description) {
        File screenshotfile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotfile, new File("screenshot/", description.getMethodName() + ".png") );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
