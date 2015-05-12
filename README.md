hamcrest-jsoup
==============
The Hamcrest JSoup library provides a set of matchers for JSoup elements to easily assert the contents of those objects.

###To inlude hamcrest-jsoup in your project:
- Clone this project 
- Build it using maven clean install 
- Add it to your dependencies using 
```
<dependency>
	<groupId>nl.fd</groupId>
      <artifactId>hamcrest-jsoup</artifactId>
      <version>0.1.6</version>
</dependency>
```

###Example implementation 
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
- **ElementWithClass** Matcher with a certain css class 
- **ElementWithData** Matcher having the desired matcher 
- **ElementWithInnerHtml** Matcher containing (inner) html 
- **ElementWithName** Matcher having name 
- **ElementWithOwnText** Matcher having text  
- **ElementWithText** Matcher having text matcher 
- **JSoupMatchers** Provides access to Hamcrest Matchers for JSoup Element and Elements 
- **Selecting** Matcher that has a list of child nodes matching the specified cssExpression  
- **SelectingFirst** Matcher selecting the first element child nodes matching the specified cssExpression
 

