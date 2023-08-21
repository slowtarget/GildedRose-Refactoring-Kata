package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }
    public void updateQuality() {
        for (Item item : items) {
            ItemType itemType = ItemType.toItemType(item.name);
            item.sellIn -= itemType.getAging();
            item.quality = itemType.getQuality(item);
        }
    }
}
