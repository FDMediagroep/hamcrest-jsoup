hamcrest-jsoup
==============
The Hamcrest JSoup library provides a set of matchers for JSoup elements to easily assert the contents of those objects.

###How to use hamcrest-jsoup in your project:
- Clone this project 
- Build it using maven clean install 
- Add it to your dependencies using 
```
<dependency>
	<groupId>nl.fd</groupId>
      <artifactId>hamcrest-jsoup</artifactId>
      <version>0.1.8-SNAPSHOT</version>
</dependency>
```

###Example Usage
Here we provide a code sample to give you a rough idea how easy this library is to use.  
```
  MvcResult result = mockMvc.perform(get("/users/new")).andReturn();

  String content = result.getResponse().getContentAsString();
  Document document = Jsoup.parse(content);

  Elements formElements = document.select("div.content > form");
  assertThat(formElements.get(0), hasAttribute("action", "/users/save"));
```

The result of a mock http get request is parsed into a JSoup document of which a specific element is selected using a css selector. The **ElementWithAttrribute** matcher is used to check if the form action has value "/users/save".  

###Matchers
The following matchers are provided: 
- **ElementWithAttrribute** Matcher with a certain attribute having a specific value
  - hasAttribute
- **ElementWithClass** Matcher with a certain css class 
  - hasClass 
- **ElementWithData** Matcher having the desired matcher 
  - hasData
- **ElementWithInnerHtml** Matcher containing (inner) html 
  - hasHtml
  - hasInnerHtml
- **ElementWithName** Matcher having name 
  - hasName 
- **ElementWithOwnText** Matcher having text  
  - hasOwnText 
- **ElementWithText** Matcher having text matcher 
  - hasText
- **Selecting** Matcher that has a list of child nodes matching the specified cssExpression  
- **SelectingFirst** Matcher selecting the first element child nodes matching the specified cssExpression
 

