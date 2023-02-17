package stepDefination;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import SetupClass.SetClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SignUpLoop extends SetClass {

	@Given("Enter the url")
	public void enter_the_url() throws InterruptedException {
		driver.get(AppURL);
		try {
			ClearBrowserCache();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Then("verify signup process in a loop")
	public void verify_signup_process_in_a_loop() throws Throwable {

		for (int j = 1; j <= 2; j++) {

			Thread.sleep(3000);
			try {
				driver.findElement(By.cssSelector("ul.header > li:nth-child(1) > a:nth-child(1)")).click();
				Thread.sleep(2000);
				log.info("It's opening the website URL and redirect user to sign up page");
			} catch (NoSuchElementException popup) {
			}

			int leftLimit = 97; // letter 'a'
			int rightLimit = 122; // letter 'z'
			int targetStringLength = 10;
			Random random = new Random();
			StringBuilder buffer = new StringBuilder(targetStringLength);
			for (int i = 0; i < targetStringLength; i++) {
				int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
				buffer.append((char) randomLimitedInt);
			}
			String generatedString = buffer.toString();

			System.out.println(generatedString);

			String full_email = "selenium.testing." + generatedString + "@gmail.com";
			System.out.println(full_email);

			Thread.sleep(2000);
			WebElement new_email_signup = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email_address']")));
			Thread.sleep(2000);
			new_email_signup.sendKeys(full_email);
			Thread.sleep(2000);

			WebElement new_fname_signup = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='firstname']")));
			Thread.sleep(2000);
			new_fname_signup.sendKeys("Selenium");
			Thread.sleep(2000);

			WebElement new_lname_signup = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='lastname']")));
			Thread.sleep(2000);
			new_lname_signup.sendKeys("Testing");
			Thread.sleep(2000);

			WebElement new_pwd_signup = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='password']")));
			Thread.sleep(2000);

			new_pwd_signup.sendKeys("selenium@123");
			Thread.sleep(2000);

			WebElement new_pwd1_signup = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='password-confirmation']")));
			Thread.sleep(2000);
			new_pwd1_signup.sendKeys("selenium@123");
			Thread.sleep(2000);

			WebElement new_btn_signup = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Sign Up']")));
			Thread.sleep(2000);
			new_btn_signup.click();
			Thread.sleep(3000);

			Thread.sleep(2000);

			// verify pricing page and select a plan

			String expected_URL = driver.getCurrentUrl();
			String actual_URL = "https://www.slideteam.net/pricing";

			Assert.assertEquals("user was not on the pricing page", expected_URL, actual_URL);
			
			js.executeScript("window.scrollBy(0,1000)");
			WebElement join_now_btn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
					"#Individual > div > div.slide-product > div:nth-child(2) > div.col.col2.col-bg2 > span > form > button > span")));
			Thread.sleep(2000);
			js.executeScript("arguments[0].scrollIntoView();", join_now_btn);
			Thread.sleep(3300);
			join_now_btn.click();
			Thread.sleep(4000);

			// verify that both stripe and paypal options are visible or not?
			boolean co_btn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='stripe_payments_checkout']")))
					.isDisplayed();
			Assert.assertTrue(co_btn);

			boolean paypalOption = wait
					.until(ExpectedConditions
							.elementToBeClickable(By.cssSelector(".payment-method-title >#paypal_express")))
					.isDisplayed();
			Assert.assertTrue(paypalOption);
			Thread.sleep(2000);

			WebElement account = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'My Account')]")));
			account.click();
			Thread.sleep(3000);

			chatWindow();
			WebElement delete_account = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@id, 'clicking')]/self::a")));
			Thread.sleep(3000); //
			js.executeScript("arguments[0].click();", delete_account);

			WebElement delete_reason = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#exampleRadios1")));
			Thread.sleep(3000);
			js.executeScript("arguments[0].click();", delete_reason);
			Thread.sleep(3000);

			WebElement delete_profile = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button#delete-final")));
			js.executeScript("arguments[0].click();", delete_profile);
			Thread.sleep(3000);
			chatWindow();

			WebElement delete_profile_coupon = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[@class = 'btn btn-default button_2']")));
			delete_profile_coupon.click();
			Thread.sleep(3000);

			String verifyDeleteAccount = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@x-html='message.text']")))
					.getText();
			Thread.sleep(3000);
			Assert.assertTrue("Account is not deleted",
					verifyDeleteAccount.contains("Your account has been deleted successfully."));
			System.out.println("your account delete successfully");
		}

	}
}
