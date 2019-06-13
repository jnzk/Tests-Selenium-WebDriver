import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePadChromeTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void openChromeBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void aNotepadInChromeTest(){
        driver.get("https://anotepad.com/");
        driver.findElement(By.name("notetitle")).sendKeys("My First Note");;
        driver.findElement(By.id("btnSaveNote")).click();

        wait = new WebDriverWait(driver, 5);

        String warningMessage = "You have saved your note as a Guest User. You can come back at anytime to continue editing as long as you don't delete your browser cookies." +
                                " To access your notes from anywhere and never lose them, please Create a Free Account. Your existing notes will be saved into your account.";
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".alert.alert-warning"), warningMessage));

        driver.findElement(By.cssSelector(".delete")).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        String noNoteText = "No note here.";
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".saved_notes"), noNoteText));
    }

    @After
    public void closeChromeBrowser(){
        driver.quit();
    }
}
