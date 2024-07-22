package appscrip;

import org.testng.annotations.*;
import static org.testng.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.Iterator;

public class AppTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeSuite
    public void createDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void signUp() throws InterruptedException {
        driver.get("https://trulyfreehome.dev");
        Thread.sleep(2000);
        WebElement signMeUpButton = driver.findElement(By.xpath("//button[@class='jsx-46f6405b54523d64 join_btn']"));
        signMeUpButton.click();
        Thread.sleep(2000);

        WebElement dropdown = driver.findElement(By.xpath("//div[@class='selected-flag']"));
        dropdown.click();
        Thread.sleep(5000);

        WebElement India = driver.findElement(By.xpath("//li[@class='country highlight preferred']"));
        India.click();
        Thread.sleep(5000);

        WebElement phoneInput = driver.findElement(By.xpath("//input[@type='tel']"));
        phoneInput.sendKeys("7783872397");
        Thread.sleep(2000);

        WebElement termsCheckbox = driver
                .findElement(By.xpath("//div[@class='jsx-2527197127 d-flex pb-2 checkbox_border']//descendant::input"));
        termsCheckbox.click();
        Thread.sleep(2000);

        WebElement continueButton = driver.findElement(By.xpath("//button[@type='submit']"));
        continueButton.click();
        Thread.sleep(2000);

        // WebElement otpInput = driver.findElement(By.id("otp"));
        // otpInput.sendKeys("1111");

        // Locate all OTP input elements
        List<WebElement> otpInputs = driver.findElements(By.xpath("//input[@class='inputStyle ']"));

        // Send '1' to each OTP input element
        // for (WebElement ele : otpInputs) {
        // ele.sendKeys("1");
        // Thread.sleep(2000);
        // }
        for (int i = 0; i < 3; i++) {
            otpInputs.get(i).sendKeys("1");
            Thread.sleep(2000);
        }
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@aria-label='Digit 4']")).sendKeys("1");
        Thread.sleep(2000);

        // WebElement firstNameInput =
        // driver.findElement(By.xpath("//input[@name='firstName']"));
        // firstNameInput.sendKeys("Saurabh");
        // Thread.sleep(2000);

        // WebElement lastNameInput =
        // driver.findElement(By.xpath("//input[@name='lastName']"));
        // lastNameInput.sendKeys("Kumar");
        // Thread.sleep(2000);

        // WebElement emailInput =
        // driver.findElement(By.xpath("//input[@name='email']"));
        // emailInput.sendKeys("saurabhkumar993973@gmail.com");
        // Thread.sleep(2000);

        // WebElement doneButton =
        // driver.findElement(By.xpath("//span[text()='DONE']"));
        // doneButton.click();
        // Thread.sleep(2000);
    }

    @Test(priority = 2)
    public void navigateToEmaniBrand() throws InterruptedException {
        WebElement brandsMenu = driver.findElement(By.xpath("//div[text()='Brands']"));
        brandsMenu.click();
        Thread.sleep(2000);

        WebElement emaniBrandLink = driver.findElement(By.xpath("//p[text()='Emani']"));
        emaniBrandLink.click();
        Thread.sleep(2000);
    }

    @Test(priority = 3)
    public void printProductDetailsToExcel() throws IOException {
        // Locate product name and price elements
       // List<WebElement> productNames = driver.findElements(By.xpath("//span[contains(@class,'card_title')]"));
       //List<WebElement> productPrices = driver.findElements(By.xpath("//div[contains(@class,'price')]/span[contains(@class,'stroke')]"));

        List<WebElement> productNames = driver.findElements(By.xpath("//div[@class='jsx-c3ec2b6b129f087d card_content_wrapper pt-0']//child::span[@class='jsx-c3ec2b6b129f087d card_title d-inline-block text-truncate w-100']"));
        List<WebElement> productPrices = driver.findElements(By.xpath("//div[@class='jsx-c3ec2b6b129f087d price']"));

		String filePath = "C:\\Users\\saura\\OneDrive\\Desktop\\SL-Projects\\Appscrip\\app\\product_details.xlsx";
		FileInputStream fs = new FileInputStream(filePath);

        // Create an Excel workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.createSheet("Product Details");

        // Write product details to the Excel sheet
        for (int i = 0; i < productNames.size(); i++) {
            XSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(productNames.get(i).getText());
            row.createCell(1).setCellValue(productPrices.get(i).getText());
        }

        // Write the workbook to a file and close resources
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            fileOut.close();
        } finally {
            workbook.close();
        }
    }

    // @Test(priority = 3)
    // public void printProductDetailsToExcel() throws IOException {
    //     // Locate product name and price elements
    //     List<WebElement> productNames = driver.findElements(By.xpath("//div[@class='product-name']"));
    //     List<WebElement> productPrices = driver.findElements(By.xpath("//div[@class='product-price']"));


    //     // Create an Excel workbook and sheet
    //     XSSFWorkbook workbook = new XSSFWorkbook();
    //     XSSFSheet sheet = workbook.createSheet("Product Details");

    //     // Write product details to the Excel sheet
    //     for (int i = 0; i < productNames.size(); i++) {
    //         XSSFRow row = sheet.createRow(i);
    //         row.createCell(0).setCellValue(productNames.get(i).getText());
    //         row.createCell(1).setCellValue(productPrices.get(i).getText());
    //     }

    //     // Write the workbook to a file and close resources
    //     try (FileOutputStream fileOut = new FileOutputStream("product_details.xlsx")) {
    //         workbook.write(fileOut);
    //     } finally {
    //         workbook.close();
    //     }
    // }

    @Test(priority = 4)
    public void checkProductDetails() throws InterruptedException {
        WebElement firstProduct = driver.findElement(By.xpath("//img[@alt='Perfect 10 Serum']"));
        firstProduct.click();
        Thread.sleep(5000);

        WebElement productDetails = driver.findElement(By.xpath("//div[@class='jsx-1005774697 detail_section']"));
        assertTrue(productDetails.isDisplayed());

        // WebElement quantityInput = driver
        // .findElement(By.xpath("//select[@name='6582fa22c0c9e1748374f0fc-one-time-purchase']"));
        // quantityInput.click();
        // Thread.sleep(2000);
        // quantityInput.sendKeys("2");

        WebElement quantityInput = driver
                .findElement(By.xpath("//select[@name='6582fa22c0c9e1748374f0fc-one-time-purchase']"));
        Select selectQuantity = new Select(quantityInput);
        selectQuantity.selectByValue("2");
        Thread.sleep(2000);

        WebElement addToCartButton = driver.findElement(By.xpath("//button[text()='Add to Cart']"));
        addToCartButton.click();
        Thread.sleep(2000);

        WebElement cartCount = driver
                .findElement(By.xpath("//select[@name='6582fa22c0c9e1748374f0fc-one-time-purchase']"));
        assertEquals(cartCount.getText(), "2");
        Thread.sleep(2000);
    }

    @Test(priority = 5)
    public void validateCart() throws InterruptedException {
        WebElement cartIcon = driver.findElement(By.xpath("//a[contains(@href,'/cart')]//*[name()='svg']"));
        cartIcon.click();
        Thread.sleep(2000);

        WebElement productName = driver.findElement(By.xpath("//h2[text()='Perfect 10 Serum']"));
        assertTrue(productName.isDisplayed());
        Thread.sleep(2000);

        WebElement productDetails = driver.findElement(By.xpath("//div[@class='detail_wrapper col pr-0']"));
        assertTrue(productDetails.isDisplayed());
        Thread.sleep(2000);

        WebElement productPrice = driver.findElement(By.xpath("//span[@class='total_price ml-auto']"));
        assertTrue(productPrice.isDisplayed());
        Thread.sleep(2000);

        WebElement checkoutButton = driver.findElement(By.xpath("//a[@class='w-100 proceed_btn desktop_btn']"));
        checkoutButton.click();
        Thread.sleep(2000);
    }

    @Test(priority = 6)
    public void checkout() throws InterruptedException {
        WebElement newAddress = driver.findElement(By.xpath(("//span[text()='+ Add New Address']")));
        newAddress.click();
        Thread.sleep(2000);

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@placeholder='Enter your first name']"));
        firstNameInput.sendKeys("S");
        Thread.sleep(2000);

        WebElement lastNameInput = driver.findElement(By.xpath("//input[@placeholder='Enter your last name']"));
        lastNameInput.sendKeys("K");
        Thread.sleep(2000);

        WebElement phoneInput = driver.findElement(By.xpath("//input[@id='regPhoneInput']"));
        phoneInput.sendKeys("1234567890");
        Thread.sleep(2000);

        WebElement addressInput = driver.findElement(By.xpath("//input[@placeholder='Enter address']"));
        addressInput.sendKeys("24 Battery PI");
        Thread.sleep(2000);

        WebElement zipInput = driver.findElement(By.xpath("//input[@placeholder='Enter zip code']"));
        zipInput.sendKeys("10004");
        Thread.sleep(2000);

        // WebElement cityInput = driver.findElement(By.id("city"));
        // cityInput.sendKeys("New York");

        // Select stateSelect = new Select(driver.findElement(By.id("state")));
        // stateSelect.selectByVisibleText("New York");

        // Select countrySelect = new Select(driver.findElement(By.xpath(("//img[@class='greyChevronIcon']"))));
        // countrySelect.selectByVisibleText("United States Of America");

        // WebElement countrydrop=driver.findElement(By.xpath("//div[@class='select__value-container css-133shws']"));
        // countrydrop.click();

        // driver.findElement(By.xpath("//div[text()='United States of America']")).click();
        // Thread.sleep(2000);
        

        WebElement continueButton = driver.findElement(By.xpath("//button[@id='continue_btn']"));
        continueButton.click();
        Thread.sleep(2000);
    }

    @Test(priority = 7)
    public void placeOrder() throws InterruptedException {

        WebElement addanewcard = driver.findElement(By.xpath("//button[text()='+Add a new card']"));
        addanewcard.click();
        Thread.sleep(2000);

        WebElement nameOnCardInput = driver.findElement(By.xpath("//input[@id='full_name']"));
        nameOnCardInput.sendKeys("Saurabh Kumar");
        Thread.sleep(2000);

        // Switch to the iframe containing the card number input
        WebElement cardIframe = driver.findElement(By.xpath("//iframe[contains(@id, 'spreedly-number-frame')]"));
        driver.switchTo().frame(cardIframe);

        // Locate the card number input element within the iframe
        WebElement cardNumberInput = driver.findElement(By.xpath("//input[@id='card_number']"));
        cardNumberInput.sendKeys("4242 4242 4242 4242");
        Thread.sleep(2000);

        // Switch back to the default content
        driver.switchTo().defaultContent();

        // WebElement cardNumberInput = driver.findElement(By.xpath("//input[@id='card_number']"));
        // cardNumberInput.sendKeys("4242 4242 4242 4242");
        // Thread.sleep(2000);

        

        // Locate the iframe and switch to it
        WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@id, 'spreedly-cvv-frame')]"));
        driver.switchTo().frame(iframe);

        // Locate the CVV input element within the iframe
        WebElement cvvInput = driver.findElement(By.xpath("//input[@id='cvv']"));
        cvvInput.sendKeys("111");
        Thread.sleep(2000);

        // Switch back to the default content
      //  driver.switchTo().defaultContent();

        // WebElement cvvInput = driver.findElement(By.xpath("//input[@id='cvv']"));
        // cvvInput.sendKeys("111");
        // Thread.sleep(2000);

       // Switch back to the default content
       driver.switchTo().defaultContent();

       // Locate the expiration month and year input elements directly
       WebElement expMonthInput = driver.findElement(By.xpath("//input[@id='month']"));
       WebElement expYearInput = driver.findElement(By.xpath("//input[@id='year']"));
       expMonthInput.sendKeys("05");
       expYearInput.sendKeys("2026");
       Thread.sleep(2000);

        WebElement cardAddButton = driver.findElement(By.xpath("//button[@id='sumbitbutton']"));
        cardAddButton.click();
        Thread.sleep(2000);

        WebElement confirmOrderButton = driver.findElement(By.xpath("//button[text()='Confirm Order & Checkout']"));
        confirmOrderButton.click();
        Thread.sleep(5000);


        // WebElement closeButton = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paperScrollPaper')]//svg[@data-testid='CloseIcon']"));
        // // Click on the cross (close) button
        // closeButton.click();
        // Thread.sleep(2000);

        Thread.sleep(10000); // Wait to be redirected to order page
        // Locate the order ID element using an appropriate selector


        WebElement orderIdElement = driver.findElement(By.xpath("//span[contains(@class, 'orderNum')]"));
        String orderId = orderIdElement.getText();
        System.out.println("Order ID: " + orderId);
        Thread.sleep(2000);
    }

    @Test(priority = 8)
    public void logout() throws InterruptedException {
    WebElement profileIcon = driver.findElement(By.xpath("//span[text()='Saurabh Kumar']"));
    profileIcon.click();
    Thread.sleep(2000);

    WebElement logoutLink = driver.findElement(By.xpath("//p[text()='Logout']"));
    logoutLink.click();
    Thread.sleep(2000);
    }

    @AfterSuite
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
