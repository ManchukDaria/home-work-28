package org.hillel.qa.hw28;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;

import java.util.UUID;

public class TestUsersBugRed {

    private WebDriver driver = new OperaDriver();

    @BeforeClass
    public static void beforeAll() {
        System.setProperty("webdriver.opera.driver", "C:\\IT\\QA\\webdrivers\\opera\\operadriver.exe");
    }

    @Before
    public void doLogin() {
        driver.get("http://users.bugred.ru/user/login/index.html");
        driver.manage().window().maximize();

        WebElement email = driver.findElement(By.name("login"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("input[type=\"submit\"]"));

        email.sendKeys("manchukd24@gmail.com");
        password.sendKeys("180791Dasha");
        loginButton.click();
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @Test
    public void shouldBeLoggedIn() {
        WebElement menu = driver.findElement(By.id("fat-menu"));
        Assert.assertNotNull(menu);
    }

    @Test
    public void shouldFindUser() {
        WebElement searchField = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[5]/td[1]/button"));

        Assert.assertNotNull(searchField);
        Assert.assertNotNull(searchButton);

        searchField.sendKeys("manchukd24@gmail.com");
        searchButton.click();

        WebElement foundElement = driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr/td[1]"));

        String actualEmail = foundElement.getText();
        Assert.assertEquals("manchukd24@gmail.com", actualEmail);
    }

    @Test
    public void shouldAddNewUser() {
        WebElement addUserBtn = driver.findElement(By.xpath("/html/body/div[3]/p[1]/a"));
        addUserBtn.click();

        WebElement newUserEmail = driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[2]/td[2]/input"));
        WebElement newUserPass = driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[3]/td[2]/input"));
        WebElement createUserButton = driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[21]/td[2]/input"));

        newUserEmail.sendKeys("1@1.us");
        newUserPass.sendKeys("111");
        createUserButton.click();

        WebElement createdUserEmail = driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[1]"));
        WebElement authorEmail = driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[3]"));

        String newEmail = createdUserEmail.getText();
        String email = authorEmail.getText().trim();

        Assert.assertEquals("1@1.us", newEmail);
        Assert.assertEquals("manchukd24@gmail.com", email);

        driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[6]/a")).click();
    }

    @Test
    public void shouldEditProfile() {
        WebElement profileMenu = driver.findElement(By.xpath("//*[@id=\"fat-menu\"]/a"));
        profileMenu.click();

        WebElement profileLink = driver.findElement(By.xpath("//*[@id=\"fat-menu\"]/ul/li[1]/a"));
        profileLink.click();

        WebElement hobby = driver.findElement(By.name("hobby"));
        hobby.clear();
        hobby.sendKeys(UUID.randomUUID().toString());

        WebElement inn = driver.findElement(By.name("inn"));
        inn.clear();
        inn.sendKeys("112233445566");

        WebElement birthDate = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/form/table/tbody/tr[4]/td[2]/input"));
        birthDate.sendKeys("18.07.1991");

        WebElement gender = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/form/table/tbody/tr[3]/td[2]/select"));
        gender.sendKeys("Женский");

        WebElement name = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/form/table/tbody/tr[2]/td[2]/input"));
        name.clear();
        name.sendKeys("Daria");

        WebElement saveNewProfileButton = driver.findElement(By.name("act_profile_now"));
        saveNewProfileButton.click();

        WebElement newProfileName = driver.findElement(By.xpath("//*[@id=\"fat-menu\"]/a"));
        String newName = newProfileName.getText().trim();

        Assert.assertEquals("Daria", newName);
    }
}
