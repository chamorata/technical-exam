package pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import org.openqa.selenium.support.ui.ExpectedConditions

class BasePage {
    protected WebDriver driver
    private WebDriverWait wait

    BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver must not be null when constructing page object")
        }
        this.driver = driver
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10))
    }

    //------------------ COMMON METHODS ------------------//
    WebElement find(By locator) {
        println "Finding element with locator: ${locator}"
        return driver.findElement(locator)
    }

    WebElement findByLocator(String type, String value) {
        if (!type || !value) {
            throw new IllegalArgumentException("Locator type and value must not be null/empty")
        }

        By locator
        switch (type.toLowerCase()) {
            case "id":
                locator = By.id(value)
                break
            case "name":
                locator = By.name(value)
                break
            case "xpath":
                locator = By.xpath(value)
                break
            case "css":
            case "cssselector":
                locator = By.cssSelector(value)
                break
            case "class":
            case "classname":
                locator = By.className(value)
                break
            case "tag":
            case "tagname":
                locator = By.tagName(value)
                break
            case "linktext":
                locator = By.linkText(value)
                break
            case "partiallinktext":
                locator = By.partialLinkText(value)
                break
            default:
                throw new IllegalArgumentException("Unsupported locator type: ${type}")
        }

        println "Waiting for element to be clickable: ${locator}"
        return wait.until(ExpectedConditions.elementToBeClickable(locator))
    }

    void clickElement(By locator) {
        try {
            println "Waiting for element to be clickable: ${locator}"
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator))
            element.click()
            println "Clicked on element: ${locator}"
        } catch (Exception e) {
            println "Standard click failed, trying JS click: ${locator}"
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator))
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element)
        }
    }

    void type(By locator, String text) {
        WebElement element = find(locator)
        println "Typing '${text}' into: ${locator}"
        element.clear()
        element.sendKeys(text)
    }

    void navigateTo(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL must not be null/empty")
        }
        println "Navigating to: ${url}"
        driver.get(url)
    }

    //------------------ FIND ELEMENT BY TEXT ------------------//
    WebElement findElementByText(String text) {
        println "Looking for element with text: '${text}'"
        By locator = By.xpath("//*[normalize-space(text())='${text}']")
        WebElement element = driver.findElement(locator)
        println "Found element: ${element}"
        return element
    }

    WebElement findElementByTextContains(String partialText) {
        println "Looking for element containing text: '${partialText}'"
        By locator = By.xpath("//*[contains(normalize-space(text()), '${partialText}')]")
        WebElement element = driver.findElement(locator)
        println "Found element: ${element}"
        return element
    }

    //------------------ SCROLL METHOD ------------------//
    void scrollToElement(By locator) {
        WebElement element = find(locator)
        println "Scrolling into view: ${locator}"
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element)
    }

    //------------------ WAITS ------------------//
    void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
    }

    //------------------ GENERATE EMAIL ------------------//
    String generateRandomEmail() {
        def alphabet = "abcdefghijklmnopqrstuvwxyz"
        def random = new Random()
        // RANDOM USERNAME WITH 8 CHARACTERS
        def username = (1..8).collect { alphabet[random.nextInt(alphabet.length())] }.join()
        // RANDOM NUMBER FOR EXTRA UNIQUENESS
        def randomNumber = random.nextInt(10000)
        // RETURN FULL EMAIL
        return "${username}${randomNumber}@example.com"
    }

    //------------------ GENERATE CONTACT NUMBER ------------------//
    String generateRandomContactNumber() {
        def random = new Random()
        // START WITH "04"
        def prefix = "04"
        // GENERATE 8 RANDOM DIGITS
        def digits = (1..8).collect { random.nextInt(10) }.join()
        return prefix + digits
    }

    //------------------ IFRAME ------------------//
    // SWITCH TO IFRAME BY LOCATOR
    void switchToIframe(By locator) {
        println "Switching to iframe: ${locator}"
        WebElement iframe = wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
        driver.switchTo().frame(iframe)
        println "Switched to iframe"
    }

    // SWITCH BACK TO THE MAIN PAGE
    void switchToDefaultContent() {
        driver.switchTo().defaultContent()
        println "Switched back to default content"
    }
}