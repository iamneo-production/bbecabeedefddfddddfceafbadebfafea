import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class EbayProductSearchTest {

    WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "path_to_geckodriver");
            driver = new FirefoxDriver();
        }
        // Other browser configurations (IE, Safari) can be added here.
    }

    @Test
    public void ebayProductSearchTest() {
        driver.get("https://in.ebay.com/");
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        searchBox.sendKeys("Apple Watches");

        // Select category from dropdown
        WebElement categoryDropdown = driver.findElement(By.id("gh-cat"));
        Select categorySelect = new Select(categoryDropdown);
        categorySelect.selectByVisibleText("Electronics");

        WebElement searchButton = driver.findElement(By.id("gh-btn"));
        searchButton.click();

        // Print the result of the product
        WebElement resultCount = driver.findElement(By.className("srp-controls__count-heading"));
        System.out.println("Total results: " + resultCount.getText());

        // Print Nth product (10th product)
        int nthProduct = 10;
        WebElement nthProductElement = driver.findElement(By.xpath("(//li[@class='s-item'])[" + nthProduct + "]"));
        System.out.println("10th Product: " + nthProductElement.getText());

        // Print all products from 1st page
        java.util.List<WebElement> productList = driver.findElements(By.xpath("//li[@class='s-item']"));
        System.out.println("All products on the 1st page:");
        for (WebElement product : productList) {
            System.out.println(product.getText());
        }

        // Scroll down to load more products
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Print all products after scrolling down
        java.util.List<WebElement> allProductsAfterScroll = driver.findElements(By.xpath("//li[@class='s-item']"));
        System.out.println("All products after scrolling down:");
        for (WebElement product : allProductsAfterScroll) {
            System.out.println(product.getText());
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
