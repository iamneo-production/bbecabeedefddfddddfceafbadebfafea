import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class eBaySearchProduct {

    private WebDriver driver;

    public void setUp(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "path_to_geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "path_to_chromedriver.exe");
                driver = new ChromeDriver();
                break;
            // Add support for other browsers if needed (Internet Explorer, Safari, etc.)
            default:
                System.setProperty("webdriver.chrome.driver", "path_to_chromedriver.exe");
                driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
    }

    public void searchProduct(String product, String category) {
        driver.get("https://in.ebay.com/");

        // Enter product in the search box
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        searchBox.sendKeys(product);

        // Select category from dropdown
        Select categoryDropdown = new Select(driver.findElement(By.id("gh-cat")));
        categoryDropdown.selectByVisibleText(category);

        // Click the Search button
        driver.findElement(By.id("gh-btn")).click();
    }

    public void printSearchResults() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("srp-river-results")));

        // Find all product items on the search results page
        List<WebElement> productItems = driver.findElements(By.cssSelector(".s-item__info.clearfix"));

        // Print the search results
        int index = 1;
        for (WebElement item : productItems) {
            System.out.println("Product " + index + ": " + item.getText());
            index++;
        }
    }

    public void printNthProduct(int n) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("srp-river-results")));

        // Find the Nth product item on the search results page
        WebElement productItem = driver.findElements(By.cssSelector(".s-item__info.clearfix")).get(n - 1);

        // Print the Nth product
        System.out.println("Product " + n + ": " + productItem.getText());
    }

    public void printAllProductsOnFirstPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("srp-river-results")));

        // Find all product items on the first page of search results
        List<WebElement> productItems = driver.findElements(By.cssSelector(".s-item__info.clearfix"));

        // Print all products on the first page
        int index = 1;
        for (WebElement item : productItems)
