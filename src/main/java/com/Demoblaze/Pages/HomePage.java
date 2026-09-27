package com.Demoblaze.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class HomePage {
    private final WebDriver driver;

    //Locators
    private final By loginMenu = By.id("login2");
    private final By usernameInput = By.id("loginusername");
    private final By passwordInput = By.id("loginpassword");
    private final By loginButton = By.xpath("//button[text()='Log in']");
    private final By userWelcomeHeader = By.id("nameofuser");
    private final By laptopsCategory = By.xpath("//a[text()='Laptops']");
    private final By cartMenu = By.id("cartur");
    private final By contactMenu = By.xpath("//a[text()='Contact']");

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public void clickLogin() {
        driver.findElement(loginMenu).click();
    }

    public void login(String username, String password){
        WaitUtils.waitForVisibility(driver,usernameInput,5).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getWelcomeMessage(){
       return WaitUtils.waitForVisibility(driver, userWelcomeHeader,5).getText();
    }

    public void  selectLaptopsCategory(){
        WaitUtils.waitForElementClickableAndClick(driver, laptopsCategory,5);
    }

    public  void selectProductByName(String productName){
        By productLink = By.xpath("//a[text()='" + productName + "']");
        WaitUtils .waitForElementClickableAndClick(driver,productLink,5);
    }

    public void goToCart(){
        WaitUtils.waitForElementClickableAndClick(driver,cartMenu,5);
    }

    public void openContactModal(){
        WaitUtils.waitForElementClickableAndClick(driver,contactMenu,5);
    }
}
