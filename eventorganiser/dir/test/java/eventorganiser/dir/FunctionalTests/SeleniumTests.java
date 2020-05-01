package eventorganiser.dir.FunctionalTests;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTests {

    private static ChromeDriverService service;
    private static String path;
    private static String server;

    @Value("${local.server.port}")
    private int port;

    @BeforeClass
    public static void createAndStartService() throws IOException {
//        server="http://10.72.98.87:8080";
        server="http://localhost:";
        path="/usr/bin/chromedriver";
//        path="C:\\Users\\Kurt\\Dropbox\\Kurt\\Uni\\Dev Ops\\chromedriver_win32\\chromedriver.exe";
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(path))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterClass
    public static void stopService() {
        service.stop();
    }

    @Test
    public void a_testRegister() throws InterruptedException {
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=960x800");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");

        WebDriver driver = new ChromeDriver(options);
        driver.get(server + port + "/register");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='sub']")));
        driver.findElement(By.xpath("//input[@id='un']")).sendKeys("Test9999");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@id='cPassword']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("Test");
        driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("9999");
        driver.findElement(By.xpath("//button[@id='sub']")).submit();
        String login = driver.findElement(By.id("loginbutton")).getText();
        Assert.assertEquals("Sign in", login);
        driver.close();
        driver.quit();
    }

    @Test
    public void b_testLogin(){
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=960x800");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");
        WebDriver driver = new ChromeDriver(options);
        logIn(driver);
        String eventFeed = driver.findElement(By.id("feedHeading")).getText();
        Assert.assertEquals("Events Feed", eventFeed);
        driver.close();
        driver.quit();
    }

    @Test
    public void c_testPostEvent() throws InterruptedException {
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1280,800");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");
        WebDriver driver = new ChromeDriver(options);

        logIn(driver);

        driver.get(server + port + "/addEvent");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submitEventButton")));
        driver.findElement(By.id("eventTitle")).sendKeys("Test Event Title");
        Thread.sleep(500);
//        LocalDate date = LocalDate.now();
//        String tomorrowsdate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date.plusDays(1));
        Thread.sleep(500);
        driver.findElement(By.id("eventStartDate")).sendKeys("01/01/2021");
        Thread.sleep(500);
        driver.findElement(By.id("eventEndDate")).sendKeys("01/01/2021");
        Thread.sleep(500);
        driver.findElement(By.id("eventStartTime")).sendKeys("10:00");
        Thread.sleep(500);
        driver.findElement(By.id("eventEndTime")).sendKeys("14:00");
        Thread.sleep(500);
        driver.findElement(By.id("eventLocSt1")).sendKeys("NSA");
        Thread.sleep(500);
        driver.findElement(By.id("eventLocSt2")).sendKeys("Queensway");
        Thread.sleep(500);
        driver.findElement(By.id("eventLocCity")).sendKeys("Newport");
        Thread.sleep(500);
        driver.findElement(By.id("eventLocPost")).sendKeys("NP20 4AX");
        Thread.sleep(500);
        driver.findElement(By.id("eventDesc")).sendKeys("Test Event for Selenium Web Driver");
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[@id='submitEventButton']")).submit();
        WebDriverWait wait2 = new WebDriverWait(driver, 30);
        wait2.until(ExpectedConditions.textToBe(By.xpath("//h1[@id='eventTitle']"), "Test Event Title"));
        String eventTitle = driver.findElement(By.xpath("//h1[@id='eventTitle']")).getText();
        Assert.assertEquals("Test Event Title", eventTitle);
        driver.close();
        driver.quit();

    }

    @Test
    public void d_testCalendarLoads() {
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=960x800");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");
        WebDriver driver = new ChromeDriver(options);
        logIn(driver);
        driver.get(server + port + "/calendar");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[3]/div[1]/div[3]/div/button[1]")));
        Month month = LocalDate.now().getMonth();
        int year = LocalDate.now().getYear();
        String monthYear = month.toString() + " " + year;
        String calMonthYear = driver.findElement(By.id("calendarTitle")).getText().toUpperCase();
        Assert.assertEquals(monthYear, calMonthYear);
        driver.close();
        driver.quit();
    }

    @Test
    public void e_testCalendarNavigationFunctional() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=960x800");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");
        WebDriver driver = new ChromeDriver(options);
        logIn(driver);
        Thread.sleep(3000);
        driver.get(server + port + "/calendar");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='fc-next-button btn btn-primary']")));
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@class='fc-next-button btn btn-primary']")).click();
        Thread.sleep(3000);
        Month month = LocalDate.now().plusMonths(1).getMonth();
        int year = LocalDate.now().getYear();
        String monthYear = month.toString() + " " + year;
        String calMonthYear = driver.findElement(By.id("calendarTitle")).getText().toUpperCase();
        Assert.assertEquals(monthYear, calMonthYear);
        driver.close();
        driver.quit();
    }

    @Test
    public void f_testEventAttendanceButtonsFunctional() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=960x800");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");
        WebDriver driver = new ChromeDriver(options);
        logIn(driver);
        driver.get(server + port + "/feedPage");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Test Event Title_Going']")));
        driver.findElement(By.xpath("//input[@id='Test Event Title_Going']")).click();
        String selected = driver.findElement(By.xpath("//input[@id='Test Event Title_Going']")).getCssValue("background-color");
        Assert.assertEquals("rgba(211, 211, 211, 1)", selected);
        driver.close();
        driver.quit();
    }

    @Test
    public void g_testEventAttendees() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=960x800");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");
        WebDriver driver = new ChromeDriver(options);
        logIn(driver);
        Thread.sleep(3000);
        driver.get(server + port + "/feedPage");
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[contains(text(),'Test Event Title')]")));
        driver.findElement(By.xpath("//h5[contains(text(),'Test Event Title')]")).click();
        WebDriverWait wait2 = new WebDriverWait(driver, 60);
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='seeAttendeesButton']")));
        driver.findElement(By.xpath("//input[@id='seeAttendeesButton']")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("seeAttendeesButton")).click();
        Thread.sleep(1000);
        String name = driver.findElement(By.xpath("//div[@class='attendees']")).getAttribute("textContent");
        Assert.assertEquals("Test 9999", name);
        driver.close();
        driver.quit();
    }


    @Test
    public void z_testDelete() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=960x800");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");
        WebDriver driver = new ChromeDriver(options);
        logIn(driver);
        Thread.sleep(3000);
        driver.get(server + port + "/deleteEvent");
        Thread.sleep(3000);
        List<WebElement> events = driver.findElements(By.className("list-group-item"));
        driver.findElement(By.id("delete_Test Event Title")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='modal-footer']//input[@class='btn']")).click();
        Thread.sleep(3000);
        Assert.assertEquals(events.size() - 1, driver.findElements(By.className("list-group-item")).size());
        driver.close();
        driver.quit();
    }

    public void logIn(WebDriver driver){
        driver.get(server + port + "/login");
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("loginbutton")));
        driver.findElement(By.id("un")).sendKeys("Test9999");
        driver.findElement(By.id("pw")).sendKeys("1234");
        driver.findElement(By.id("loginbutton")).submit();
    }

}
