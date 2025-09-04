package tests

import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import pages.DetailsPage
import pages.LandingPage
import pages.ShoppingCartPage
import pages.SimPlansPage

class MobilePlanErrorTest extends BaseTest{

    private LandingPage landingPage
    private SimPlansPage simPlansPage
    private ShoppingCartPage cartPage
    private DetailsPage detailsPage

    @BeforeMethod
    void initPageObjects() {
        landingPage = new LandingPage(driver)
        simPlansPage = new SimPlansPage(driver)
        cartPage = new ShoppingCartPage(driver)
        detailsPage = new DetailsPage(driver)
    }

    @Test
    void validateMobilePlanErrorMessage() {
        // OPEN AMAYSIM URL
        landingPage.open(testData.baseUrl)
        // NAVIGATE TO SIM PLANS
        landingPage.navigateToMobilePhonePlans()

        // SIM PLANS PAGE
        simPlansPage.validateSimPlansPageDisplayed()
        simPlansPage.buy7DaySim()

        // CART PAGE
        cartPage.validateCartPageDisplayed()
        cartPage.selectYourOrder(testData.selectYourNumber, testData.simType)

        // DETAILS PAGE
        detailsPage.validateDetailsPageDisplayed()
        detailsPage.fillInForm(testData.firstName, testData.lastName, testData.dateOfBirth, testData.password, testData.address)
        detailsPage.choosePaymentMethod(testData.paymentMethod, testData.ccNumber, testData.exp, testData.cvc)
        detailsPage.confirmPayment()

        // ERROR MESSAGE
        detailsPage.paymentErrorMessage()
    }
}