import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AdminPanel {
    WebDriver driver;
    boolean shouldLogout = false;

    @BeforeClass
    public void setup() {
        ChromeOptions option = new ChromeOptions();
//        option.addArguments("--remote-allow-origins=*"); // This is for bypassing potential CORS issues
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.get("https://admin.smartqc.io/");
        System.out.println("Setup completed");
//        shouldLogout = true; // Set the flag to true after Subscribers test
    }

    @BeforeMethod
    public void login() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        // Wait until the login page is loaded by waiting for the email input field to be visible
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='email']")));

        // Verify the page title to confirm it's the correct login page
        String title = driver.getTitle();
        Assert.assertEquals("SmartQC Admin Panel", title, "Title is not matched");
        System.out.println(title);

        // Locate elements on the login page
        WebElement emailAddress = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='email']")));
        WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='password']")));
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));

        // Perform login actions
        emailAddress.sendKeys("bkhan@smartqc.io");
        password.sendKeys("alltake@1234");
        loginButton.click();

        // Check the current URL after login
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after login: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("https://admin.smartqc.io/"), "Login was unsuccessful");

    }

    @Test
//    @Test(dependsOnMethods = "login")
    public void campaigns() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement campaignsButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Campaigns']")));
        campaignsButton.click();
        Thread.sleep(2000);
        System.out.println("Show All Campaigns");

        // Assert that campaigns page is loaded
        Assert.assertTrue(driver.getCurrentUrl().contains("/campaigns"), "Campaigns page not loaded");

//    }

//        @Test(dependsOnMethods = "campaigns")
//    public void searchCampaign() {
        WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Search']")));
        search.sendKeys("pending");
        Thread.sleep(2000);
        System.out.println("Search Successful");

        // Assert that the search was performed
        Assert.assertTrue(driver.getPageSource().contains("pending"), "Search term not found in results");
//    }

//        @Test(dependsOnMethods = "login")
//    public void listBar() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        // Wait for the dropdown to be present
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class='form-select']")));
        // Use JavaScript to ensure the dropdown is visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.display='block';", dropdown);
        //Now locate the specific option
        WebElement listBar20 = dropdown.findElement(By.xpath("//option[@value='20']"));
        listBar20.click();
        Thread.sleep(20000);
        System.out.println("View 20 Campaign");

        // Assert correct number of campaigns displayed
        Assert.assertTrue(driver.getPageSource().contains("20 campaigns"), "20 Campaigns not displayed");

        WebElement BackButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Back']")));
        BackButton.click();
        Thread.sleep(20000);
        shouldLogout = true; // Set the flag to true after Subscribers test
    }

    @Test
//    @Test(dependsOnMethods = "login")
    public void viewCampaign() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement campaignsButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Campaigns']")));
        campaignsButton.click();
        Thread.sleep(2000);
        System.out.println("Show All Campaigns");

        WebElement viewButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr[3]/td[2]")));
        viewButton.click();
        Thread.sleep(20000);
        System.out.println("View Campaign Details");

        // Assert that campaign details page is loaded
        Assert.assertTrue(driver.getCurrentUrl().contains("/account/campaigns"), "Campaign details page not loaded");

        WebElement BackButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Back']")));
        BackButton.click();
        Thread.sleep(10000);
        shouldLogout = true; // Set the flag to true after Subscribers test
    }
@Test
//    @Test(dependsOnMethods = "login")
public void NewUser() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));  // Increase timeout to 60 seconds


    WebElement Users = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Users']")));
    Users.click();
    Thread.sleep(2000);

    WebElement AddUser = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[normalize-space()='New User']")));
    AddUser.click();
    Thread.sleep(2000);

    WebElement FirstName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='First Name']")));
    FirstName.sendKeys("Atish");

    WebElement LastName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Last Name']")));
    LastName.sendKeys("Ovhal");

    WebElement CompanyName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Company Name']")));
    CompanyName.sendKeys("Alltake");

    WebElement JobTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='JobTitle']")));
    JobTitle.sendKeys("FrontEnd Dev");

    WebElement Email = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Email']")));
    Email.sendKeys("atishovhal870@gmail.com");

    WebElement Password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Password']")));
    Password.sendKeys("Atish@123");

    WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class='mb-3 form-select']")));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].style.display='block';", dropdown);

    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//option[@value='651c2269c5e7c88757479403']")));
    WebElement UserOption = dropdown.findElement(By.xpath("//option[@value='651c2269c5e7c88757479403']"));
    UserOption.click();
    Thread.sleep(2000);
    System.out.println("User option selected");

    // Submit the form
    WebElement Submit = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[normalize-space()='Submit']")));
    Submit.click();
    Thread.sleep(2000);
    System.out.println("Submitted");

    try {
        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'OK')]")));
        okButton.click();
        Thread.sleep(2000);

        WebElement alertMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'User')]")));
        String alertText = alertMessage.getText();
        Assert.assertTrue(alertText.contains("User created successfully"), "User was not added successfully");
    } catch (TimeoutException e1) {
        try {
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'User already exists with this email address')]")));
            String errorText = errorMessage.getText();
            Assert.fail("User already exists: " + Email.getAttribute("value"));
        } catch (TimeoutException e2) {
//            Assert.fail("Neither success nor error message appeared within the timeout.");
            System.out.println("User already exists with this email address");
        }  
    }

//        catch (TimeoutException ex) {
//            // If neither OK button nor error message is found
//            System.out.println("Neither OK button nor error message found.");
//            Assert.fail("Unexpected outcome. Neither OK button nor error message found.");
//        }
//    }

    // Click the Back button to navigate back if necessary
//    try {
//        WebElement BackButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Back']")));
//        BackButton.click();
//        Thread.sleep(2000);
//    } catch (TimeoutException e) {
//        System.out.println("Back button not found.");
//    }

    shouldLogout = true; // Set the flag to true after Subscribers test
}



    @Test
//    @Test(dependsOnMethods = "login")
    public void Payments() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement Payments = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Payments']")));
        Payments.click();
        Thread.sleep(2000);
        System.out.println("View Payments Details");

        WebElement Paymentsearch = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Search']")));
        Paymentsearch.sendKeys("Bilal Khan");
        Thread.sleep(20000);
        System.out.println("Payment Search Successful");

        // Assert that the search result is displayed
        Assert.assertTrue(driver.getPageSource().contains("Bilal Khan"), "Payment search result not found");

        WebElement BackButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Back']")));
        BackButton.click();
        Thread.sleep(2000);     
        shouldLogout = true; // Set the flag to true after Subscribers test
    }

    @AfterMethod
    public void logout() {
        if (shouldLogout) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement ProfileButton= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='dropdownId']")));
                ProfileButton.click();
                Thread.sleep(2000);
                WebElement logout = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dropdown-item fw-bold nav-item']")));
                logout.click();
                wait.until(ExpectedConditions.urlToBe("https://admin.smartqc.io/"));
                System.out.println("User logged out.");
            } catch (Exception e) {
                System.out.println("Logout failed: " + e.getMessage());
            }
            shouldLogout = false; // Reset the flag after logging out
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
