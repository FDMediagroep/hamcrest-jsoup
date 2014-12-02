package nl.fd.hamcrest.jsoup.test;

import nl.fd.hamcrest.jsoup.ElementWithName;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ElementWithNameTest {

    @Test
    public void testWithName_string() {
        // Given
        Matcher<Element> matcher = ElementWithName.hasName("div");
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();

        // When
        boolean matches = matcher.matches(element);

        // Then
        assertTrue(matches);
    }

    @Test
    public void testWithName_matcher() {
        // Given
        Matcher<Element> matcher = ElementWithName.hasName("p");
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();

        // When
        boolean matches = matcher.matches(element);

        // Then
        assertFalse(matches);
    }

    @Test
    public void testWithName_describeMismatch_no_mismatch() {
        // Given
        Matcher<Element> matcher = ElementWithName.hasName("div");
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();
        StringDescription description = new StringDescription();

        // When
        matcher.describeMismatch(element, description);

        // Then
        assertEquals("", description.toString());
    }

    @Test
    public void testWithName_describeMismatch_name_mismatch() {
        // Given
        Matcher<Element> matcher = ElementWithName.hasName("p");
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();
        StringDescription description = new StringDescription();

        // When
        matcher.describeMismatch(element, description);

        // Then
        assertEquals("element name was \"div\"", description.toString());
    }

    @Test
    public void testWithName_describeMismatch_matcher() {
        // Given
        Matcher<Element> matcher = ElementWithName.hasName(startsWith("de"));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();
        StringDescription description = new StringDescription();

        // When
        matcher.describeMismatch(element, description);

        // Then
        assertEquals("element name was \"div\"", description.toString());
    }

    @Test
    public void testWithName_describeTo() {
        // Given
        Matcher<Element> matcher = ElementWithName.hasName("p");
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertEquals("element with name \"p\"", description.toString());
    }

}