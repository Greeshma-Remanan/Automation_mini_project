package mini_project;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Mini_project {
WebDriver driver;
	
	@BeforeTest
	@Parameters("browser")
	public void browser_setup(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		else {
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.testapiqa.com/");
	}
	
	@BeforeMethod
	public void Bofore_method() {
		driver.manage().window().maximize();
	}
	
	@Test(priority = 0)
	public void Test1_Login() throws InterruptedException {
		WebElement login = driver.findElement(By.xpath("//a[text()=' Signup / Login'][1]"));
		login.click();
		
	    Thread.sleep(1000);	
	    
	    //email
		WebElement email = driver.findElement(By.xpath("//input[@name='email']"));
		email.sendKeys("saileja@gmail.com");
		
		//pass
		WebElement pass = driver.findElement(By.xpath("//input[@name='password']"));
		pass.sendKeys("saileja123");
		
		pass.sendKeys(Keys.ENTER);
		
	}
	@Test(priority = 1)
	public void Test2_Product() throws InterruptedException {
		Thread.sleep(1000);
		
		//men category
		WebElement men = driver.findElement(By.xpath("//span[text()='Tshirts']"));
		men.click();
		
		//search-jeans
		Thread.sleep(1000);
		
		WebElement search = driver.findElement(By.xpath("//input[@placeholder='Search for products...']"));
		search.sendKeys("jeans");
		search.sendKeys(Keys.ENTER);
		
		Thread.sleep(1000);
		
		//filter option
		WebElement filter = driver.findElement(By.xpath("//select[@class='sort-select']"));
		Select s2 = new Select(filter);
		s2.selectByVisibleText("Price: Low to High");
		
		//select size option
		WebElement jeans = driver.findElement(By.xpath("//a[@href='/product/9'][1]"));
		Actions act = new Actions(driver);
		act.moveToElement(jeans);
		act.build().perform();
		WebElement s_size = driver.findElement(By.xpath("//a[text()=' Select Size'][1]"));
		s_size.click();
		
	}
	
	@Test(priority = 2)
	public void Test3_Size() throws InterruptedException {
		Thread.sleep(1000);
		
		//choose size
		WebElement size = driver.findElement(By.xpath("//button[text()='32']"));
		size.click();
		
		Thread.sleep(1000);
		//qty increase
		WebElement qty = driver.findElement(By.xpath("//button[text()='+']"));
		qty.click();
		
		//add to cart
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement addcart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add to Cart']")));
		addcart.sendKeys(Keys.ENTER);
		
		Thread.sleep(1000);
		
		//cart log click
		WebElement cart_logo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Cart']")));
		cart_logo.click();
		
	}
	@Test(priority = 3)
	public void Test4_Checkout() throws InterruptedException {
		Thread.sleep(1000);
		
		//qty decrease
		WebElement qty_decr=driver.findElement(By.xpath("//button[text()='−']"));
		qty_decr.click();
		
		Thread.sleep(1000);
		
		//checkout
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement checkout = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='🛍️ Proceed to Checkout']")));
		checkout.click();
		
		//address form check-place order
		WebElement place_order = driver.findElement(By.xpath("//button[text()=' Place Order']"));
		place_order.click();
		
		Thread.sleep(2000);
		
	}
	
	@Test(priority = 4)
	public void Test5_Screenshot() throws IOException, InterruptedException {
		Thread.sleep(1000);
		File img = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(img,new File(".//Screenshot//MyJeansOrderpage.png"));
	}
	
	@Test(priority = 5)
	public void Test6_Delete() throws InterruptedException {
		Thread.sleep(1000);
		//clcik delete
		WebElement dlt = driver.findElement(By.xpath("(//button[@title='Delete Order'])[2]"));
		dlt.click();
		Thread.sleep(1000);
		//confirm delete
		WebElement ys= driver.findElement(By.xpath("//button[text()='Yes, Delete']"));
		ys.click();
	}
	
	@Test(priority = 6)
	public void Test7_SoftAssertion() {
		String url = driver.getCurrentUrl();
		String exp_url ="https://www.testapiqa.com/orders";
		SoftAssert s = new SoftAssert();
		s.assertEquals(url, exp_url);
		
		s.assertAll();
		
	}
	
	@AfterTest
	public void end() {
		driver.quit();
	}

}
