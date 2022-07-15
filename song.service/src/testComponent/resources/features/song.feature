Feature: Song functionalities
  This feature contains a list of functionalities related to song

  Scenario: get song metadata by song id
    Given the collection of songs:
      | id  | name       | artist          | album    | length | year | resourceId |
      | 1   | Believer   | Imagine Dragons | Evolve   | 225    | 2017 | 23         |
      | 2   | I See Fire | Ed Sheeran      | See Fire | 300    | 2013 | 42         |
    When song id 1 is passed to get the song metadata
    Then song metadata is returned:
      | id  | name       | artist          | album    | length | year | resourceId |
      | 1   | Believer   | Imagine Dragons | Evolve   | 225    | 2017 | 23         |

  Scenario: save song metadata
    When song metadata is saved:
      | name       | artist          | album    | length | year | resourceId |
      | Believer   | Imagine Dragons | Evolve   | 225    | 2017 | 23         |
    Then song metadata is returned:
      | id  | name       | artist          | album    | length | year | resourceId |
      | 3   | Believer   | Imagine Dragons | Evolve   | 225    | 2017 | 23         |
    And song metadata is persisted:
      | id  | name       | artist          | album    | length | year | resourceId |
      | 3   | Believer   | Imagine Dragons | Evolve   | 225    | 2017 | 23         |

  Scenario: delete song metadata by song id list
    Given the collection of songs:
      | id  | name       | artist          | album    | length | year | resourceId |
      | 4   | Believer   | Imagine Dragons | Evolve   | 225    | 2017 | 23         |
      | 5   | I See Fire | Ed Sheeran      | See Fire | 300    | 2013 | 42         |
    When song id list is passed to delete song metadata:
      | 4 | 5 |
    Then song id list is returned:
      | 4 | 5 |
    And song metadata is not persisted for song id list:
      | 4 | 5 |