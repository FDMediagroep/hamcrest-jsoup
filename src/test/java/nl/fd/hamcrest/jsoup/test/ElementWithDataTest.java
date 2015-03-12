package nl.fd.hamcrest.jsoup.test;

import nl.fd.hamcrest.jsoup.ElementWithData;
import nl.fd.hamcrest.jsoup.ElementWithName;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElementWithDataTest {

    @Test
    public void testWithData_describeMismatch_no_mismatch() {
        // Given
        Matcher<Element> matcher = ElementWithData.hasData(containsString("var i = 1;"));
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
        Matcher<Element> matcher = ElementWithData.hasData(containsString("var i = 2;"));
        Element element = Jsoup.parse("<body><script>var i = 1;</script></body>").body().children().first();
        StringDescription description = new StringDescription();

        // When
        matcher.describeMismatch(element, description);

        // Then
        assertEquals(" data was \"var i = 1;\"", description.toString());
    }

    @Test
    public void testWithData_describeTo() {
        // Given
        Matcher<Element> matcher = ElementWithData.hasData(containsString("var i = 1;"));
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertEquals("element with data a string containing \"var i = 1;\"", description.toString());
    }

}