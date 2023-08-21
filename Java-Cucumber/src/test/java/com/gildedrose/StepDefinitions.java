package com.gildedrose;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
    private final Item[] items = new Item[1];
    private GildedRose app;

    @Given("The item as {string} with initial sellin {int} and quality {int}")
    public void theItemAsWithInitialSellinAndQuality(String name, int sellin, int quality) {
        items[0] = new Item(name, sellin, quality);
        app = new GildedRose(items);
    }

    @When("I update the quality")
    public void i_update_the_quality() {
        app.updateQuality();
    }

    @Then("I should get sellin as {int} and quality as {int}")
    public void iShouldGetSellinAsAndQualityAs(int sellin, int quality) {
        assertAll(
                () ->  assertEquals(sellin, app.items[0].sellIn),
                () ->  assertEquals(quality, app.items[0].quality)
        );
    }
}

