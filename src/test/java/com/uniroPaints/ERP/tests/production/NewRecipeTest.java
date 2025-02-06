package com.uniroPaints.ERP.tests.production;

import com.uniroPaints.ERP.tests.auth.LoginTest;
import com.uniroPaints.ERP.tests.base.BaseTest;
import com.uniroPaints.ERP.utils.PropertyFileReader;
import com.uniroPaints.ERP.utils.TestApp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class NewRecipeTest extends BaseTest {

    PropertyFileReader prop = new PropertyFileReader();
    String productionModule = prop.getProperty("modules","module.production");
    String recipeSection = prop.getProperty("navigationMenu","navigation.recipe.element");
    String newRecipePage =  prop.getProperty("recipe","recipe.card.newRecipe");
    String recipePanelTitle = prop.getProperty("recipe","recipe.panel.title");
    String newRecipePageTitle = prop.getProperty("newRecipe","newRecipe.page.title");
    String recipeProductDropdown = prop.getProperty("newRecipe","newRecipe.dropdown.recipeProduct" );
    String recipeProduct1 = prop.getProperty("newRecipe","dropdown.recipeProduct.1");
    String recipeProduct2 = prop.getProperty("newRecipe","dropdown.recipeProduct.2");
    String recipeProduct3 = prop.getProperty("newRecipe","dropdown.recipeProduct.3");
    String recipeProduct4 = prop.getProperty("newRecipe","dropdown.recipeProduct.4");

    String inputVersion = prop.getProperty("newRecipe", "newRecipe.input.version");
    String inputQuantity = prop.getProperty("newRecipe","newRecipe.input.quantity");
    String QuantityValidationError1 = prop.getProperty("newRecipe","newRecipe.quantity.error1");
    String QuantityValidationError2 = prop.getProperty("newRecipe","newRecipe.quantity.error2");

    String inputDensity = prop.getProperty("newRecipe","newRecipe.input.density");
    String densityValidationError1 = prop.getProperty("newRecipe","newRecipe.density.error1");
    String densityValidationError2 = prop.getProperty("newRecipe","newRecipe.density.error2");

    String inputVolumeVariance = prop.getProperty("newRecipe","newRecipe.input.volumeVariance");
    String volumeVarianceValidationError1 = prop.getProperty("newRecipe","newRecipe.volumeVariance.error1");
    String volumeVarianceValidationError2 = prop.getProperty("newRecipe","newRecipe.volumeVariance.error2");

    String inputGoodIssueVariance = prop.getProperty("newRecipe","newRecipe.input.goodIssueVariance");
    String goodIssueVarianceValidationError1 = prop.getProperty("newRecipe","newRecipe.goodIssueVariance.error1");
    String goodIssueVarianceValidationError2 = prop.getProperty("newRecipe","newRecipe.goodIssueVariance.error2");

    String mainStepDropdown = prop.getProperty("newRecipe","newRecipe.dropdown.mainStepNumber");
    String mainStep1 = prop.getProperty("newRecipe","dropdown.mainStepNumber.1");
    String mainStep2 = prop.getProperty("newRecipe","dropdown.mainStepNumber.2");
    String mainStep3 = prop.getProperty("newRecipe","dropdown.mainStepNumber.3");
    String mainStep4 = prop.getProperty("newRecipe","dropdown.mainStepNumber.4");

    String mainStepDescription = prop.getProperty("newRecipe","newRecipe.input.mainStepDescription");
    String mainStepDescriptionValidationError = prop.getProperty("newRecipe","newRecipe.mainStepNumberDesc.error1");

    String subStepDropdown = prop.getProperty("newRecipe","newRecipe.dropdown.subStep");
    String subStep1 = prop.getProperty("newRecipe","dropdown.subStepNumber.1");
    String subStep2 = prop.getProperty("newRecipe","dropdown.subStepNumber.2");
    String subStep3 = prop.getProperty("newRecipe","dropdown.subStepNumber.3");

    String recipeStepClearBtn = prop.getProperty("newRecipe","newRecipe.recipeSteps.clearBtn");

    String rawMaterialDropdown = prop.getProperty("newRecipe","newRecipe.recipeSteps.dropdown.rawMaterials");
    String rawMaterial3 = prop.getProperty("newRecipe","dropdown.rawMaterial.3");

    String inputRawMaterialQuantity = prop.getProperty("newRecipe","newRecipe.recipeSteps.input.Quantity");
    String rawMaterialQuantityValidationError = prop.getProperty("newRecipe","recipeSteps.quantity.error1");

    String subStepDescription = prop.getProperty("newRecipe","newRecipe.input.subStepDescription");
    String subStepDescriptionValidationError = prop.getProperty("newRecipe","newRecipe.subStepNumberDesc.error");





    @Test(priority = 2)
    public void testNewRecipePageLoad() {
        clickElement(productionModule);
        clickElement(recipeSection);
        waitUntilNextElement(By.xpath(recipePanelTitle), 10);
        clickElement(newRecipePage);
        waitUntilNextElement(By.xpath(newRecipePageTitle), 10);

    }

    @Test(priority = 3, description = "Verify the dropdown displays all available recipe products & select the specific product.")
    public void testRecipeProductDropdown() {
        clickElement(recipeProductDropdown);
        WebElement productDropdown = driver.findElement(By.xpath(recipeProductDropdown));
        List<WebElement> options = productDropdown.findElements(By.xpath("//li[@role='option']"));
        assert options.size() > 1 : "Recipe product dropdown does not contain any products.";
        int optionCount = options.size();
        System.out.println("Number of options in the dropdown: " + optionCount);

        for (WebElement option : options) {
            System.out.println(option.getText());
        }

        clickElement(recipeProduct1);

        String selectedValue = productDropdown.getAttribute("aria-label");
        Assert.assertEquals(selectedValue, "DURAFLEX ACRYLIC MATTE BTH-006", "Selected value is not correct.");

        System.out.println("Selected dropdown option is: "+selectedValue);
    }

    @Test(priority = 4, description = "Verify the field is read-only")
    public void testVersionFieldReadOnly() {
        WebElement versionField = driver.findElement(By.xpath(inputVersion));
        assert versionField.getAttribute("readonly") != null : "Version field is not read-only.";
    }


//--------------------------Qunatity (kg) field-------------------------//

    @Test(priority = 5, description = "Verify the field accepts valid numeric values.")
    public void testQuantityFieldValidInput() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity),10);
        quantityField.sendKeys("1000.000");
        assert Objects.equals(quantityField.getAttribute("value"), "1000.000") : "Valid quantity value not accepted.";
    }

    @Test(priority = 6, description = "Verify the minimum boundary value (0.001) is accepted")
    public void testQuantityFieldMinBoundary() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("0.001");
        assert Objects.equals(quantityField.getAttribute("value"), "0.001") : "Minimum valid quantity value not accepted.";
    }

    @Test(priority = 7, description = "Verify the maximum boundary value (10000000.000) is accepted")
    public void testQuantityFieldMaxBoundary() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("10000000.000");
        assert Objects.equals(quantityField.getAttribute("value"), "10000000.000") : "Maximum valid quantity value not accepted.";
    }

    @Test(priority = 8, description = "Verify an input less than the minimum boundary (0.000) displays an error message")
    public void testQuantityFieldBelowMin() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("0.000");

        WebElement errorMessage = driver.findElement(By.xpath(QuantityValidationError1));
        assert errorMessage.isDisplayed() : "Error message for below minimum value not displayed.";
        assert errorMessage.getText().equals("Quantity (kg) must be greater than 0.001") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 9, description = "Verify an input greater than the maximum boundary (10000000.001) displays an error message")
    public void testQuantityFieldAboveMax() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("10000000.001");

        WebElement errorMessage = driver.findElement(By.xpath(QuantityValidationError2));
        assert errorMessage.isDisplayed() : "Error message for exceeding maximum value not displayed.";
        assert errorMessage.getText().equals("Quantity (kg) value cannot exceed 10000000.000") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 10, description = "Verify entering a negative value (-10.000) displays an error message")
    public void testQuantityFieldNegativeValue() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("-10.000");

        WebElement errorMessage = driver.findElement(By.xpath(QuantityValidationError1));
        assert errorMessage.isDisplayed() : "Error message for negative value not displayed.";
        assert errorMessage.getText().equals("Quantity (kg) must be greater than 0.001") : "Incorrect error message displayed.";
    }

    @Test(priority = 11, description = "Verify entering non-numeric characters is not allowed")
    public void testQuantityFieldNonNumericInput() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("abc123");

        assert !Objects.requireNonNull(quantityField.getAttribute("value")).matches(".*[a-zA-Z].*") : "Non-numeric characters should not be accepted.";
    }

    @Test(priority = 12, description = "Verify entering special characters is not allowed")
    public void testQuantityFieldSpecialCharacters() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("@#$%^&*()");

        assert Objects.requireNonNull(quantityField.getAttribute("value")).isEmpty() : "Special characters should not be accepted.";
    }

    @Test(priority = 13, description = "Verify trimming leading and trailing spaces")
    public void testQuantityFieldLeadingTrailingSpaces() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("   500.000   ");

        assert Objects.equals(quantityField.getAttribute("value"), "500.000") : "Leading or trailing spaces not trimmed correctly.";
    }

    @Test(priority = 14, description = "Verify decimal precision does not allow more than three decimal places")
    public void testQuantityFieldDecimalPrecision() {
        WebElement quantityField = driver.findElement(By.xpath(inputQuantity));
        waitUntilNextElement(By.xpath(inputQuantity), 10);
        quantityField.clear();
        quantityField.sendKeys("123.4567");

        assert Objects.equals(quantityField.getAttribute("value"), "123.456") : "More than three decimal places should not be accepted.";
    }

    //---------------------------Density field-------------------------------//

    @Test(priority = 15, description = "Verify the field accepts valid numeric values.")
    public void testDensityFieldValidInput() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity),10);
        densityField.sendKeys("0.590");
        assert Objects.equals(densityField.getAttribute("value"), "0.590") : "Valid density value not accepted.";
    }

    @Test(priority = 16, description = "Verify the minimum boundary value (0.001) is accepted")
    public void testDensityFieldMinBoundary() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("0.001");
        assert Objects.equals(densityField.getAttribute("value"), "0.001") : "Minimum valid density value not accepted.";
    }

    @Test(priority = 17, description = "Verify the maximum boundary value (10000.000) is accepted")
    public void testDensityFieldMaxBoundary() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("10000.000");
        assert Objects.equals(densityField.getAttribute("value"), "10000.000") : "Maximum valid density value not accepted.";
    }

    @Test(priority = 18, description = "Verify an input less than the minimum boundary (0.000) displays an error message")
    public void testDensityFieldBelowMin() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("0.000");

        WebElement errorMessage = driver.findElement(By.xpath(densityValidationError1));
        assert errorMessage.isDisplayed() : "Error message for below minimum value not displayed.";
        assert errorMessage.getText().equals("Density (kg/ltr) must be greater than 0.001") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 19, description = "Verify an input greater than the maximum boundary (10000.001) displays an error message")
    public void testDensityFieldAboveMax() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("10000.001");

        WebElement errorMessage = driver.findElement(By.xpath(densityValidationError2));
        assert errorMessage.isDisplayed() : "Error message for exceeding maximum value not displayed.";
        assert errorMessage.getText().equals("Density (kg/ltr) value cannot exceed 10000.000") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 20, description = "Verify entering a negative value (-10.000) displays an error message")
    public void testDensityFieldNegativeValue() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("-10.000");

        WebElement errorMessage = driver.findElement(By.xpath(densityValidationError1));
        assert errorMessage.isDisplayed() : "Error message for negative value not displayed.";
        assert errorMessage.getText().equals("Density (kg/ltr) must be greater than 0.001") : "Incorrect error message displayed.";
    }

    @Test(priority = 21, description = "Verify entering non-numeric characters is not allowed")
    public void testDensityFieldNonNumericInput() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("abc123");

        assert !Objects.requireNonNull(densityField.getAttribute("value")).matches(".*[a-zA-Z].*") : "Non-numeric characters should not be accepted.";
    }

    @Test(priority = 22, description = "Verify entering special characters is not allowed")
    public void testDensityFieldSpecialCharacters() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("@#$%^&*()");

        assert Objects.requireNonNull(densityField.getAttribute("value")).isEmpty() : "Special characters should not be accepted.";
    }

    @Test(priority = 23, description = "Verify trimming leading and trailing spaces")
    public void testDensityFieldLeadingTrailingSpaces() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("   500.000   ");

        assert Objects.equals(densityField.getAttribute("value"), "500.000") : "Leading or trailing spaces not trimmed correctly.";
    }

    @Test(priority = 24, description = "Verify decimal precision does not allow more than three decimal places")
    public void testDensityFieldDecimalPrecision() {
        WebElement densityField = driver.findElement(By.xpath(inputDensity));
        waitUntilNextElement(By.xpath(inputDensity), 10);
        densityField.clear();
        densityField.sendKeys("123.4567");

        assert Objects.equals(densityField.getAttribute("value"), "123.456") : "More than three decimal places should not be accepted.";
    }


    //--------------------------------Volume Variance Field--------------------------------//

    @Test(priority = 25, description = "Verify the field accepts valid numeric values.")
    public void testVolumeVarianceFieldValidInput() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance),10);
        volumeVarianceField.sendKeys("5.000");
        assert Objects.equals(volumeVarianceField.getAttribute("value"), "5.000") : "Valid volume variance value not accepted.";
    }

    @Test(priority = 26, description = "Verify the minimum boundary value (0.000) is accepted")
    public void testVolumeVarianceFieldMinBoundary() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance), 10);
        volumeVarianceField.clear();
        volumeVarianceField.sendKeys("0.000");
        assert Objects.equals(volumeVarianceField.getAttribute("value"), "0.000") : "Minimum valid volume variance value not accepted.";
    }

    @Test(priority = 27, description = "Verify the maximum boundary value (100.000) is accepted")
    public void testVolumeVarianceFieldMaxBoundary() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance), 10);
        volumeVarianceField.clear();
        volumeVarianceField.sendKeys("100.000");
        assert Objects.equals(volumeVarianceField.getAttribute("value"), "100.000") : "Maximum valid volume variance value not accepted.";
    }

    @Test(priority = 28, description = "Verify an input less than the minimum boundary (-1.000) displays an error message")
    public void testVolumeVarianceFieldBelowMin() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance), 10);
        volumeVarianceField.clear();
        volumeVarianceField.sendKeys("-1.000");

        WebElement errorMessage = driver.findElement(By.xpath(volumeVarianceValidationError1));
        assert errorMessage.isDisplayed() : "Error message for below minimum value not displayed.";
        assert errorMessage.getText().equals("Volume variance (%) must be at least zero.") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 29, description = "Verify an input greater than the maximum boundary (100.001) displays an error message")
    public void testVolumeVarianceFieldAboveMax() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance), 10);
        volumeVarianceField.clear();
        volumeVarianceField.sendKeys("100.001");

        WebElement errorMessage = driver.findElement(By.xpath(volumeVarianceValidationError2));
        assert errorMessage.isDisplayed() : "Error message for exceeding maximum value not displayed.";
        assert errorMessage.getText().equals("Volume variance (%) value cannot exceed 100.000") : "Incorrect validation error message displayed.";
    }


    @Test(priority = 30, description = "Verify entering non-numeric characters is not allowed")
    public void testVolumeVarianceFieldNonNumericInput() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance), 10);
        volumeVarianceField.clear();
        volumeVarianceField.sendKeys("abc12");

        assert !Objects.requireNonNull(volumeVarianceField.getAttribute("value")).matches(".*[a-zA-Z].*") : "Non-numeric characters should not be accepted.";
    }

    @Test(priority = 31, description = "Verify entering special characters is not allowed")
    public void testVolumeVarianceFieldSpecialCharacters() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance), 10);
        volumeVarianceField.clear();
        volumeVarianceField.sendKeys("@#$%^&*()");

        assert Objects.requireNonNull(volumeVarianceField.getAttribute("value")).isEmpty() : "Special characters should not be accepted.";
    }

    @Test(priority = 32, description = "Verify trimming leading and trailing spaces")
    public void testVolumeVarianceFieldLeadingTrailingSpaces() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance), 10);
        volumeVarianceField.clear();
        volumeVarianceField.sendKeys("   100.000   ");

        assert Objects.equals(volumeVarianceField.getAttribute("value"), "100.000") : "Leading or trailing spaces not trimmed correctly.";
    }

    @Test(priority = 33, description = "Verify decimal precision does not allow more than three decimal places")
    public void testVolumeVarianceFieldDecimalPrecision() {
        WebElement volumeVarianceField = driver.findElement(By.xpath(inputVolumeVariance));
        waitUntilNextElement(By.xpath(inputVolumeVariance), 10);
        volumeVarianceField.clear();
        volumeVarianceField.sendKeys("5.4567");

        assert Objects.equals(volumeVarianceField.getAttribute("value"), "5.456") : "More than three decimal places should not be accepted.";
    }

    //----------------------------------Good Issue Variance-----------------------------------//

    @Test(priority = 34, description = "Verify the field accepts valid numeric values.")
    public void testGoodIssueVarianceFieldValidInput() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance),10);
        goodIssueVarianceField.sendKeys("5.000");
        assert Objects.equals(goodIssueVarianceField.getAttribute("value"), "5.000") : "Valid good issue variance value not accepted.";
    }

    @Test(priority = 35, description = "Verify the minimum boundary value (0.000) is accepted")
    public void testGoodIssueVarianceFieldMinBoundary() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance), 10);
        goodIssueVarianceField.clear();
        goodIssueVarianceField.sendKeys("0.000");
        assert Objects.equals(goodIssueVarianceField.getAttribute("value"), "0.000") : "Minimum valid good issue variance value not accepted.";
    }

    @Test(priority = 36, description = "Verify the maximum boundary value (100.000) is accepted")
    public void testGoodIssueVarianceFieldMaxBoundary() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance), 10);
        goodIssueVarianceField.clear();
        goodIssueVarianceField.sendKeys("100.000");
        assert Objects.equals(goodIssueVarianceField.getAttribute("value"), "100.000") : "Maximum valid good issue variance value not accepted.";
    }

    @Test(priority = 37, description = "Verify an input less than the minimum boundary (-1.000) displays an error message")
    public void testGoodIssueVarianceFieldBelowMin() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance), 10);
        goodIssueVarianceField.clear();
        goodIssueVarianceField.sendKeys("-1.000");

        WebElement errorMessage = driver.findElement(By.xpath(goodIssueVarianceValidationError1));
        assert errorMessage.isDisplayed() : "Error message for below minimum value not displayed.";
        assert errorMessage.getText().equals("Good issue variance (%) must be at least zero.") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 38, description = "Verify an input greater than the maximum boundary (100.001) displays an error message")
    public void testGoodIssueVarianceFieldAboveMax() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance), 10);
        goodIssueVarianceField.clear();
        goodIssueVarianceField.sendKeys("100.001");

        WebElement errorMessage = driver.findElement(By.xpath(goodIssueVarianceValidationError2));
        assert errorMessage.isDisplayed() : "Error message for exceeding maximum value not displayed.";
        assert errorMessage.getText().equals("Good issue variance (%) value cannot exceed 100.000") : "Incorrect validation error message displayed.";
    }


    @Test(priority = 39, description = "Verify entering non-numeric characters is not allowed")
    public void testGoodIssueVarianceFieldNonNumericInput() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance), 10);
        goodIssueVarianceField.clear();
        goodIssueVarianceField.sendKeys("abc12");

        assert !Objects.requireNonNull(goodIssueVarianceField.getAttribute("value")).matches(".*[a-zA-Z].*") : "Non-numeric characters should not be accepted.";
    }

    @Test(priority = 40, description = "Verify entering special characters is not allowed")
    public void testGoodIssueVarianceFieldSpecialCharacters() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance), 10);
        goodIssueVarianceField.clear();
        goodIssueVarianceField.sendKeys("@#$%^&*()");

        assert Objects.requireNonNull(goodIssueVarianceField.getAttribute("value")).isEmpty() : "Special characters should not be accepted.";
    }

    @Test(priority = 41, description = "Verify trimming leading and trailing spaces")
    public void testGoodIssueVarianceFieldLeadingTrailingSpaces() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance), 10);
        goodIssueVarianceField.clear();
        goodIssueVarianceField.sendKeys("   100.000   ");

        assert Objects.equals(goodIssueVarianceField.getAttribute("value"), "100.000") : "Leading or trailing spaces not trimmed correctly.";
    }

    @Test(priority = 42, description = "Verify decimal precision does not allow more than three decimal places")
    public void testGoodIssueVarianceFieldDecimalPrecision() {
        WebElement goodIssueVarianceField = driver.findElement(By.xpath(inputGoodIssueVariance));
        waitUntilNextElement(By.xpath(inputGoodIssueVariance), 10);
        goodIssueVarianceField.clear();
        goodIssueVarianceField.sendKeys("5.4567");

        assert Objects.equals(goodIssueVarianceField.getAttribute("value"), "5.456") : "More than three decimal places should not be accepted.";
    }

    //------------------------------Main Step Number Dropdown----------------------------------//

    @Test(priority = 43, description = "Verify the dropdown displays all available main steps & select the specific product.")
    public void testMainStepNumberDropdown() {
        clickElement(mainStepDropdown);
        WebElement productDropdown = driver.findElement(By.xpath(mainStepDropdown));
        List<WebElement> options = productDropdown.findElements(By.xpath("//li[@role='option']"));
        assert options.size() > 1 : "Main Step dropdown does not contain any main steps.";
        int optionCount = options.size();
        System.out.println("Number of options in the dropdown: " + optionCount);

        for (WebElement option : options) {
            System.out.println(option.getText());
        }

        clickElement(mainStep1);

        String selectedValue = productDropdown.getAttribute("aria-label");
        Assert.assertEquals(selectedValue, "STEP 1", "Selected value is not correct.");

        System.out.println("Selected dropdown option is: "+selectedValue);
    }


    //------------------------------Main Step Description----------------------------------//


    @Test(priority = 44, description = "Verify the field accepts valid description values.")
    public void testMainStepDescFieldValidInput() {
        WebElement mainStepDescField = driver.findElement(By.xpath(mainStepDescription));
        waitUntilNextElement(By.xpath(mainStepDescription),10);
        mainStepDescField.sendKeys("Others who use this device won’t see your activity, so you can browse more privately.");
        assert Objects.equals(mainStepDescField.getAttribute("value"), "Others who use this device won’t see your activity, so you can browse more privately.") : "Valid main step description value not accepted.";
    }

    @Test(priority = 45, description = "Verify the minimum character limit (3) is accepted")
    public void testMainStepDescFieldMinBoundary() {
        WebElement mainStepDescField = driver.findElement(By.xpath(mainStepDescription));
        waitUntilNextElement(By.xpath(mainStepDescription), 10);
        mainStepDescField.clear();
        mainStepDescField.sendKeys("ABC");
        assert Objects.equals(mainStepDescField.getAttribute("value"), "ABC") : "Minimum valid description character limit not accepted.";
    }

    @Test(priority = 46, description = "Verify the maximum character limit (150) is accepted")
    public void testMainStepDescFieldMaxBoundary() {
        WebElement mainStepDescField = driver.findElement(By.xpath(mainStepDescription));
        waitUntilNextElement(By.xpath(mainStepDescription), 10);
        mainStepDescField.clear();
        mainStepDescField.sendKeys("Others who use this device won’t see your activity, so you can browse more privately. This won't change how data is collected by websites you visited.");
        assert Objects.equals(mainStepDescField.getAttribute("value"), "Others who use this device won’t see your activity, so you can browse more privately. This won't change how data is collected by websites you visited.") : "Maximum valid character limit not accepted.";
    }

    @Test(priority = 47, description = "Verify an input less than the minimum character limit (2) displays an error message")
    public void testMainStepDescFieldBelowMin() {
        WebElement mainStepDescField = driver.findElement(By.xpath(mainStepDescription));
        waitUntilNextElement(By.xpath(mainStepDescription), 10);
        mainStepDescField.clear();
        mainStepDescField.sendKeys("AB");

        List<WebElement> errorMessages = driver.findElements(By.xpath(mainStepDescriptionValidationError));
        // Ensure the test fails if the expected error message is NOT displayed
        assert !errorMessages.isEmpty() : "Error message for below minimum character limit not displayed.(with characters below minimum limit)";
    }

    @Test(priority = 48, description = "Verify an input greater than the maximum character limit (192) displays an error message")
    public void testMainStepDescFieldAboveMax() {
        WebElement mainStepDescField = driver.findElement(By.xpath(mainStepDescription));
        waitUntilNextElement(By.xpath(mainStepDescription), 10);
        mainStepDescField.clear();
        mainStepDescField.sendKeys("Others who use this device won’t see your activity, so you can browse more privately. This won't change how data is collected by websites you visit and the services they use, including Google.");

        WebElement errorMessage = driver.findElement(By.xpath(mainStepDescriptionValidationError));
        assert errorMessage.isDisplayed() : "Error message for exceeding maximum character limit not displayed.";
        assert errorMessage.getText().equals("Main step description can't be more than 150 Characters") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 49, description = "Test that All Characters are Accepted in Description Input Field")
    public void testMainStepDescFieldAllCharactersAllowed() {
        WebElement mainStepDescField = driver.findElement(By.xpath(mainStepDescription));
        waitUntilNextElement(By.xpath(mainStepDescription), 10);
        mainStepDescField.clear();
        mainStepDescField.sendKeys("ABCdef123!@#$%^&*()_+-=[]{}|;:',.<>?/`~");

        assert Objects.equals(mainStepDescField.getAttribute("value"), "ABCdef123!@#$%^&*()_+-=[]{}|;:',.<>?/`~") : "The input field did not accept all characters as expected.";
    }

    @Test(priority = 50, description = "Verify that field incorrectly allows multiple consecutive spaces (Bug Detection)")
    public void testMainStepDescFieldAllowsMultipleSpaces() {
        WebElement mainStepDescField = driver.findElement(By.xpath(mainStepDescription));
        waitUntilNextElement(By.xpath(mainStepDescription), 10);
        mainStepDescField.clear();
        mainStepDescField.sendKeys("   "); // Multiple spaces

        String actualValue = mainStepDescField.getAttribute("value");
        System.out.println("Actual Value: '" + actualValue + "'");

        // Check if multiple spaces are retained
        assert Objects.requireNonNull(actualValue).contains("    ") : "Bug detected: Field is not preventing multiple spaces!";
    }

    //------------------------------Sub Step Number Dropdown----------------------------------//

    @Test(priority = 51, description = "Verify the dropdown displays all available sub steps & select the specific sub step.")
    public void testSubStepNumberDropdown() {
        clickElement(subStepDropdown);
        WebElement productDropdown = driver.findElement(By.xpath(subStepDropdown));
        List<WebElement> options = productDropdown.findElements(By.xpath("//li[@role='option']"));
        assert options.size() > 1 : "Sub Step dropdown does not contain any sub steps.";
        int optionCount = options.size();
        System.out.println("Number of options in the subStep dropdown: " + optionCount);

        for (WebElement option : options) {
            System.out.println(option.getText());
        }

        clickElement(subStep1);

        String selectedValue = productDropdown.getAttribute("aria-label");
        Assert.assertEquals(selectedValue, "STEP 1.1", "Selected value is not correct.");

        System.out.println("Selected Sub Step dropdown option is: "+selectedValue);
    }

    @Test(priority = 52, description = "Recipe step Clear button function")
    public void testRecipeStepClearButton() {
        waitUntilNextElement(By.xpath(recipeStepClearBtn),10);
        clickElement(recipeStepClearBtn);
    }

    @Test(priority = 53, description = "Verify that sub-step dropdown is empty until a main step is selected.")
    public void testSubStepAvailabilityDependsOnMainStep() {
        // Step 1: Open Sub Step dropdown without selecting a Main Step
        waitUntilNextElement(By.xpath(subStepDropdown),10);
        clickElement(subStepDropdown);
        WebElement productDropdown = driver.findElement(By.xpath(subStepDropdown));
        List<WebElement> options = productDropdown.findElements(By.xpath("//li[@role='option']"));

        // Step 2: Verify that no sub-steps are available initially
        Assert.assertTrue(options.isEmpty(), "Sub Step dropdown should be empty when no Main Step is selected.");
        System.out.println("Sub Step dropdown is empty before selecting a Main Step.");

        // Step 3: Select a Main Step
        clickElement(mainStepDropdown);
        clickElement(mainStep1);  // Select "STEP 1"
        System.out.println("Main Step 'STEP 1' selected.");

        // Step 4: Open Sub Step dropdown again
        clickElement(subStepDropdown);
        options = productDropdown.findElements(By.xpath("//li[@role='option']"));

        // Step 5: Verify that sub-steps are now available
        Assert.assertFalse(options.isEmpty(), "Sub Step dropdown should be populated after selecting a Main Step.");
        System.out.println("Sub Step dropdown is now populated after selecting a Main Step.");
    }

    //------------------------------Raw Material Dropdown----------------------------------//


    @Test(priority = 54, description = "Verify the dropdown displays all available raw Materials & select the specific raw material")
    public void testRawMaterialDropdown() {
        clickElement(recipeStepClearBtn);
        clickElement(rawMaterialDropdown);
        WebElement rawMaterialDropdownElement = driver.findElement(By.xpath(rawMaterialDropdown));
        List<WebElement> options = rawMaterialDropdownElement.findElements(By.xpath("//li[@role='option']"));
        assert options.size() > 1 : "Raw Material dropdown does not contain any materials.";
        int optionCount = options.size();
        System.out.println("Number of options in the Raw Material dropdown: " + optionCount);

        for (WebElement option : options) {
            System.out.println(option.getText());
        }

        clickElement(rawMaterial3);

        String selectedValue = rawMaterialDropdownElement.getAttribute("aria-label");
        Assert.assertEquals(selectedValue, "EM POLYMER", "Selected value is not correct.");

        System.out.println("Selected Raw Material dropdown option is: "+selectedValue);
    }

//------------------------------Raw Material Quantity----------------------------------//

    @Test(priority = 55, description = "Verify the field accepts valid numeric values.")
    public void testRawMaterialQuantityFieldValidInput() {
        clickElement(recipeStepClearBtn);
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity),10);
        rawMaterialQuantityField.sendKeys("1000.000");
        assert Objects.equals(rawMaterialQuantityField.getAttribute("value"), "1000.000") : "Valid quantity value not accepted.";
    }

    @Test(priority = 56, description = "Verify the minimum boundary value (0.001) is accepted")
    public void testRawMaterialQuantityFieldMinBoundary() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("0.001");
        assert Objects.equals(rawMaterialQuantityField.getAttribute("value"), "0.001") : "Minimum valid quantity value not accepted.";
    }

    @Test(priority = 57, description = "Verify the maximum boundary value (10000000.000) is accepted")
    public void testRawMaterialQuantityFieldMaxBoundary() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("10000000.000");
        assert Objects.equals(rawMaterialQuantityField.getAttribute("value"), "10000000.000") : "Maximum valid quantity value not accepted.";
    }

    @Test(priority = 58, description = "Verify an input less than the minimum boundary (0.000) displays an error message")
    public void testRawMaterialQuantityFieldBelowMin() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("0.000");

        WebElement errorMessage = driver.findElement(By.xpath(rawMaterialQuantityValidationError));
        assert errorMessage.isDisplayed() : "Error message for below minimum value not displayed.";
        assert errorMessage.getText().equals("Raw material quantity (kg) must be greater than 0.001") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 59, description = "Verify an input greater than the maximum boundary (10000000.001) displays an error message")
    public void testRawMaterialQuantityFieldAboveMax() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("10000000.001");

        WebElement errorMessage = driver.findElement(By.xpath(rawMaterialQuantityValidationError));
        assert errorMessage.isDisplayed() : "Error message for exceeding maximum value not displayed.";
        assert errorMessage.getText().equals("Raw material quantity (kg) value cannot exceed 10000000.000") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 60, description = "Verify entering a negative value (-10.000) displays an error message")
    public void testRawMaterialQuantityFieldNegativeValue() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("-10.000");

        WebElement errorMessage = driver.findElement(By.xpath(rawMaterialQuantityValidationError));
        assert errorMessage.isDisplayed() : "Error message for negative value not displayed.";
        assert errorMessage.getText().equals("Raw material quantity (kg) must be greater than 0.001") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 61, description = "Verify entering non-numeric characters is not allowed")
    public void testRawMaterialQuantityFieldNonNumericInput() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("abc123");

        assert !Objects.requireNonNull(rawMaterialQuantityField.getAttribute("value")).matches(".*[a-zA-Z].*") : "Non-numeric characters should not be accepted.";
    }

    @Test(priority = 62, description = "Verify entering special characters is not allowed")
    public void testRawMaterialQuantityFieldSpecialCharacters() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("@#$%^&*()");

        assert Objects.requireNonNull(rawMaterialQuantityField.getAttribute("value")).isEmpty() : "Special characters should not be accepted.";
    }

    @Test(priority = 63, description = "Verify trimming leading and trailing spaces")
    public void testRawMaterialQuantityFieldLeadingTrailingSpaces() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("   500.000   ");

        assert Objects.equals(rawMaterialQuantityField.getAttribute("value"), "500.000") : "Leading or trailing spaces not trimmed correctly.";
    }

    @Test(priority = 64, description = "Verify decimal precision does not allow more than three decimal places")
    public void testRawMaterialQuantityFieldDecimalPrecision() {
        WebElement rawMaterialQuantityField = driver.findElement(By.xpath(inputRawMaterialQuantity));
        waitUntilNextElement(By.xpath(inputRawMaterialQuantity), 10);
        rawMaterialQuantityField.clear();
        rawMaterialQuantityField.sendKeys("123.4567");

        assert Objects.equals(rawMaterialQuantityField.getAttribute("value"), "123.456") : "More than three decimal places should not be accepted.";
    }

    //------------------------------Sub Step Description----------------------------------//


    @Test(priority = 65, description = "Verify the field accepts valid description values.")
    public void testSubStepDescFieldValidInput() {
        WebElement subStepDescField = driver.findElement(By.xpath(subStepDescription));
        waitUntilNextElement(By.xpath(subStepDescription),10);
        subStepDescField.sendKeys("Others who use this device won’t see your activity, so you can browse more privately.");
        assert Objects.equals(subStepDescField.getAttribute("value"), "Others who use this device won’t see your activity, so you can browse more privately.") : "Valid main step description value not accepted.";
    }

    @Test(priority = 66, description = "Verify the minimum character limit (3) is accepted")
    public void testSubStepDescFieldMinBoundary() {
        WebElement subStepDescField = driver.findElement(By.xpath(subStepDescription));
        waitUntilNextElement(By.xpath(subStepDescription), 10);
        subStepDescField.clear();
        subStepDescField.sendKeys("ABC");
        assert Objects.equals(subStepDescField.getAttribute("value"), "ABC") : "Minimum valid description character limit not accepted.";
    }

    @Test(priority = 67, description = "Verify the maximum character limit (150) is accepted")
    public void testSubStepDescFieldMaxBoundary() {
        WebElement subStepDescField = driver.findElement(By.xpath(subStepDescription));
        waitUntilNextElement(By.xpath(subStepDescription), 10);
        subStepDescField.clear();
        subStepDescField.sendKeys("Others who use this device won’t see your activity, so you can browse more privately. This won't change how data is collected by websites you visited.");
        assert Objects.equals(subStepDescField.getAttribute("value"), "Others who use this device won’t see your activity, so you can browse more privately. This won't change how data is collected by websites you visited.") : "Maximum valid character limit not accepted.";
    }

    @Test(priority = 68, description = "Verify an input less than the minimum character limit (2) displays an error message")
    public void testSubStepDescFieldBelowMin() {
        WebElement subStepDescField = driver.findElement(By.xpath(subStepDescription));
        waitUntilNextElement(By.xpath(subStepDescription), 10);
        subStepDescField.clear();
        subStepDescField.sendKeys("AB");

        List<WebElement> errorMessages = driver.findElements(By.xpath(subStepDescriptionValidationError));
        // Ensure the test fails if the expected error message is NOT displayed
        assert !errorMessages.isEmpty() : "Error message for below minimum character limit not displayed.(with characters below minimum limit)";
    }

    @Test(priority = 69, description = "Verify an input greater than the maximum character limit (192) displays an error message")
    public void testSubStepDescFieldAboveMax() {
        WebElement subStepDescField = driver.findElement(By.xpath(subStepDescription));
        waitUntilNextElement(By.xpath(subStepDescription), 10);
        subStepDescField.clear();
        subStepDescField.sendKeys("Others who use this device won’t see your activity, so you can browse more privately. This won't change how data is collected by websites you visit and the services they use, including Google.");

        WebElement errorMessage = driver.findElement(By.xpath(subStepDescriptionValidationError));
        assert errorMessage.isDisplayed() : "Error message for exceeding maximum character limit not displayed.";
        assert errorMessage.getText().equals("Sub step description can't be more than 150 characters") : "Incorrect validation error message displayed.";
    }

    @Test(priority = 70, description = "Test that All Characters are Accepted in Description Input Field")
    public void testSubStepDescFieldAllCharactersAllowed() {
        WebElement subStepDescField = driver.findElement(By.xpath(subStepDescription));
        waitUntilNextElement(By.xpath(subStepDescription), 10);
        subStepDescField.clear();
        subStepDescField.sendKeys("ABCdef123!@#$%^&*()_+-=[]{}|;:',.<>?/`~");

        assert Objects.equals(subStepDescField.getAttribute("value"), "ABCdef123!@#$%^&*()_+-=[]{}|;:',.<>?/`~") : "The input field did not accept all characters as expected.";
    }

    @Test(priority = 75, description = "Verify that field incorrectly allows multiple consecutive spaces (Bug Detection)")
    public void testSubStepDescFieldAllowsMultipleSpaces() {
        WebElement subStepDescField = driver.findElement(By.xpath(subStepDescription));
        waitUntilNextElement(By.xpath(subStepDescription), 10);
        subStepDescField.clear();
        subStepDescField.sendKeys("   "); // Multiple spaces

        String actualValue = subStepDescField.getAttribute("value");
        System.out.println("Actual Value: '" + actualValue + "'");

        // Check if multiple spaces are retained
        assert Objects.requireNonNull(actualValue).contains("    ") : "Bug detected: Field is not preventing multiple spaces!";
    }




    private WebElement waitUntilNextElement(By locator, int maxTimeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTimeOut));
        // Wait for the element to be visible and enabled for interaction
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    private void clickElement(String xpath) {
        WebElement element = waitUntilNextElement(By.xpath(xpath), 10);
        element.click();
    }

    private void clearSelection(String xpath) {
        clickElement(xpath);  // Open the dropdown

        // Locate the clear button inside the dropdown
        try {
            WebElement clearButton = waitUntilNextElement(By.xpath("//button[@aria-label='Clear selection']"), 5);
            if (clearButton.isDisplayed()) {
                clearButton.click();  // Click the clear button if visible
                System.out.println("Dropdown selection cleared.");
            }
        } catch (Exception e) {
            System.out.println("No clear button found. Dropdown might already be empty.");
        }
    }

}