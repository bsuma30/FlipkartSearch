
package FlipkartSearch;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClickNewestFirstTest {

    @Test
    public void clickNewestFirst() throws InterruptedException {
        WebDriver driver = SearchDataValidationTest.driver;
        WebDriverWait wait = SearchDataValidationTest.wait;

        Actions act = new Actions(SearchDataValidationTest.driver);

        WebElement newestFirst = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[normalize-space(text())='Newest First']")
        ));
        act.moveToElement(newestFirst).click().perform();

        // screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File target_source = new File(System.getProperty("user.dir")
                + "\\src\\test\\resources\\screenshots\\" + timestamp + ".png");
        source.renameTo(target_source);

        Thread.sleep(2000);
    }
}
