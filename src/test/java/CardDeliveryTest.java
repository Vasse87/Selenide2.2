import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    String generateDate (int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldReturnPositiveResult() {
        String date = generateDate(3, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + date)).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldReturnNegativeResultNotBigСity() {
        String date = generateDate(3, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Костомукша");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id= city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldReturnNegativeResultCityIsNotInserted() {
        String date = generateDate(3, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id= city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldReturnNegativeResultDateIsEarly() {
        String date = generateDate(2, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id = date] .input_invalid").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldReturnNegativeResultDateIsNotInserted() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id = date] .input_invalid").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldReturnNegativeResultDateIsToday() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(String.valueOf(LocalDate.now()));
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id = date] .input_invalid").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldReturnPositiveResultWithDateInAYear() {
        String date = generateDate(365, "dd.MM.yyyy");
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + date)).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldReturnPositiveResultDoubleName() {
        String date = generateDate(3, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Васильева Софья-Луиза");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + date)).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldReturnPositiveResultOnlyName() {
        String date = generateDate(3, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Софья");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + date)).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldReturnNegativeResultEnglishName() {
        String date = generateDate(3, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Anna Bolton");
        $("[data-test-id = phone] input").setValue("+79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id= name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldReturnNegativeResultPhoneWithoutPlus() {
        String date = generateDate(3, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("79500120357");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id= phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldReturnNegativeResultShortNumber() {
        String date = generateDate(3, "dd.MM.yyyy" );
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = city] input").setValue("Самара");
        $("[data-test-id = date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Васильева Софья");
        $("[data-test-id = phone] input").setValue("5875446");
        $("[data-test-id = agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id= phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldReturnNegativeEmpty() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $(byText("Забронировать")).click();
        $("[data-test-id= city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
