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
                .filter(not(this::isSulfuras))
                .ifPresent(this::decreaseSellIn);

        Optional.of(item)
                .filter(not(this::isSulfuras))
                .filter(not(this::isAgedBrie))
                .filter(not(this::isBackstagePasses))
                .filter(this::hasPositiveQuality)
                .ifPresent(this::decreaseQuality);

        Optional.of(item)
                .filter(this::isAgedBrie)
                .filter(this::hasQualityBelow50)
                .ifPresent(this::increaseQuality);

        Optional.of(item)
                .filter(this::isBackstagePasses)
                .filter(this::hasQualityBelow50)
                .ifPresent(this::increaseQualityByBackstageRules);

        Optional.of(item)
                .filter(not(this::isSulfuras))
                .filter(i -> i.sellIn < 0)
                .filter(not(this::isAgedBrie))
                .filter(not(this::isBackstagePasses))
                .filter(this::hasPositiveQuality)
                .ifPresent(this::decreaseQuality);

        Optional.of(item)
                .filter(i -> i.sellIn < 0)
                .filter(this::isAgedBrie)
                .filter(this::hasQualityBelow50)
                .ifPresent(this::increaseQuality);

        Optional.of(item)
                .filter(i -> i.sellIn < 0)
                .filter(this::isBackstagePasses)
                .ifPresent(i -> i.quality = 0);
    }

    private boolean isSulfuras(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private boolean isAgedBrie(Item item) {
        return item.name.equals("Aged Brie");
    }

    private boolean isBackstagePasses(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private boolean hasPositiveQuality(Item item) {
        return item.quality > 0;
    }

    private boolean hasQualityBelow50(Item item) {
        return item.quality < 50;
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

    // A helper method that returns the negation of a predicate
    private static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
}
