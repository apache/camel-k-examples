Feature: the API allows CRUD operations on a S3 bucket


  Background:
    Given integration api is running
    Given URL: http://api


  Scenario: LIST objects
    When send GET /
    Then verify HTTP response header Content-Type="application/json"
    And receive HTTP 200 OK


  Scenario: SAVE object
    Given variable objectName is "citrus:randomString(10)"
    Given variable sampleText is "This is a sample text"
    Given HTTP request header Content-Type is "application/octet-stream"
    Given HTTP request body
      """
      ${sampleText}
      """
    When send PUT /${objectName}
    Then receive HTTP 200 OK
    Then verify HTTP response body
      """
      ${sampleText}
      """


  Scenario: GET object
    Given variable objectName is "citrus:randomString(10)"
    Given variable sampleText is "This is another sample text"
    Given HTTP request body
      """
      ${sampleText}
      """
    Given HTTP request header Content-Type is "application/octet-stream"
    Given send PUT /${objectName}
    Given receive HTTP 200 OK
    When send GET /${objectName}
    Then receive HTTP 200 OK
    Then verify HTTP response body
      """
      ${sampleText}
      """
  

  Scenario: DELETE object
    Given variable objectName is "citrus:randomString(10)"
    Given variable sampleText is "This is yet another sample text"
    Given HTTP request body
      """
      ${sampleText}
      """
    Given HTTP request header Content-Type is "application/octet-stream"
    Given send PUT /${objectName}
    Given receive HTTP 200 OK
    When send DELETE /${objectName}
    Then receive HTTP 204 OK


  Scenario: expose OpenAPI
    When send GET /openapi.json
    Then receive HTTP 200 OK
