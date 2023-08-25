package com.gildedrose;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class GildedRose {
    Item[] items;
    Map<String, ItemType> itemTypes;

    public GildedRose(Item[] items) {
        this.items = items;
        // Initialize the map of item types
        itemTypes = new HashMap<>();
        itemTypes.put("Sulfuras, Hand of Ragnaros", new Sulfuras());
        itemTypes.put("Aged Brie", new AgedBrie());
        itemTypes.put("Backstage passes to a TAFKAL80ETC concert", new BackstagePasses());
        itemTypes.put("Other", new OtherItem());
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(this::updateItem);
    }

    private void updateItem(Item item) {
        // Get the item type from the map or use the default one
        ItemType itemType = itemTypes.getOrDefault(item.name, itemTypes.get("Other"));
        // Update the item using the item type behaviour
        itemType.update(item);
    }
}

// An interface that defines the behaviour of different item types
interface ItemType {
    void update(Item item);
}

// A class that implements the behaviour of sulfuras items
class Sulfuras implements ItemType {

    @Override
    public void update(Item item) {
        // Do nothing, sulfuras never changes
    }
}

// A class that implements the behaviour of aged brie items
class AgedBrie implements ItemType {

    @Override
    public void update(Item item) {
        // Increase the quality by 1 or 2 depending on the sell in value
        item.quality = Math.coerce(0, 50, item.quality + (item.sellIn < 0 ? 2 : 1));
        // Decrease the sell in value by 1
        item.sellIn--;
    }
}

// A class that implements the behaviour of backstage passes items
class BackstagePasses implements ItemType {

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
}

// A class that implements the behaviour of other items
class OtherItem implements ItemType {

    @Override
    public void update(Item item) {
        // Decrease the quality by 1 or 2 depending on the sell in value
        item.quality = Math.coerce(0, 50, item.quality - (item.sellIn < 0 ? 2 : 1));
        // Decrease the sell in value by 1
        item.sellIn--;
    }
}
