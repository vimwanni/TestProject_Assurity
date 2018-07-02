Feature: tmsandbox Categories test

Scenario Outline: As a tester I want to test Categories API
Given Categories API is under test
When category id <categId> is provided
	And catalogue parameter is <ctlogPref>
	And Category details API is called
Then API response contains Name as <expName>
	And API response contains CanRelist as <expCanRelistVal>
	And Promotion element with name <name> contains <text> in description

Examples:
|categId	|ctlogPref	|expName					|expCanRelistVal	|name				|text							|
|"6327"		|"false"		|"Carbon credits"	|"true"						|"Gallery"	|"2x larger image"|