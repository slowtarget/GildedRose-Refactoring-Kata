Feature: Gilded Rose quality
  I want to know if the quality is updated properly

  Scenario Outline: Checking normal depreciation
    Given "<name>" with initial sellin <initialSellin> and quality <initialQuality>
    When I update the quality
    Then I should get sellin as <expectedSellin> and quality as <expectedQuality>
    Examples:
    | name               | initialSellin | initialQuality | expectedSellin | expectedQuality |
    | +5 Dexterity Vest  |            10 |             20 |              9 |              19 |



