hamcrest-jsoup
[![All Contributors](https://img.shields.io/badge/all_contributors-4-orange.svg?style=flat-square)](#contributors)
==============
The Hamcrest JSoup library provides a set of matchers for JSoup elements to assert the contents of those objects. With this library you can easily unit test HTML contents in your backend.   

The JavaDoc can be found at: http://files2.fd.nl/development/hamcrest-jsoup/javadoc/ or via maven central!

###How to use hamcrest-jsoup in your project:
- Clone this project 
- Build it using maven clean install 
- Add it to your dependencies using 
```
<dependency>
	<groupId>nl.fd</groupId>
      <artifactId>hamcrest-jsoup</artifactId>
      <version>0.1.12-SNAPSHOT</version>
</dependency>
```

you can also download the latest release from
http://files2.fd.nl/development/hamcrest-jsoup/binaries/0.1.7/hamcrest-jsoup-0.1.7.jar

###Standard Maven dependency
With a lot of tries we managed to get this into maven central, so you can add a dependency from your project directly!
http://search.maven.org/#search%7Cga%7C1%7Chamcrest-jsoup

###Example Usage
Here we provide a code sample to give you a rough idea how easy to use this library is.  
```
	String html = "<html><body> <div class=\"content container\"><h1>Some user form</h1> "
                +"<form role=\"form\" method=\"POST\" action=\"/users/save\"> "
                +"<div class=\"form-group\">"
                +"<label for=\"username\">Username</label>"
                +"<input type=\"text\" id=\"username\" name=\"username\" />"
                +"</div>"
                +"<div class=\"form-group\">"
                +"<label for=\"password\">Password</label>"
                +"<input type=\"password\" id=\"password\" name=\"password\" />"
                +"</div>"
                +"</form>"
                +"</div>"
                +"</body>"
                +"</html>";
                
	Document document = Jsoup.parse(html);
        
	Elements formElements = document.select("div.content > form");
	assertThat(formElements.get(0), hasAttribute("action", "/users/save"));
```

Or when using MockMVC: 
```
	MvcResult result = mockMvc.perform(get("/users/new")).andReturn();
	assertThat(result.getResponse().getStatus(), is(200));
	
	String content = result.getResponse().getContentAsString();
	Document document = Jsoup.parse(content);
	
	Elements formElements = document.select("div.content > form");
	assertThat(formElements.get(0), hasAttribute("action", "/users/save"));
```

The result of a mock http get request is parsed into a JSoup document of which a specific element is selected using a css selector. The **ElementWithAttrribute** matcher is used to check if the form action has value "/users/save".  

###Matchers
The following matchers are provided: 
- **ElementWithAttribute** Matcher with a certain attribute having a specific value
  - hasAttribute
  - hasHref
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
- **ElementWithChild** Matcher having at least one child with given CSS selector.
  - hasChild
- **ElementWithUniqueChild** Matcher having exactly one child with given CSS selector.
  - hasUniqueChild
- **Selecting** Matcher that has a list of child nodes matching the specified cssExpression  
- **SelectingFirst** Matcher selecting the first element child nodes matching the specified cssExpression


## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/JHannema"><img src="https://avatars2.githubusercontent.com/u/5299964?v=4" width="100px;" alt="Joram Hannema"/><br /><sub><b>Joram Hannema</b></sub></a><br /><a href="https://github.com/FDMediagroep/hamcrest-jsoup/commits?author=JHannema" title="Code">💻</a> <a href="https://github.com/FDMediagroep/hamcrest-jsoup/commits?author=JHannema" title="Documentation">📖</a> <a href="#maintenance-JHannema" title="Maintenance">🚧</a></td>
    <td align="center"><a href="https://github.com/jmsnoeij"><img src="https://avatars3.githubusercontent.com/u/3830004?v=4" width="100px;" alt="Jeroen M Snoeij"/><br /><sub><b>Jeroen M Snoeij</b></sub></a><br /><a href="https://github.com/FDMediagroep/hamcrest-jsoup/commits?author=jmsnoeij" title="Code">💻</a> <a href="https://github.com/FDMediagroep/hamcrest-jsoup/commits?author=jmsnoeij" title="Documentation">📖</a> <a href="#maintenance-jmsnoeij" title="Maintenance">🚧</a></td>
    <td align="center"><a href="https://github.com/Krijger"><img src="https://avatars1.githubusercontent.com/u/3886732?v=4" width="100px;" alt="Quinten Krijger"/><br /><sub><b>Quinten Krijger</b></sub></a><br /><a href="https://github.com/FDMediagroep/hamcrest-jsoup/commits?author=Krijger" title="Code">💻</a> <a href="https://github.com/FDMediagroep/hamcrest-jsoup/commits?author=Krijger" title="Documentation">📖</a></td>
    <td align="center"><a href="http://naghavi.me"><img src="https://avatars1.githubusercontent.com/u/4481421?v=4" width="100px;" alt="Mohammad Naghavi"/><br /><sub><b>Mohammad Naghavi</b></sub></a><br /><a href="https://github.com/FDMediagroep/hamcrest-jsoup/commits?author=mohamnag" title="Code">💻</a></td>
  </tr>
</table>

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!