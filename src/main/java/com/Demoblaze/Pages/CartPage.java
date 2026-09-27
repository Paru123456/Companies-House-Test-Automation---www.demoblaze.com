package com.Demoblaze.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class CartPage {
    private final WebDriver driver;
    private final By placeOrderButton = By.xpath("//button[text()='Place Order']");
    private final By nameInput = By.id("name");
    private final By creditCardInput = By.id("card");
    private final By purchaseButton = By.xpath("//button[text()='Purchase']");
    private final By confirmationModalHeader = By.xpath("//h2[text()='Thank you for your purchase!']");
    private final By okButton = By.xpath("//button[text()='OK']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductInCart(String productName) {
        By itemLocator = By.xpath("//td[text()='" + productName + "']");
        return WaitUtils.waitForVisibility(driver, itemLocator, 5).isDisplayed();
    }

    public void clickPlaceOrder(){
        WaitUtils.waitForElementClickableAndClick(driver,placeOrderButton,5);
    }

    public void fillPurchaseFrom(String name, String card){
        WaitUtils.waitForVisibility(driver, nameInput,5).sendKeys(name);
        driver.findElement(creditCardInput).sendKeys(card);
    }
    public void clickPurchase(){
        driver.findElement(purchaseButton).click();
    }

    public boolean isPurchaseSuccessful(){
        return WaitUtils.waitForVisibility(driver,confirmationModalHeader,5).isDisplayed();
    }

}
