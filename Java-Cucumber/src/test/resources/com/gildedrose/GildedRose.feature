Feature: Gilded Rose quality
  I want to know if the quality is updated properly

  Scenario: Checking normal depreciation
    Given The item as "+5 Dexterity Vest" with initial sellin 10 and quality 20
    When I update the quality
    Then I should get sellin as 9 and quality as 19
