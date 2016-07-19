package nl.fd.hamcrest.jsoup.test;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static nl.fd.hamcrest.jsoup.ElementWithData.hasData;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

public class ElementWithDataTest {

    @Test
    public void testWithData_describeMismatch_no_mismatch() {
        // Given
        Matcher<Element> matcher = hasData(containsString("var i = 1;"));
        Element element = Jsoup.parse("<body><script>var i = 1;</script></body>").body().children().first();
        StringDescription description = new StringDescription();

        // When
        matcher.describeMismatch(element, description);

        // Then
        assertEquals("", description.toString());
    }

    @Test
    public void testWithData_describeMismatch_matcher() {
        // Given
        Matcher<Element> matcher = hasData(containsString("var i = 2;"));
        Element element = Jsoup.parse("<body><script>var i = 1;</script></body>").body().children().first();
        StringDescription description = new StringDescription();

        // When
        matcher.describeMismatch(element, description);

        // Then
        assertEquals("data was \"var i = 1;\"", description.toString());
    }

    @Test
    public void testWithData_describeTo() {
        // Given
        Matcher<Element> matcher = hasData(containsString("var i = 1;"));
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertEquals("element to have data matching a string containing \"var i = 1;\"", description.toString());

    }

}