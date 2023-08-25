package com.gildedrose;


import java.util.Arrays;
import java.util.function.Predicate;

public enum ItemType {
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
            item.quality = coerce(item.quality + (item.sellIn <= 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }
    },

    BackstagePasses(item -> item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
        @Override
        public void update(Item item) {
            item.quality = item.sellIn > 0 ? coerce(item.quality + Math.max(1, (20 - item.sellIn) / 5)) : 0;
            item.sellIn--;
        }
    },
    Conjured(item -> item.name.equals("Conjured Mana Cake")) {
        @Override
        public void update(Item item) {
            item.quality = coerce(item.quality - (item.sellIn <= 0 ? 4 : 2));
            item.sellIn--;
        }
    },

    Other(item -> true) { // Default case for any other item name
    };

    private final Predicate<Item> matcher; // A predicate that checks if an item matches the enum constantpublic ItemType() {

    ItemType(Predicate<Item> matcher) {
        this.matcher = matcher;
    }


    //A method that each enum constant can override
    public void update(Item item) {
        // Decrease the quality by 1 or 2 depending on the sell in value
        item.quality = coerce(item.quality - (item.sellIn <= 0 ? 2 : 1));
        // Decrease the sell in value by 1
        item.sellIn--;
    }
    // A static method that updates an item using the enum constant that matches its name or the default one

    public static void updateItem(Item item) {
        Arrays.stream(ItemType.values())
                .filter(it -> it.matcher.test(item))
                .findFirst()
                .orElse(Other)
                .update(item);
    }
    private static int coerce(int value) {
        return Math.max(0, Math.min(50, value));
    }
}