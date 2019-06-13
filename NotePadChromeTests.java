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

        WebElement noteTitle = driver.findElement(By.name("notetitle"));
        noteTitle.sendKeys("My First Note");

        WebElement saveButton = driver.findElement(By.id("btnSaveNote"));
        saveButton.click();

        wait = new WebDriverWait(driver, 5);

        String warningMessage = "You have saved your note as a Guest User. You can come back at anytime to continue editing as long as you don't delete your browser cookies." +
                                " To access your notes from anywhere and never lose them, please Create a Free Account. Your existing notes will be saved into your account.";
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".alert.alert-warning"), warningMessage));

        WebElement deleteButton = driver.findElement(By.cssSelector(".delete"));
        deleteButton.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        String noNoteText = "No note here.";
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".saved_notes"), noNoteText));
    }

    @After
    public void closeChromeBrowser(){
        driver.quit();
    }
}
