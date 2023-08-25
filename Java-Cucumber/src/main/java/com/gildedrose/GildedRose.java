package com.gildedrose;

import java.util.Arrays;
import java.util.function.Predicate;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(item -> ItemType.updateItem(item));
    }
}

// An enum that defines the behaviour of different item types
enum ItemType {
    Sulfuras(item -> item.name.equals("Sulfuras, Hand of Ragnaros")) {
        @Override
        public void update(Item item) {
            // Do nothing, sulfuras never changes
        }
    },
    AgedBrie(item -> item.name.equals("Aged Brie")) {
        @Override
        public void update(Item item) {
            // Increase the quality by 1 or 2 depending on the sell in value
            item.quality = coerce(0, 50, item.quality + (item.sellIn <= 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }
    },
    BackstagePasses(item -> item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
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
    Other(item -> true) { // Default case for any other item name
        @Override
        public void update(Item item) {
            // Decrease the quality by 1 or 2 depending on the sell in value
            item.quality = coerce(0, 50, item.quality - (item.sellIn <= 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }
    };

    private Predicate<Item> matcher; // A predicate that checks if an item matches the enum constant

    private ItemType(Predicate<Item> matcher) {
        this.matcher = matcher;
    }

    //A method that each enum constant can override
    public void update(Item item) {
        // Decrease the quality by 1 or 2 depending on the sell in value
        item.quality = coerce(0, 50, item.quality - (item.sellIn <= 0 ? 2 : 1));
        // Decrease the sell in value by 1
        item.sellIn--;
    }

    // A static method that updates an item using the enum constant that matches its name or the default one
    public static void updateItem(Item item) {
        Arrays.stream(values())
                .filter(it -> it.matcher.test(item))
                .findFirst()
                .orElse(Other)
                .update(item);
    }

    private static int coerce(int min, int max, int value) {
        return Math.max(min, Math.min(max, value));
    }
}
