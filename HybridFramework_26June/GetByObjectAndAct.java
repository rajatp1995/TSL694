package keywordDriven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class GetByObjectAndAct {

	WebDriver driver;
	// OpenBrowser browserobj;

	public GetByObjectAndAct(WebDriver driver) {
		this.driver = driver;
	}

	public void performAction(String operation, String objectName, String objectType, String value, String var) throws Exception {
		System.out.println("performing action");
		switch (operation.toUpperCase()) {
		case "CLICK":
			// Perform click

			if (objectName.contains(".") == true) {
				objectName = objectName.split("\\.")[0];
			}
			driver.findElement(this.getByObject(objectName, objectType)).click();
			break;

		case "SETTEXT":
			// Set text on control
			System.out.println("llalalalala" + value);
			driver.findElement(this.getByObject(objectName, objectType)).sendKeys(value);
			break;

		case "GOTOURL":
			// Get url of application
			driver.get(value);
			break;

		case "GETTEXT":
			// Get text of an element
			String str = driver.findElement(this.getByObject(objectName, objectType)).getText();
			System.out.println(str);
			break;

		case "TIMEOUT":
			// Get url of application
			float sleeptime = Float.parseFloat(value);
			Thread.sleep((long) (sleeptime) * 1000);
			break;

		// for hovering
		case "HOVER":
			Actions membership = new Actions(driver);
			WebElement search = driver.findElement(this.getByObject(objectName, objectType));
			membership.moveToElement(search).build().perform();
			break;
		// for dropdowns under select tag
		case "SELECT":
			WebElement search1 = driver.findElement(this.getByObject(objectName, objectType));
			Select dropDown = new Select(search1);

			List<WebElement> lst = dropDown.getOptions();
			int size = lst.size();
			for (int i = 0; i < size; i++) {
				System.out.println(lst.get(i).getText());
			}
			if (value.contains(".") == true) {
				value = value.split("\\.")[0];
			}

			dropDown.selectByVisibleText(value);
			break;
			
		case "CLEAR":
			driver.findElement(this.getByObject(objectName, objectType)).clear();
			break;

		case "SELECT_LIST_ITEM":// selecting current id of created account

			List<WebElement> list = driver.findElements(this.getByObject(objectName, objectType));

			for (WebElement webElement : list) {
				if (webElement.getText().contains(value)) {
					System.out.println("Found it");
					webElement.click();
				}
				System.out.println(webElement.getText());
			}

			break;
		// for pressing enter key through keyboard
		case "ENTER":
			driver.findElement(this.getByObject(objectName, objectType));
			Actions spantagDropdown = new Actions(driver);
			// spantagDropdown.sendKeys(Keys.ARROW_DOWN).build().perform();

			spantagDropdown.sendKeys(Keys.ENTER).build().perform();
			break;
			
		case "ENTER_KEY":
			
			Actions act = new Actions(driver);
			act.sendKeys(Keys.ENTER).build().perform();
			break;


		case "DOWN":
			// driver.findElement(this.getByObject(objectName,objectType));
			Actions spantagDropdown2 = new Actions(driver);
			spantagDropdown2.sendKeys(Keys.ARROW_DOWN).build().perform();
			// spantagDropdown.sendKeys(Keys.ENTER).build().perform();
			break;
		// switching over to new tab in same browser
		case "SWITCHTAB":
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			/*
			 * driver.close(); driver.switchTo().window(tabs2.get(0));
			 */
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
	private By getByObject(String objectName, String objectType) throws Exception {
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