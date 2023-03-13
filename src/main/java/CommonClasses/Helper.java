package CommonClasses;

import com.cucumber.listener.Reporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Helper extends AbstractPageObject {

    private static String screenShotPath = "/target/cucumberReports/screenshots/";


    public static void CreateScreenShotsDirectory() {
        try {
            Path dir = Paths.get(FileSystems.getDefault().getPath("").toAbsolutePath() + screenShotPath);
            if (!Files.exists(dir))
                Files.createDirectories(dir);
        } catch (Exception e) {
            System.out.println("CreateScreenShotsDirectory: While managing directories for screenshots: " + e.getMessage());
        }
    }

    public static void CaptureScreenShot(String SceenshotName) throws IOException {
        System.out.println("CaptureScreenShot : Start");

        File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        System.out.println("CaptureScreenShot : sourcePath " + sourcePath);

        Path source = Paths.get(sourcePath.getPath());
        System.out.println("CaptureScreenShot : source " + source);

        File destinationPath = new File(FileSystems.getDefault().getPath("").toAbsolutePath() + screenShotPath + SceenshotName + ".png");
        System.out.println("CaptureScreenShot : destinationPath " + destinationPath);

        Path destination = Paths.get(destinationPath.getPath());
        System.out.println("CaptureScreenShot : destination " + destination);

        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("CaptureScreenShot : Files.copy ");

        Reporter.addScreenCaptureFromPath(destination.toString());
        System.out.println("CaptureScreenShot : addScreenCaptureFromPath ");

        System.out.println("CaptureScreenShot : End");
    }

    public static void PrintBrowserLogs() {
        System.out.println("PrintBrowserLogs: Start");
        // Mentioning type of Log
        LogEntries entry = driver.manage().logs().get(LogType.BROWSER);
        // Retrieving all log
        List<LogEntry> logs = entry.getAll();
        // Print one by one
        for (LogEntry e : logs) {
            System.out.println(e);
        }
        // Printing details separately
        for (LogEntry e : logs) {
            System.out.println("Message is: " + e.getMessage());
            System.out.println("Level is: " + e.getLevel());
        }
        System.out.println("PrintBrowserLogs: End");

        try {
            Reporter.addStepLog("Browser Logs: ");
            for (LogEntry e : logs) {
                Reporter.addStepLog("Message is: " + e.getMessage());
                Reporter.addStepLog("Level is: " + e.getLevel());
            }
        } catch (NullPointerException e) {
            System.out.println("No Report exist");
        }
    }

    public static String getCurrentDateTime(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).toString();
    }
}