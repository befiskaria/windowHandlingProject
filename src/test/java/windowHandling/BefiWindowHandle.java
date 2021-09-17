//Window Handling
//Task: switching to child window from the parent window - then closes the child window and switching back to parent window
package windowHandling;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BefiWindowHandle {
WebDriver driver;
Actions action;
@BeforeMethod
public void preSetup() {
	driver=new FirefoxDriver();
	System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
	driver.get("https://www.google.com");
	driver.manage().window().maximize();
	driver.manage().deleteAllCookies();
	driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
}

@AfterMethod
public void closeBrowser() {
	driver.quit();
}

@Test
public void windowHANDLE() throws AWTException, InterruptedException {
action=new Actions(driver);
WebElement link=driver.findElement(By.xpath("//a[@href='https://mail.google.com/mail/&ogbl']"));
action.contextClick(link).perform();
Robot robot=new Robot();
Thread.sleep(1000);
robot.keyPress(KeyEvent.VK_DOWN);
Thread.sleep(1000);
robot.keyPress(KeyEvent.VK_DOWN);
Thread.sleep(1000);
robot.keyPress(KeyEvent.VK_ENTER);

//Get Window Handles
String parentWindow=driver.getWindowHandle();
Set<String> allWindows=driver.getWindowHandles();
Iterator<String> iterate=allWindows.iterator();
while(iterate.hasNext()){
String childWindow=iterate.next();
if(!parentWindow.equalsIgnoreCase(childWindow)) {
	driver.switchTo().window(childWindow);
	Thread.sleep(1000);
	String URL1=driver.getCurrentUrl();
	System.out.println("WebDriver navigated back to child window and the url is: "+URL1);
	driver.findElement(By.cssSelector("a.button:nth-child(2)")).click();
	Thread.sleep(2000);
	driver.close();
}
}
	driver.switchTo().window(parentWindow);
	Thread.sleep(1000);
	String URL2=driver.getCurrentUrl();
	System.out.println("WebDriver navigated back to parent window and the url is: "+URL2);
	
}
}
