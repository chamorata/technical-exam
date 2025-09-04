package pages

import org.openqa.selenium.WebDriver

class LandingPage extends BasePage{

    LandingPage(WebDriver driver) {
        super(driver)
    }

    void open (String url) {
        navigateTo(url)
        // VALIDATE LANDING PAGE
        findElementByText("SIM plans")
        findElementByText("Phones")
        findElementByText("Home internet")
        println "AMAYSIM LANDING PAGE IS DISPLAYED..."
    }

    void navigateToMobilePhonePlans () {
        // CLICK SIM PLANS TAB
        findElementByText("SIM plans").click()
    }
}