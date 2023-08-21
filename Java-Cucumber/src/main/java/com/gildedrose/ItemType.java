package com.gildedrose;

public enum ItemType {
    AGED_BRIE("Aged Brie"),
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert"),
    SULFURAS("Sulfuras, Hand of Ragnaros");
    private final String value;

    ItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}