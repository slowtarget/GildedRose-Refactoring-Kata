Feature: Gilded Rose quality
  I want to know if the quality is updated properly

  Scenario Outline: Gilded Rose Requirements Specification
    Given "<name>" with initial sellin <iSellin> and quality <iQuality>
    When I update the quality
    Then I should get sellin as <eSellin> and quality as <eQuality>
    Examples: At the end of each day our system lowers both values for every item
      | name              | iSellin | iQuality | eSellin | eQuality |
      | +5 Dexterity Vest | 10      | 20       | 9       | 19       |

    Examples: Once the sell by date has passed, Quality degrades twice as fast
      | name              | iSellin | iQuality | eSellin | eQuality |
      | +5 Dexterity Vest | 0       | 20       | -1      | 18       |
      | +5 Dexterity Vest | 1       | 20       | 0       | 19       |
      | +5 Dexterity Vest | -1      | 20       | -2      | 18       |



    Examples: The Quality of an item is never negative
      | name              | iSellin | iQuality | eSellin | eQuality |
      | +5 Dexterity Vest | 0       | 0        | -1      | 0        |
      | +5 Dexterity Vest | -1      | 1        | -2      | 0        |

    Examples: "Aged Brie" actually increases in Quality the older it gets
      | name      | iSellin | iQuality | eSellin | eQuality |
      | Aged Brie | 10      | 20       | 9       | 21       |
      | Aged Brie | 10      | 20       | 9       | 21       |
      | Aged Brie | 1       | 20       | 0       | 21       |
      | Aged Brie | 0       | 20       | -1      | 22       |

    Examples: The Quality of an item is never more than 50 (aged brie)
      | name      | iSellin | iQuality | eSellin | eQuality |
      | Aged Brie | 10      | 50       | 9       | 50       |

    Examples: "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
      | name                       | iSellin | iQuality | eSellin | eQuality |
      | Sulfuras, Hand of Ragnaros | 10      | 80       | 10      | 80       |
      | Sulfuras, Hand of Ragnaros | 100     | 80       | 100     | 80       |

    Examples: "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
    Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    Quality drops to 0 after the concert
      | name                                      | iSellin | iQuality | eSellin | eQuality |
      | Backstage passes to a TAFKAL80ETC concert | 20      | 20       | 19      | 21       |
      | Backstage passes to a TAFKAL80ETC concert | 11      | 20       | 10      | 21       |
      | Backstage passes to a TAFKAL80ETC concert | 10      | 20       | 9       | 22       |
      | Backstage passes to a TAFKAL80ETC concert | 6       | 20       | 5       | 22       |
      | Backstage passes to a TAFKAL80ETC concert | 5       | 20       | 4       | 23       |
      | Backstage passes to a TAFKAL80ETC concert | 1       | 20       | 0       | 23       |
      | Backstage passes to a TAFKAL80ETC concert | 0       | 20       | -1      | 0        |

    Examples: The Quality of an item is never more than 50 (backstage passes)
      | name                                      | iSellin | iQuality | eSellin | eQuality |
      | Backstage passes to a TAFKAL80ETC concert | 20      | 50       | 19      | 50       |
      | Backstage passes to a TAFKAL80ETC concert | 11      | 50       | 10      | 50       |
      | Backstage passes to a TAFKAL80ETC concert | 10      | 50       | 9       | 50       |
      | Backstage passes to a TAFKAL80ETC concert | 6       | 50       | 5       | 50       |
      | Backstage passes to a TAFKAL80ETC concert | 5       | 50       | 4       | 50       |
      | Backstage passes to a TAFKAL80ETC concert | 1       | 50       | 0       | 50       |
      | Backstage passes to a TAFKAL80ETC concert | 0       | 50       | -1      | 0        |

    Examples: "Conjured" items degrade in Quality twice as fast as normal items
      | name               | iSellin | iQuality | eSellin | eQuality |
      | Conjured Mana Cake | 10      | 10       | 9       | 8        |
      | Conjured Mana Cake | -10     | 1        | -11     | 0        |
      | Conjured Mana Cake | -10     | 2        | -11     | 0        |
      | Conjured Mana Cake | -10     | 3        | -11     | 0        |
      | Conjured Mana Cake | -10     | 4        | -11     | 0        |
      | Conjured Mana Cake | -10     | 5        | -11     | 1        |
      | Conjured Mana Cake | 2       | 10       | 1       | 8        |
      | Conjured Mana Cake | 1       | 8        | 0       | 6        |
      | Conjured Mana Cake | 0       | 6        | -1      | 2        |
      | Conjured Mana Cake | -1      | 2        | -2      | 0        |
      | Conjured Mana Cake | 2       | 11       | 1       | 9        |
      | Conjured Mana Cake | 1       | 9        | 0       | 7        |
      | Conjured Mana Cake | 0       | 7        | -1      | 3        |
      | Conjured Mana Cake | -1      | 3        | -2      | 0        |