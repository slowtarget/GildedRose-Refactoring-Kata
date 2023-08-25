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
                .filter(isSulfuras().negate())
                .filter(isAgedBrie().negate())
                .filter(isBackstagePasses().negate())
                .filter(hasPositiveQuality())
                .ifPresent(this::decreaseQuality);

        Optional.of(item)
                .filter(isAgedBrie())
                .filter(hasQualityBelow50())
                .ifPresent(this::increaseQuality);

        Optional.of(item)
                .filter(isBackstagePasses())
                .filter(hasQualityBelow50())
                .ifPresent(this::increaseQualityByBackstageRules);

        Optional.of(item)
                .filter(isSulfuras().negate())
                .filter(i -> i.sellIn < 0)
                .filter(isAgedBrie().negate())
                .filter(isBackstagePasses().negate())
                .filter(hasPositiveQuality())
                .ifPresent(this::decreaseQuality);

        Optional.of(item)
                .filter(i -> i.sellIn < 0)
                .filter(isAgedBrie())
                .filter(hasQualityBelow50())
                .ifPresent(this::increaseQuality);

        Optional.of(item)
                .filter(i -> i.sellIn < 0)
                .filter(isBackstagePasses())
                .ifPresent(i -> i.quality = 0);
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
}
