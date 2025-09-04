package pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions

class DetailsPage extends BasePage{

    DetailsPage(WebDriver driver) {
        super(driver)
    }

    private By iframe = By.xpath("//div[@id='payment-element']//iframe")
    private By payments = By.id("payments-header-name")

    void validateDetailsPageDisplayed () {
        By headerName = By.id("already-with-amaysim-header-name")
        waitForElementVisible(headerName)
        findElementByText("already with amaysim?")
    }

    void fillInForm (String fName, String lName, String dOB, String password, String address) {
        // ABOUT YOU
        type(By.name("firstName"),fName)
        type(By.name("lastName"),lName)
        type(By.name("dateOfBirth"),dOB)

        def email = generateRandomEmail()
        println "Generated test email: ${email}"
        type(By.name("email"), email)

        def phone = generateRandomContactNumber()
        println "Generated test contact number: ${phone}"
        type(By.name("contactNumber"), phone)

        type(By.name("password"),password)
        type(By.xpath("//input[@placeholder='e.g 123 George St Sydney NSW 2000']"),address)
        findByLocator("id","react-autowhatever-1--item-0").click()
    }

    void choosePaymentMethod (String paymentMethod, String ccNumber, String exp, String cvc) {
        // SCROLL DOWN TO PAYMENTS
        waitForElementVisible(payments)
        scrollToElement(payments)
        Thread.sleep(3000)

        switchToIframe(iframe)

        switch (paymentMethod) {
            case "Card":
                // CREDIT CARD PAYMENT
                By cardButton = By.xpath("//button[@data-testid='card' and @id='card-tab']")
                Thread.sleep(3000)
                clickElement(cardButton)

                try {
                    findByLocator("name","number")
                } catch (Exception e) {
                    clickElement(cardButton)
                }

                type(By.name("number"), ccNumber)
                type(By.name("expiry"), exp)
                type(By.name("cvc"), cvc)

                break
            case "PayPal":
                findElementByText("PayPal").click()
                /** SAMPLE ONLY **/
                break
            case "GPay":
                findElementByText("GPay").click()
                /** SAMPLE ONLY **/
                break
            default:
                throw new IllegalArgumentException("${paymentMethod} is NOT existing..")
        }
        switchToDefaultContent()
    }

    void confirmPayment () {
        // TICK TERMS AND CONDITION
        clickElement (By.xpath("//a[@href='https://www.amaysim.com.au/dms/amaysim/documents/terms-conditions/amaysim-General-Terms.pdf']/preceding::div[@class='css-1417z9a'][1]"))
        // CLICK PAY NOW
        findElementByText("pay now").click()
    }

    void paymentErrorMessage () {
        Thread.sleep(3000)

        // VALIDATE ERROR MESSAGE
        try {
            findElementByTextContains("Credit Card payment failed")
        } catch (Exception e) {
            scrollToElement(payments)
        }

        findElementByTextContains("Credit Card payment failed")
        findElementByTextContains("Your attempt to pay via Credit Card has failed. Please ensure you have enough funds and try again or use another credit card.")
    }
}