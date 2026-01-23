
package FlipkartSearch;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.time.Duration;

public class SearchDataValidationTest {


    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions act;

    private final String url = "https://www.flipkart.com/";

    @BeforeSuite(alwaysRun = true)
    @Parameters({"browser"})
    public void beforeSuite(@Optional("chrome") String browser) {
        switch (browser.toLowerCase()) {
            case "chrome": driver = new ChromeDriver(); break;
            case "edge":   driver = new EdgeDriver();   break;
            default: throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        act  = new Actions(driver);

        driver.get(url);

    }

    @Test
    public void searchDataValidation() throws InterruptedException {
        WebElement inputSearch = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='q']")));
        inputSearch.clear();
        inputSearch.sendKeys("Mobiles ");
        Thread.sleep(1500);
        inputSearch.sendKeys(" ");      // trigger suggestions

        String target = "mobiles under 15000";
        boolean clicked = false;

        try {
            java.util.List<WebElement> autoItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@id='container']//form/ul/li")
            ));

            for (int i = 1; i <= autoItems.size(); i++) {
                WebElement li = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@id='container']//form/ul/li[" + i + "]")
                ));
                String text = li.getText();
                WebElement anchor = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@id='container']//form/ul/li[" + i + "]//a")
                ));

                if (text != null && text.toLowerCase().contains(target.toLowerCase())) {
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(anchor));
                        act.moveToElement(anchor).click().perform();
                    } catch (Exception e) {
                        anchor.sendKeys(Keys.ENTER);
                    }
                    clicked = true;
                    break;
                }
            }
        } catch (TimeoutException | NoSuchElementException e) {
            System.out.println("Autosuggest not visible; falling back to direct search.");
        }

        if (!clicked) {
            inputSearch.clear();
            inputSearch.sendKeys("mobiles under 15000");
            inputSearch.sendKeys(Keys.ENTER);
        }

        // Wait for results
        wait.until(ExpectedConditions.urlContains("/search"));

    }
}
