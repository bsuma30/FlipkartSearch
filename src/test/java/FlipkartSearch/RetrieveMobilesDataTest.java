
package FlipkartSearch;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class RetrieveMobilesDataTest {

    public static List<WebElement> mobilesList;

    @Test
    public void retrieveMobilesData() throws IOException {
        WebDriverWait wait = SearchDataValidationTest.wait;

        // Collect cards
        By cardsLocator = By.xpath(
                "//div[contains(@class,'QSCKDh') and contains(@class,'dLgFEE')][2]" +
                        "//div[contains(@class,'lvJbLV') and contains(@class,'col-12-12')]"
        );
        mobilesList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cardsLocator));
        Assert.assertTrue(mobilesList.size() > 0, "Expected at least one mobile card");

        // Create a fresh workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Mobiles");

        // Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Price");

        int limit = Math.min(5, mobilesList.size());
        for (int i = 0; i < limit; i++) {
            WebElement card = mobilesList.get(i);
            String title = card.findElement(By.xpath(".//div[@class='RG5Slk']")).getText();
            String priceText = card.findElement(By.xpath(".//div[@class='hZ3P6w DeU9vF']")).getText();

            Assert.assertFalse(title.isEmpty(), "Title should not be empty for card " + (i + 1));
            Assert.assertFalse(priceText.isEmpty(), "Price should not be empty for card " + (i + 1));

            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(title);
            row.createCell(1).setCellValue(priceText);
        }


        Path filePath = Paths.get(System.getProperty("user.dir"), "\\src\\test\\resources\\resultmobiles.xlsx");
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            workbook.write(fos);
        }
        workbook.close();
    }
}

