package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class SimPlansPage extends BasePage{

    SimPlansPage(WebDriver driver) {
        super(driver)
    }

    void validateSimPlansPageDisplayed () {
        // VALIDATE SIM PLANS PAGE
        findElementByText("SIM plans")
        findElementByText("eSIM ready")
        findElementByText("Optus network")
        findElementByText("Unlimited data")
        findElementByText("No lock-in contract")
    }

    void buy7DaySim () {
        // SCROLL DOWN 7 DAY SIM PLAN
        By selectedPlan = By.xpath("//h2[normalize-space(text())='7 DAY SIM PLANS']")
        scrollToElement(selectedPlan)
        // CLICK BUY NOW
        findByLocator("xpath","//a[@class='btn no-icon btn-orange' and @href='/mobile/cart/7-day-10gb' and text()='Buy now']").click()
    }
}