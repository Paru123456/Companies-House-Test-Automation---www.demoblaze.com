package com.Demoblaze.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import utils.WaitUtils;

public class ProductPage {
    private final WebDriver driver;

    private final By addToCartButton = By.xpath("//a[text()='Add to cart']");
    public ProductPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public void addToCart()
    {
        WaitUtils.waitForElementClickableAndClick(driver, addToCartButton, 5);
    }

    public String getAlertTextAndAccept()
    {
        String alertText = WaitUtils.waitForAlertAccept(driver, 5).getText();
        driver.switchTo().alert().accept();
        return alertText;
    }
}
