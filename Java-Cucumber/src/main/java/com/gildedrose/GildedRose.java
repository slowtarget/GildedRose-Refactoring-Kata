package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(item -> ItemType.handleusingchain(item));
    }
}

// An enum that defines the behaviour of different item types
enum ItemType {
    Sulfuras {
        @Override
        public void update(Item item) {
            // Do nothing, sulfuras never changes
        }

        @Override
        public boolean matches(Item item) {
            return item.name.equals("Sulfuras, Hand of Ragnaros");
        }

        @Override
        public void handle(Item item) {
           if (matches(item)) {
               update(item);
           } else {
               AgedBrie.handle(item);
           }
        }


    },
    AgedBrie {
        @Override
        public void update(Item item) {
            // Increase the quality by 1 or 2 depending on the sell in value
            item.quality = coerce(0, 50, item.quality + (item.sellIn <= 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }

        @Override
        public boolean matches(Item item) {
            return item.name.equals("Aged Brie");
        }

        @Override
        public void handle(Item item) {
            if (matches(item)) {
                update(item);
            } else {
                BackstagePasses.handle(item);
            }
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
            item.quality = coerce(0, 50, item.quality);
            // Decrease the sell in value by 1
            item.sellIn--;
        }

        @Override
        public boolean matches(Item item) {
            return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
        }

        @Override
        public void handle(Item item) {
            if (matches(item)) {
                update(item);
            } else {
                Other.handle(item);
            }
        }
    },
    Other {
        @Override
        public void update(Item item) {
            // Decrease the quality by 1 or 2 depending on the sell in value
            item.quality = coerce(0, 50, item.quality - (item.sellIn <= 0 ? 2 : 1));
            // Decrease the sell in value by 1
            item.sellIn--;
        }

        @Override
        public boolean matches(Item item) {
            return true;
        }

        @Override
        public void handle(Item item) {
            update(item);
        }
    };

    public static void handleusingchain(Item item) {
        Sulfuras.handle(item);
    }

    //An abstract method that each enum constant must implement
    public abstract void update(Item item);

    // An abstract method that each enum constant must implement to return a boolean that checks if an item matches the enum constant
    public abstract boolean matches(Item item);

    private static int coerce(int min, int max, int value) {
        return Math.max(min, Math.min(max, value));
    }

    public abstract void handle(Item item);
}
