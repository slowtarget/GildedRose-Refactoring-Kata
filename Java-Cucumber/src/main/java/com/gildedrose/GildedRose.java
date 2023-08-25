package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(this::updateItem);
    }

    private void updateItem(Item item) {
        // Get the item type from the enum or use the default one
        ItemType itemType = ItemType.valueOf(item.name).orElse(ItemType.Other);
        // Update the item using the item type behaviour
        itemType.update(item);
    }
}

// An enum that defines the behaviour of different item types
enum ItemType {
    Sulfuras {
        @Override
        public void update(Item item) {
            // Do nothing, sulfuras never changes
        }
    },
    AgedBrie {
        @Override
        public void update(Item item) {
            // Increase the quality by 1 or 2 depending on the sell in value
            item.quality = Math.coerce(0, 50, item.quality + (item.sellIn < 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }
    },
    BackstagePasses {
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
            item.quality = Math.coerce(0, 50, item.quality);
            // Decrease the sell in value by 1
            item.sellIn--;
        }
    },
    Other {
        @Override
        public void update(Item item) {
            // Decrease the quality by 1 or 2 depending on the sell in value
            item.quality = Math.coerce(0, 50, item.quality - (item.sellIn < 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }
    };

    // An abstract method that each enum constant must implement
    public abstract void update(Item item);

    // A static method that returns an optional enum constant from a string name
    public static Optional<ItemType> valueOf(String name) {
        try {
            return Optional.of(Enum.valueOf(ItemType.class, name));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
