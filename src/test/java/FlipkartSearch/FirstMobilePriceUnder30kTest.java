package FlipkartSearch;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class FirstMobilePriceUnder30kTest {

    @Test
    public void firstMobilePriceUnder30k() {
        WebDriverWait wait = SearchDataValidationTest.wait;

        By cardsLocator = By.xpath(
                "//div[contains(@class,'QSCKDh') and contains(@class,'dLgFEE')][2]" +
                        "//div[contains(@class,'lvJbLV') and contains(@class,'col-12-12')]"
        );

        List<WebElement> mobiles =
                RetrieveMobilesDataTest.mobilesList != null
                        ? RetrieveMobilesDataTest.mobilesList
                        : wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cardsLocator));

        Assert.assertTrue(mobiles.size() >= 1, "Need at least one mobile to assert price");
        WebElement firstPriceEl = mobiles.get(0).findElement(By.xpath(".//div[@class='hZ3P6w DeU9vF']"));
        String priceText = firstPriceEl.getText().replace(",", "").replace("₹", "").trim();
        int price = Integer.parseInt(priceText);
        Assert.assertTrue(price < 30000, "Expected first mobile price < 30000, but was " + price);
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (SearchDataValidationTest.driver != null) {
            SearchDataValidationTest.driver.quit();
        }
        SearchDataValidationTest.driver = null;
        SearchDataValidationTest.wait   = null;
        SearchDataValidationTest.act    = null;
    }
}
