package tests

import org.openqa.selenium.WebDriver
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import utils.DriverManager
import utils.JsonDataReader

class BaseTest {
    protected WebDriver driver
    protected Map testData

    @BeforeMethod
    void setUp() {
        driver = DriverManager.getDriver()
        println "BaseTest: driver initialized? ${driver != null}"

        def reader = new JsonDataReader()
        testData = reader.getData()
        assert testData != null : "Test data was not loaded."
        println "BaseTest: testData.baseUrl = ${testData.baseUrl}"
    }

    @AfterMethod(alwaysRun = true)
    void tearDown() {
        DriverManager.quitDriver()
        println "BaseTest: driver quit"
    }
}