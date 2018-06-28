package softpac;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.softpac.scripts.BrowserLauncher;
import com.softpac.scripts.Driver;

public class GetByObjectAndAct {

	static WebDriver driver;
	BrowserLauncher browserobj;
	Driver excelex = new Driver();

	public GetByObjectAndAct(WebDriver driver) {
		this.driver = driver;
	}

	public void performAction(String operation, String objectName,
			String objectType, String value, String variable) throws Exception {
		System.out.println("performing action");
		switch (operation.toUpperCase()) {

		case "CLICK":
			driver.findElement(this.getByObject(objectName, objectType))
					.click();
			break;

		case "CLEAR":
			driver.findElement(this.getByObject(objectName, objectType))
					.clear();
			break;

		case "SWITCHTAB":
			ArrayList<String> tabs2 = new ArrayList<String>(
					driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(0));
			break;

		case "SETAMOUNT":
			if (value.equals("nil")) {
				System.out.println("No amount required.");
			} else {
				driver.findElement(this.getByObject(objectName, objectType))
						.clear();

				driver.findElement(this.getByObject(objectName, objectType))
						.sendKeys(value);
			}

			break;

		case "ENTER_KEY":

			Actions act = new Actions(driver);
			act.sendKeys(Keys.ENTER).build().perform();
			break;

		case "DOWN":
			Actions spantagDropdown2 = new Actions(driver);
			spantagDropdown2.sendKeys(Keys.ARROW_DOWN).build().perform();
			break;

		case "SETTEXT":
			driver.findElement(this.getByObject(objectName, objectType))
					.sendKeys(value);

			break;

		case "SELECTCHARGES":
			if (driver.findElement(
					By.xpath("//*[@id=\"charges_chosen\"]/a/div/b"))
					.isDisplayed()) {
				driver.findElement(
						By.xpath("//*[@id=\"charges_chosen\"]/a/div/b"))
						.click();
				Thread.sleep(2000);
				List<WebElement> s = driver.findElements(this.getByObject(
						objectName, objectType));

				for (WebElement webElement : s) {
					if (webElement.getText().contains(value)) {
						System.out.println("Found it");
						webElement.click();
					}

					System.out.println(webElement.getText());
				}
			}
			break;

		case "ENTERCHEQUE":
			if (driver.findElement(By.xpath("//*[@id=\"chequeno\"]"))
					.isDisplayed()) {
				driver.findElement(this.getByObject(objectName, objectType))
						.sendKeys(value);
			}

			break;

		case "SELECT1":

			List<WebElement> s = driver.findElements(this.getByObject(
					objectName, objectType));
			for (WebElement webElement : s) {
				if (webElement.getText().contains(value)) {
					System.out.println("Found it");
					webElement.click();
					break;
				}
			}
			break;

		case "GOTOURL":
			if (driver != null)
				driver.get(value);
			else
				System.out.println("driver object is null");
			break;

		case "GETTEXT":
			String str = driver.findElement(
					this.getByObject(objectName, objectType)).getText();
			System.out.println(str);
			break;

		case "MOVETO":
			Actions membership = new Actions(driver);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = driver.findElement(this.getByObject(
					objectName, objectType));
			membership.moveToElement(element).build().perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(this
					.getByObject(objectName, objectType)));
			break;

		case "TIMEOUT":
			float sleeptime = Float.parseFloat(value);
			Thread.sleep((long) (sleeptime) * 1000);
			break;

		case "OPENBROWSER":
			browserobj = new BrowserLauncher(value);
			this.driver = browserobj.getDriver();
			break;

		case "CLOSEBROWSER":
			System.out.println("attempting to close browser");
			driver.quit();
			System.out.println("browser closed");
			break;

		case "ASSERT":
			assertEquals(driver.getCurrentUrl(), value);

			break;

		case "SELECT":
			WebElement search1 = driver.findElement(this.getByObject(
					objectName, objectType));
			Select dropDown = new Select(search1);
			dropDown.selectByVisibleText(value);
			break;

		case "STORE":
			String variableName = variable;
			variableName = variableName.substring(2, variableName.length() - 1);
			excelex.getVariableMap().put(variableName, value);
			break;

		case "ECHO":

			/*
			 * 
			 * // Print the hashmap for (String name:
			 * excelex.getPeopleMap().keySet()){
			 * 
			 * String key =name.toString(); String value3 =
			 * excelex.getPeopleMap().get(name).toString();
			 * System.out.println(key + " " + value3);
			 * 
			 * }
			 */
			System.out.println(excelex.getVariableMap().get(
					variable.substring(2, variable.length() - 1)));

			break;

		case "VERIFYTITLE":

			try {
				assertEquals(driver.getTitle(), value);
			} catch (AssertionError e) {
				System.out.println("Verification Failed");
				e.getMessage();
			}
			break;

		case "VERIFYTEXT":

			try {

				assertEquals(
						driver.findElement(
								this.getByObject(objectName, objectType))
								.getText(), value);
			} catch (AssertionError e) {
				System.out.println("Verification Failed");
				e.getMessage();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * Find element BY using object type and value * @param objectName
	 * 
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	private By getByObject(String objectName, String objectType)
			throws Exception {
		// Find by xpath
		if (objectType.equalsIgnoreCase("XPATH")) {

			return By.xpath(objectName);
		}
		// find by class
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {

			return By.className(objectName);

		}
		// find by id
		else if (objectType.equalsIgnoreCase("ID")) {

			return By.id(objectName);

		}
		// find by name
		else if (objectType.equalsIgnoreCase("NAME")) {

			return By.name(objectName);

		}
		// Find by css
		else if (objectType.equalsIgnoreCase("CSS")) {

			return By.cssSelector(objectName);

		}
		// find by link
		else if (objectType.equalsIgnoreCase("LINK")) {

			return By.linkText(objectName);

		}
		// find by partial link
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {

			return By.partialLinkText(objectName);

		} else {
			throw new Exception("Wrong object type");
		}
	}
}
