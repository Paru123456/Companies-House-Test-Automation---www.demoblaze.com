package demoblazetests;
import base.BaseTest;
import com.Demoblaze.Pages.CartPage;
import com.Demoblaze.Pages.HomePage;
import com.Demoblaze.Pages.ProductPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.TestListener;
import utils.WaitUtils;

@Listeners(TestListener.class) // Automatically tracks test execution and screenshots

public class FunctionalTests extends BaseTest {

        /**
         * TC-01: Verify successful user login with valid credentials.
         * Rationale: Authentication controls session state across the application.
         */
        @Test(priority = 1)
        public void testSuccessfulUserLogin() {
            HomePage homePage = new HomePage(driver);
            homePage.clickLogin();
            // Uses standard test account created on DemoBlaze
            homePage.login("test_user_qa123", "password123");

            String welcomeText = homePage.getWelcomeMessage();
            Assert.assertTrue(welcomeText.contains("Welcome test_user_qa123"),
                    "Login failed: Welcome header does not contain expected username.");
        }

        /**
         * TC-02: Verify adding a product ("Sony vaio i5") to the cart.
         * Rationale: Validates catalog navigation, dynamic rendering, and JS Alert notifications.
         */
        @Test(priority = 2)
        public void testAddProductToCart() {
            HomePage homePage = new HomePage(driver);
            homePage.selectLaptopsCategory();
            homePage.selectProductByName("Sony vaio i5");

            ProductPage productPage = new ProductPage(driver);
            productPage.addToCart();

            String alertMessage = productPage.getAlertTextAndAccept();
            Assert.assertEquals(alertMessage, "Product added",
                    "Alert text mismatch after adding product to cart.");

            homePage.goToCart();
            CartPage cartPage = new CartPage(driver);
            Assert.assertTrue(cartPage.isProductInCart("Sony vaio i5"),
                    "Selected item was not found in the shopping cart.");
        }

        /**
         * TC-03: Verify End-to-End order placement workflow.
         * Rationale: Core revenue-generating path (E2E workflow).
         */
        @Test(priority = 3)
        public void testCompleteOrderPlacementE2E() {
            HomePage homePage = new HomePage(driver);
            homePage.selectLaptopsCategory();
            homePage.selectProductByName("Sony vaio i5");

            ProductPage productPage = new ProductPage(driver);
            productPage.addToCart();
            productPage.getAlertTextAndAccept();

            homePage.goToCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.clickPlaceOrder();
            cartPage.fillPurchaseFrom("John Doe", "4111111111111111");
            cartPage.clickPurchase();

            Assert.assertTrue(cartPage.isPurchaseSuccessful(),
                    "Order completion failed: Confirmation modal was not displayed.");
        }

        /**
         * TC-04 [BUG DEFECT TARGET]: Verify checkout validation fails when cart is empty.
         * Rationale: Verifies business rule enforcement preventing empty orders.
         */
        @Test(priority = 4)
        public void testEmptyCartPurchaseValidation() {
            HomePage homePage = new HomePage(driver);
            homePage.goToCart();

            CartPage cartPage = new CartPage(driver);
            cartPage.clickPlaceOrder();
            cartPage.fillPurchaseFrom("Ghost User", "0000000000000000");
            cartPage.clickPurchase();

            // EXPECTED: Application should reject order.
            // ACTUAL (BUG): Order completes successfully with zero items.
            Assert.assertFalse(cartPage.isPurchaseSuccessful(),
                    "CRITICAL BUG: Application allowed placing an order with an empty cart!");
        }

        /**
         * TC-05 [BUG DEFECT TARGET]: Verify Contact Form required field validation.
         * Rationale: Assesses client-side form validation integrity on inputs.
         */
        @Test(priority = 5)
        public void testEmptyContactFormSubmissionValidation() {
            HomePage homePage = new HomePage(driver);
            homePage.openContactModal();

            // Submit form without populating mandatory fields
            WaitUtils.waitForElementClickableAndClick(driver, By.xpath("//button[text()='Send message']"), 5);

            String alertText = WaitUtils.waitForAlertAccept(driver, 5).getText();

            // EXPECTED: Warning prompt indicating missing required fields.
            // ACTUAL (BUG): Alert pops up stating "Thanks for the message!!"
            Assert.assertNotEquals(alertText, "Thanks for the message!!",
                    "BUG DETECTED: Contact message sent without mandatory input validation.");
        }
    /**
     * TC-06 (Negative Test): Verify system behavior when attempting to log in with a non-existent user.
     * Expected Behavior: System displays an alert with text "User does not exist." and accepts it.
     */
    @Test(priority = 6)
    public void testLoginWithNonExistentUser() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        // Pass credentials for a non-existent user
        homePage.login("non_existent_qa_user_999", "invalid_pass");

        // Capture and handle the browser alert
        String alertText = WaitUtils.waitForAlertAccept(driver, 5).getText();

        // Assert that the application properly displays the negative validation message
        Assert.assertEquals(alertText, "User does not exist.",
                "Unexpected alert message displayed for non-existent user login.");
    }
    }

