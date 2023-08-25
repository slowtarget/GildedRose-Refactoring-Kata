package com.gildedrose;

import java.util.Arrays;
import java.util.Optional;
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
        ItemType itemType = Arrays.stream(ItemType.values())
                .filter(it -> it.matches(item.name))
                .findFirst()
                .orElse(ItemType.Other);
        itemType.update(item);
    }
}

enum ItemType {
    Sulfuras("Sulfuras, Hand of Ragnaros") {
        @Override
        public void update(Item item) {
        }
    },
    AgedBrie("Aged Brie") {
        @Override
        public void update(Item item) {
            item.quality = coerce(0, 50, item.quality + (item.sellIn <= 0 ? 2 : 1));
            item.sellIn--;
        }
    },
    BackstagePasses("Backstage passes to a TAFKAL80ETC concert") {
        @Override
        public void update(Item item) {
            if (item.sellIn > 10) {
                item.quality++;
            } else if (item.sellIn > 5) {
                item.quality += 2;
            } else if (item.sellIn > 0) {
                item.quality += 3;
            } else {
                item.quality = 0;
            }
            item.quality = coerce(0, 50, item.quality);
            item.sellIn--;
        }
    },
    Other(".*") {
        @Override
        public void update(Item item) {
            item.quality = coerce(0, 50, item.quality - (item.sellIn <= 0 ? 2 : 1));
            item.sellIn--;
        }


    };

    private String name;

    private ItemType(String name) {
        this.name = name;
    }

    public abstract void update(Item item);

    public boolean matches(String itemName) {
        return Pattern.matches(name, itemName);
    }

    private static int coerce(int min, int max, int value) {
        return Math.max(min, Math.min(max, value));
    }
}
