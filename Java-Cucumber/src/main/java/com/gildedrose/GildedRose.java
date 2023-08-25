package com.gildedrose;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(this::updateItem);
    }

    private void updateItem(Item item) {
        // Get the item type from the stream of enum values or use the default one
        ItemType itemType = Arrays.stream(ItemType.values())
                .filter(it -> it.matches().test(item.name))
                .findFirst()
                .orElse(ItemType.Other);
        // Update the item using the item type behaviour
        itemType.update(item);
    }
}

// An enum that defines the behaviour of different item types
enum ItemType {
    Sulfuras("Sulfuras, Hand of Ragnaros") {
        @Override
        public void update(Item item) {
            // Do nothing, sulfuras never changes
        }
    },
    AgedBrie("Aged Brie") {
        @Override
        public void update(Item item) {
            // Increase the quality by 1 or 2 depending on the sell in value
            item.quality = coerce(0, 50, item.quality + (item.sellIn <= 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }
    },
    BackstagePasses("Backstage passes to a TAFKAL80ETC concert") {
        @Override
        public void update(Item item) {
            // Increase the quality by 1, 2, or 3 depending on the sell in value
            if (item.sellIn > 10) {
                item.quality++;
            } else if (item.sellIn > 5) {
                item.quality += 2;
            } else if (item.sellIn > 0) {
                item.quality += 3;
            } else {
                // Drop the quality to zero if the sell in value is negative
                item.quality = 0;
            }
            // Coerce the quality to be between 0 and 50
            item.quality = coerce(0, 50, item.quality);
            // Decrease the sell in value by 1
            item.sellIn--;
        }
    },
    Other(".*") {
        @Override
        public void update(Item item) {
            // Decrease the quality by 1 or 2 depending on the sell in value
            item.quality = coerce(0, 50, item.quality - (item.sellIn <= 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }


    };

    private String name; // The regex name of the item type

    private ItemType(String name) {
        this.name = name;
    }

    //An abstract method that each enum constant must implement
    public abstract void update(Item item);

    // A method that returns a predicate that checks if an item name matches the regex name of the enum constant
    public Predicate<String> matches() {
        return itemName -> Pattern.matches(name, itemName);
    }

    private static int coerce(int min, int max, int value) {
        return Math.max(min, Math.min(max, value));
    }
}
