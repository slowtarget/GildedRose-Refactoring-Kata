package com.gildedrose;

import java.util.Arrays;
import java.util.function.Function;

public enum ItemType {
    AGED_BRIE("Aged Brie", Constants.UPDATE_BRIE_QUALITY, Constants.NORMAL_MAXIMUM, 1),
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert", Constants.UPDATE_BACKSTAGE_PASS_QUALITY, Constants.NORMAL_MAXIMUM, 1),
    SULFURAS("Sulfuras, Hand of Ragnaros", Constants.UPDATE_SULFURAS_QUALITY, Constants.LEGENDARY_MAXIMUM, 0),
    DEFAULT("default", Constants.UPDATE_QUALITY, Constants.NORMAL_MAXIMUM, 1);

    private final String value;
    private final Function<Item, Integer> updateQuality;
    private final int maximum;

    public int getAging() {
        return aging;
    }

    private final int aging;

    ItemType(String value, Function<Item, Integer> updateQuality, int maximum, int aging) {
        this.value = value;
        this.updateQuality = updateQuality;
        this.maximum = maximum;
        this.aging = aging;
    }

    public String getValue() {
        return value;
    }

    public int getQuality(final Item item) {
        int quality = this.updateQuality.apply(item);

        return Math.min(Math.max(quality, 0), maximum);
    }

    public static ItemType toItemType(String name) {
        return Arrays.stream(ItemType.values())
                .filter(type -> type.getValue().equals(name))
                .findFirst()
                .orElse(ItemType.DEFAULT);
    }

    private static class Constants {
        public static final int NORMAL_MAXIMUM = 50;
        public static final int LEGENDARY_MAXIMUM = 80;
        private static final Function<Item, Integer> UPDATE_SULFURAS_QUALITY = (Item item) -> item.quality;
        private static final Function<Item, Integer> UPDATE_BACKSTAGE_PASS_QUALITY = (Item item) -> {
            if (item.sellIn < 0) {
                return 0;
            }
            return item.quality + Math.max(1, (19 - item.sellIn) / 5);
        };
        private static final Function<Item, Integer> UPDATE_BRIE_QUALITY = (Item item) -> item.quality + 1;
        private static final Function<Item, Integer> UPDATE_QUALITY = (Item item) -> {
            if (item.sellIn > 0) {
                return item.quality - 1;
            } else {
                return item.quality - 2;
            }
        };
    }
}