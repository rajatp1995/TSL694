package keywordDriven;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;

public class OpenBrowser {

	private WebDriver driver;
	static String driverPath = "test\\resources\\drivers\\";

	public OpenBrowser(String browserType) {

		setDriver(browserType);

	}

	public WebDriver getDriver() {
		return driver;
	}

	private void setDriver(String browserType) {
		switch (browserType) {
		case "CHROME":
			System.out.println("Chrome Selected");
			driver = initChromeDriver();
			System.out.println("Chrome Executed");

			break;
		case "EDGE":
			driver = initEdgeDriver();
			break;
		case "FIREFOX":
			driver = initFirefoxDriver();
			break;
		case "IE":
			driver = initIEDriver();
			break;

		case "HEADLESS":
			driver = initHeadLessDriver();
			break;

		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver();
		}
	}

	private static WebDriver initChromeDriver() {
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private static WebDriver initEdgeDriver() {
		System.out.println("Launching edge driver with new profile..");
		System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private static WebDriver initIEDriver() {
		System.out.println("Launching edge driver with new profile..");
		System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private static WebDriver initFirefoxDriver() {
		System.out.println("Launching Firefox browser..");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private static WebDriver initHeadLessDriver() {
		System.out.println("Launching headless browser..");
		// WebDriver driver = new HtmlUnitDriver();
		// driver.manage().window().maximize();
		System.out.println("browser : headless"
				+ " is not implemented due to incorrect libs, Launching Firefox as browser of choice..");
		WebDriver driver = initFirefoxDriver();
		return driver;
	}

	public void initializeTestDriverSetup(String browserType) {
		try {
			setDriver(browserType);

		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}