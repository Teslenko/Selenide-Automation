// Тэст вводит слово в гугл, затем открывает по очереди каждую найденную ссылку и ищет в ней
// текст - "openHAB 2 inside a Docker Container".  Метод - LocatorLink(); собирает основные ссылки "а" из результатов 
// поиска в Гугл и показывает их в логах.

import com.codeborne.selenide.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by vt on 04.07.17.
 */
public class AriaPaglia {

        final String browserName = "chrome";
        final int timeOut = 5000;
        final String browserPropertyName = "webdriver.chrome.driver";
        final String browserPropertyPath = "./src/main/resources/chromedriver";

    @Test
    public void findlocator() throws InterruptedException {

        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tT %4$s %5$s%6$s%n");

        open("https://www.google.ru/");
        $(By.name("q")).val("Docker").pressEnter();Thread.sleep(1000);

       for ( int t=0; t < 2; t++) {         // обворачиваем весь тест в облочку цикла

           for (int p = 0; p < 1; p++) {    // обворачиваем основной функционал теста в цикл
               ElementsCollection links = $$("div.g>div>div.rc>h3.r>a");    // приравниваем к дочерней ссылки

               for (SelenideElement elem : links) {                         // представление каждого элемента к массиву
                   elem.click();                                            // кликаем по каждой найденой Теме
                   switchTo().window(1);     Thread.sleep(3000);            // переходим в новую вкладку

                   for ( int q=0; q<1; q++) {
                       SelenideElement locator = $(byText("openHAB 2 inside a Docker Container"));

                       if (locator.exists()){                                       // Пытаемся найти в открытой вкладке "Текст"
                           locator.scrollTo().contextClick(); Thread.sleep(3000);   // Если условие выполнилось, то жмем на "Текст"
                       }
                       switchTo().window(1).close();                                // если не нашли то закрываем вкладку
                       switchTo().window(0);            Thread.sleep(700);          // переходим в первую вкладку
                   }
               }
           }
           locatorlink();                                                           // метод который собирает все ссылки
           $(By.id("pnnext")).click(); Thread.sleep(700);                           // кликаем на кнопку "след страница"
       }
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
    }
}
