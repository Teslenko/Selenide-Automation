import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import java.awt.*;
import java.awt.event.KeyEvent;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.*;

public class Newjava {

    final String browserName = "chrome";
    final int timeOut = 5000;
    final String browserPropertyName = "webdriver.chrome.driver";
    final String browserPropertyPath = "./src/main/resources/chromedriver";

    @Test
    public void setup () throws InterruptedException, AWTException {
        OpenGoogle();

        FindNeedElement();

        RobotClassAmazon();

    }

    public void OpenGoogle() throws InterruptedException {
        open("https://www.google.ru/");Thread.sleep(700);
        $(By.name("q")).val("Amazon").click();
        $(byText("Amazon.com: Online Shopping for Electronics, Apparel, Computers ...")).click();
        switchTo().window(1); Thread.sleep(1200);
        $(By.id("twotabsearchtextbox")).val("Organo gold").pressEnter();
    }

    public void FindNeedElement() throws InterruptedException {
        int pagesVisited = 0;
        SelenideElement element = $(byText("got organo-? American Apparel Juniors Cut Women's T-Shirt"));
        while (++pagesVisited < 10&& !element.exists()) {
            $(byText("Next Page")).click();Thread.sleep(1200);
        }
        $(byText("got organo-? American Apparel Juniors Cut Women's T-Shirt")).click();

    }

    public void RobotClassAmazon() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        $(byText("LUGGAGE")).contextClick(); Thread.sleep(1200);
        robot.keyPress(KeyEvent.VK_ENTER);   Thread.sleep(1700);
        robot.keyPress(KeyEvent.VK_ESCAPE);  Thread.sleep(1200);
        switchTo().window(2);
        }

    @Before
    public void before(){
    Configuration.browser = browserName;
    Configuration.timeout = timeOut;
        System.setProperty(browserPropertyName, browserPropertyPath);
    }
}













