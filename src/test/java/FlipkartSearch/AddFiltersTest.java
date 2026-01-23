
package FlipkartSearch;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddFiltersTest {

    @Test
    public void addFilters() throws InterruptedException {
        WebDriver driver = SearchDataValidationTest.driver;
        WebDriverWait wait = SearchDataValidationTest.wait;
        Actions act = SearchDataValidationTest.act;

        // Adjust price slider
        WebElement priceFilterKnob = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='lPYKVv jVLYlZ']/child::div")
        ));
        act.moveToElement(priceFilterKnob).perform();
        act.clickAndHold(priceFilterKnob).moveByOffset(-175, 0).release().perform();
        Thread.sleep(1000);

        // OS version Pie
        WebElement operating_system = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[text()='Operating System Version Name']/parent::div")
        ));
        act.moveToElement(operating_system).perform();
        operating_system.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='12 MORE']"))).click();

        WebElement os = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Series 60']")));
        act.moveToElement(os).perform();

        WebElement pie = driver.findElement(By.xpath("//div[@class='buvtMR' and text()='Pie']/parent::label"));
        act.moveToElement(pie).pause(Duration.ofMillis(100)).perform();
        pie.click();

        Thread.sleep(1000);
    }
}
