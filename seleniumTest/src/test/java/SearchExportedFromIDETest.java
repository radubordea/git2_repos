import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;

public class SearchExportedFromIDETest {
    private WebDriver driver;

    private void setupIEDriver(){
        File file = new File("driver/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        driver = new InternetExplorerDriver();
    }

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testSearchExportedFromIDE() throws Exception {
        String baseUrl = "http://www.yr.no";
        driver.get(baseUrl + "/");

        WebElement searchField = driver.findElement(By.id("sted"));
                searchField.clear();
                searchField.sendKeys("Stockholm");
        searchField.submit();

        String topLinkXPathExpression = "//div[@id='directories']/table/tbody/tr/td[2]/a";
        WebElement topSearchResult = driver.findElement(By.xpath(topLinkXPathExpression));
        topSearchResult.click();

        driver.findElement(By.cssSelector("li")).click();
        String expected = "Stockholm";
        WebElement actualHeadLine = driver.findElement(By.cssSelector("h1"));
        String actual = actualHeadLine.getText();
        assertTrue(actual.contains(expected));
    }
}