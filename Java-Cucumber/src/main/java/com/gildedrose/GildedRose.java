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
                .filter(isSulfuras().negate())
                .ifPresent(this::decreaseSellIn);

        Optional.of(item)
                .filter(isSulfuras())
                .ifPresent(this::updateSulfuras);

        Optional.of(item)
                .filter(isAgedBrie())
                .ifPresent(this::updateAgedBrie);

        Optional.of(item)
                .filter(isBackstagePasses())
                .ifPresent(this::updateBackstagePasses);

        Optional.of(item)
                .filter(isOtherItem())
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

    private Predicate<Item> hasPositiveQuality() {
        return item -> item.quality > 0;
    }

    private Predicate<Item> hasQualityBelow50() {
        return item -> item.quality < 50;
    }

    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }

    private void decreaseQuality(Item item) {
        item.quality--;
    }

    private void increaseQuality(Item item) {
        item.quality++;
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
        if (hasQualityBelow50().test(item)) {
            increaseQuality(item);
            if (item.sellIn < 0 && hasQualityBelow50().test(item)) {
                increaseQuality(item);
            }
        }
    }

    private void updateBackstagePasses(Item item) {
        if (hasQualityBelow50().test(item)) {
            increaseQualityByBackstageRules(item);
            if (item.sellIn < 0) {
                item.quality = 0;
            }
        }
    }

    private void updateOtherItem(Item item) {
        if (hasPositiveQuality().test(item)) {
            decreaseQuality(item);
            if (item.sellIn < 0 && hasPositiveQuality().test(item)) {
                decreaseQuality(item);
            }
        }
    }
}
