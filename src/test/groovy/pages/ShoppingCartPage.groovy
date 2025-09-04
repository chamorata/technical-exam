package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class ShoppingCartPage extends BasePage{

    ShoppingCartPage(WebDriver driver) {
        super(driver)
    }

    void validateCartPageDisplayed () {
        By headerName = By.xpath("//h3[@id='product-header-name-desktop' and text()='UNLIMITED 10GB']")
        waitForElementVisible(headerName)
        findElementByTextContains("UNLIMITED 10GB")
        findElementByTextContains("\$10/7 days")
    }

    void selectYourOrder (String selectYourNumber, String simType) {
        // YOUR MOBILE NUMBER
        findElementByText(selectYourNumber).click()
        Thread.sleep(3000)
        String newMobileNumber = findByLocator("xpath","//span[@data-new-number]").getText()
        println "New Mobile Number: " +newMobileNumber
        // TYPE OF SIM
        isSimTypeSelected(simType)
        // VALIDATE YOUR ORDER
        Map<String, String> expectedCartValues = [
                planName            : "UNLIMITED 10GB",
                planPrice           : "\$10.00",
                numberType          : "New number",
                numberPrice         : "Free",
                selectedNumber      : newMobileNumber,
                deliveryType        : "Standard delivery",
                deliveryPrice       : "Free",
                deliveryDescription : "Delivery in 2-10 business days",
                total               : "\$10.00"
        ]
        validateCartSummary(expectedCartValues)
        // CLICK CHECKOUT
        findElementByText("checkout").click()
    }

    boolean isSimTypeSelected(String value) {
        WebElement simTypeRadio = findByLocator("xpath", "//input[@name='simType' and @value='${value}']")
        boolean selected = simTypeRadio.isSelected()
        println "SIM type '${value}' is selected: ${selected}"
        return selected
    }

    void validateCartSummary(Map<String, String> expectedValues) {
        expectedValues.each { key, expectedValue ->
            String xpath = ""

            switch (key) {
                case "planName":
                    xpath = "//strong[contains(@class, 'css-176y7cu') and text()='${expectedValue}']"
                    break
                case "planPrice":
                    xpath = "//span[@data-testid='cart-summary-product-price']/strong"
                    break
                case "numberType":
                    xpath = "//span[@data-testid='cart-summary-number-type']/strong"
                    break
                case "numberPrice":
                    xpath = "//span[@data-testid='cart-summary-number-price']/strong"
                    break
                case "selectedNumber":
                    xpath = "//span[@data-testid='cart-summary-selected-number']"
                    break
                case "deliveryType":
                    xpath = "//strong[text()='Standard delivery']"
                    break
                case "deliveryPrice":
                    xpath = "//span[@data-testid='cart-summary-delivery-price']/strong"
                    break
                case "deliveryDescription":
                    xpath = "//span[@data-testid='cart-summary-delivery-description']"
                    break
                case "total":
                    xpath = "//span[@data-testid='cart-summary-total-price']"
                    break
                default:
                    throw new IllegalArgumentException("No locator defined for key: ${key}")
            }

            WebElement element = findByLocator("xpath", xpath)
            String actualValue = element.getText().trim()

            assert actualValue == expectedValue :
                    "Validation failed for ${key}. Expected: '${expectedValue}', but got: '${actualValue}'"
            println "${key} validated: ${actualValue}"
        }
    }
}