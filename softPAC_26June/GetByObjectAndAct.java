package softPAC;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import mx4j.tools.config.DefaultConfigurationBuilder.New;

public class GetByObjectAndAct {

	WebDriver driver;
	OpenBrowser browserobj;

	public GetByObjectAndAct(WebDriver driver) {
		this.driver = driver;
	}

	public void performAction(String operation, String objectName, String objectType, String value, String var) throws Exception {
		System.out.println("performing action");
		switch (operation.toUpperCase()) {

		case "CLICK":
			// Perform click
			driver.findElement(this.getByObject(objectName, objectType)).click();
			break;
		case "SETTEXT":
			// Set text on control
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
		case "OPENBROWSER":
			browserobj = new OpenBrowser(value);
			// Thread.sleep(5000);
			this.driver = browserobj.getDriver();
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
