package SetupClass;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SetClass {

	public static WebDriver driver;
	public static String AppURL;
	public static Properties property = new Properties(System.getProperties());
	public static Logger log;
	public static WebElement webelement;
	protected static WebDriverWait wait;
	protected static JavascriptExecutor js;
	public static String browser;

	public static final String UserName = "rahulsharma_8RUv0Y";

	public static final String Automate_Key = "zg8foQC6mjQGoHx9CQJE";
	public static final String URL = "https://" + UserName + ":" + Automate_Key + "@hub-cloud.browserstack.com/wd/hub";

	@BeforeClass
	public static void before_Class() throws Exception {
		log = Logger.getLogger(BeforeClass.class.getName());
		property.load(new FileReader("Config//config.properties"));
		AppURL = property.getProperty("App_url");

		System.out.println("Bname=====" + AppURL);

		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
			System.out.println("env browser = " + browser);

		} else {
			browser = property.getProperty("browser");
			System.out.println("config browser = " + browser);
		}

		property.setProperty("browser", browser);

		System.out.println("setProperty browser = " + browser);

		if ((property.getProperty("browser").equals("chrome"))) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 50);
			js = (JavascriptExecutor) driver;
			Thread.sleep(1000);
		}
		// if (browser.equalsIgnoreCase("firefox"))

		// if (browser.equalsIgnoreCase("chrome"))
		else if ((property.getProperty("browser").equals("firefox"))) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 50);
			js = (JavascriptExecutor) driver;
			Thread.sleep(1000);
		} else if ((property.getProperty("browser").equals("edge"))) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 50);
			js = (JavascriptExecutor) driver;
			Thread.sleep(1000);
		}

		else if ((property.getProperty("browser").equals("safari"))) {

			// DesiredCapabilities caps = new DesiredCapabilities();

			/*
			 * caps.setCapability("browser", browser); caps.setCapability("browser_version",
			 * "104"); caps.setCapability("os", "windows"); caps.setCapability("os_version",
			 * "10"); caps.setCapability("resolution", "1920x1200");
			 */

			MutableCapabilities capabilities = new MutableCapabilities();
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "15.0");
			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
			browserstackOptions.put("os", "OS X");
			browserstackOptions.put("osVersion", "Monterey");
			// browserstackOptions.put("resolution", "1920x1080");
			browserstackOptions.put("local", "false");
			browserstackOptions.put("seleniumVersion", "3.141.59");
			capabilities.setCapability("bstack:options", browserstackOptions);

			try {
				driver = new RemoteWebDriver(new URL(URL), capabilities);
				wait = new WebDriverWait(driver, 30);
				js = (JavascriptExecutor) driver;
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {

			System.out.println("platform does not provide");
		}

	}

	public static void chatWindow() throws Throwable {

		try {
			WebElement iframe = driver.findElement(By.xpath("//iframe[@id = 'chat-widget' or @name = 'chat-widget']"));
			Thread.sleep(1000);
			boolean value = iframe.isDisplayed();
			System.out.println("value = " + value);
			if (value == true) {
				driver.switchTo().frame(iframe);
				WebElement chat1 = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//div[@role = 'main']//button[@aria-label = 'Minimize window']")));
				Thread.sleep(1000);
				chat1.click();
				Thread.sleep(1000);
				driver.switchTo().defaultContent();
				Thread.sleep(3000);
			} else {

				System.out.println("chat window does not open");
			}
		} catch (NoSuchElementException NCP) {

		}
	}

	public static void ClearBrowserCache() throws Throwable {

		driver.manage().deleteAllCookies();
		Thread.sleep(4000); // wait 7 seconds to clear cookies.
		driver.navigate().refresh();
		Thread.sleep(3000);
	}

	@AfterClass
	public static void after_Class() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit(); // ->> don't want to close the browser for now
		Thread.sleep(2000);

	}

}
