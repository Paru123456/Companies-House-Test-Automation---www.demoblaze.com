package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    // Captures screenshot and returns file path for Extent Reports
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String destinationPath = System.getProperty("user.dir") + "/reports/screenshots/"
                + screenshotName + "_" + timeStamp + ".png";
        File finalDestination = new File(destinationPath);

        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            System.out.println("Screenshot Capture Failed: " + e.getMessage());
        }

        return destinationPath;
    }

}
