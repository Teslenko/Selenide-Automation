//Test introduces the word to GoogleSearch, then opens each link found in turn and looks in it and search the text - "openHAB 2 inside a Docker Container". 

//Method - LocatorLink (); - Collects the main links "a" from the result  search and shows them in the logs.

import com.codeborne.selenide.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

/**
 * Created by vt on 04.07.17.
 */
public class AriaPaglia {

        public static boolean holdBrowserOpen = Boolean.getBoolean("selenide.holdBrowserOpen"); // Dselenide property
        final String browserName = "chrome";
        final int timeOut = 5000;
        final String browserPropertyName = "webdriver.chrome.driver";
        final String browserPropertyPath = "./src/main/resources/chromedriver";

    @Test
    public void setup() throws InterruptedException {

        clearBrowserCache();
        open("https://www.google.ru/");
        $(By.name("q")).val("Docker").pressEnter(); Thread.sleep(500);
       for ( int t=0; t < 2; t++) {                                 // обворачиваем весь тест в облочку цикла

           elementCollection(); //метод корый открывает по очереди каждый найденный результат и проверяет в нем элемент

           locatorlink();                                            // метод который собирает все ссылки

           $(By.id("pnnext")).click(); Thread.sleep(700);            // кликаем на кнопку "след страница"
       }
    }

    @Test
    public void elementCollection() throws InterruptedException{
        ElementsCollection links = $$("div.g>div>div.rc>h3.r>a");    // приравниваем к дочерней ссылки

        for (SelenideElement elem : links) {                         // представление каждого элемента к массиву
            elem.click();                                            // кликаем по каждой найденой Теме
            switchTo().window(1); Thread.sleep(2000);                // переходим в новую вкладку

            ifMetod();

        }
    }

    @Test
    public void ifMetod() throws InterruptedException{

        SelenideElement locator = $(byText("openHAB 2 inside a Docker Container"));

        if (locator.exists()){                                       // Пытаемся найти в открытой вкладке "Текст"
            locator.scrollTo().contextClick(); Thread.sleep(3000);   // Если условие выполнилось, то жмем на "Текст"
        }
        switchTo().window(1).close();                                // если не нашли то закрываем вкладку
        switchTo().window(0);            Thread.sleep(700);          // переходим в первую вкладку
    }

    @Test
    public void locatorlink() throws InterruptedException {

        ElementsCollection link = $$("div.g>div>div.rc>h3.r>a");
        for (int i = 0; i<10; i++) {
            System.out.println(link.get(i).getAttribute("text"));
            System.out.println(link.get(i).getAttribute("href"));
        }
    }

    @Rule
    public TestRule report = new com.codeborne.selenide.junit.TextReport().onFailedTest(true);

    @Before
    public void before(){
        Configuration.browser = browserName;
        Configuration.timeout = timeOut;
        System.setProperty(browserPropertyName, browserPropertyPath);
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tT %4$s %5$s%6$s%n");
    }
}
