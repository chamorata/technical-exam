package utils

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

class DriverManager {
    private static WebDriver driver

    static synchronized WebDriver getDriver() {
        if (driver == null) {
            def browser = System.getProperty('browser') ?: 'chrome'
            boolean isCI = System.getenv('CI') != null  // GitHub Actions sets CI=true

            switch(browser.toLowerCase()) {
                case 'chrome':
                    WebDriverManager.chromedriver().setup()
                    ChromeOptions chromeOptions = new ChromeOptions()
                    chromeOptions.addArguments("--start-maximized")
                    chromeOptions.addArguments("--disable-notifications")
                    if (isCI) {
                        chromeOptions.addArguments("--headless=new")
                        chromeOptions.addArguments("--no-sandbox")
                        chromeOptions.addArguments("--disable-dev-shm-usage")
                    }
                    driver = new ChromeDriver(chromeOptions)
                    break
                case 'firefox':
                    WebDriverManager.firefoxdriver().setup()
                    FirefoxOptions firefoxOptions = new FirefoxOptions()
                    if (isCI) {
                        firefoxOptions.addArguments("-headless")
                    }
                    driver = new FirefoxDriver(firefoxOptions)
                    break
                default:
                    throw new IllegalArgumentException("Unsupported browser: $browser")
            }
        }
        return driver
    }

    static synchronized void quitDriver() {
        if (driver != null) {
            try { driver.quit() } catch (Exception ignored) {}
            driver = null
        }
    }
}