package utils

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class DriverManager {
    private static WebDriver driver

    static synchronized WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup()
            ChromeOptions options = new ChromeOptions()
            // options.addArguments("--headless=new") // if needed
            options.addArguments("--start-maximized")
            options.addArguments("--disable-notifications")
            driver = new ChromeDriver(options)
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