package com.gildedrose;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(this::updateItem);
    }

    private void updateItem(Item item) {
        Optional.of(item)
                .filter(isSulfuras())
                .ifPresent(this::updateSulfuras);

        Optional.of(item)
                .filter(isAgedBrie().and(isSulfuras().negate()))
                .ifPresent(this::updateAgedBrie);

        Optional.of(item)
                .filter(isBackstagePasses().and(isSulfuras().negate()))
                .ifPresent(this::updateBackstagePasses);

        Optional.of(item)
                .filter(isOtherItem().and(isSulfuras().negate()))
                .ifPresent(this::updateOtherItem);
    }

    private Predicate<Item> isSulfuras() {
        return item -> item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private Predicate<Item> isAgedBrie() {
        return item -> item.name.equals("Aged Brie");
    }

    private Predicate<Item> isBackstagePasses() {
        return item -> item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private Predicate<Item> isOtherItem() {
        return item -> !isSulfuras().test(item) && !isAgedBrie().test(item) && !isBackstagePasses().test(item);
    }

    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }

    private void decreaseQuality(Item item) {
        item.quality = Math.coerce(0, 50, item.quality - 1);
    }

    private void increaseQuality(Item item) {
        item.quality = Math.coerce(0, 50, item.quality + 1);
    }

    private void increaseQualityByBackstageRules(Item item) {
        increaseQuality(item);
        if (item.sellIn < 11) {
            increaseQuality(item);
        }
        if (item.sellIn < 6) {
            increaseQuality(item);
        }
    }

    private void updateSulfuras(Item item) {
        // Do nothing, sulfuras never changes
    }

    private void updateAgedBrie(Item item) {
        increaseQuality(item);
        if (item.sellIn < 0) {
            increaseQuality(item);
        }
        decreaseSellIn(item);
    }

    private void updateBackstagePasses(Item item) {
        increaseQualityByBackstageRules(item);
        if (item.sellIn < 0) {
            item.quality = 0;
        }
        decreaseSellIn(item);
    }

    private void updateOtherItem(Item item) {
        decreaseQuality(item);
        if (item.sellIn < 0) {
            decreaseQuality(item);
        }
        decreaseSellIn(item);
    }
}
