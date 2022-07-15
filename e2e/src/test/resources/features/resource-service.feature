Feature: Resource upload flow

  Scenario: Upload new resource
    Given resource '/data/Lakey Inspired - Better Days.mp3' is uploaded
    When request to get song metadata by uploaded resource id returns not empty results
    Then verify song metadata response body with uploaded resource id:
      | name        | artist         | album       | length | year |
      | Better Days | Lakey Inspired | Better Days | 208    | 2018 |