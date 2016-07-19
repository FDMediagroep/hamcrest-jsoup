package nl.fd.hamcrest.jsoup.test;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static nl.fd.hamcrest.jsoup.ElementWithText.hasText;
import static nl.fd.hamcrest.jsoup.Selecting.selecting;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

/**
 * Unit tests for the Selecting class.
 */
public class SelectingTest {

    @Test
    public void testMatches_match() throws Exception {
        // Given
        Matcher<Element> selected = selecting("p", hasItem(hasText("Some linked text")));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p><p>Some unlinked text</p></div>").body().children().first();

        // When
        boolean matches = selected.matches(element);

        // Then
        assertTrue(matches);
    }

    @Test
    public void testMatches_mismatch() throws Exception {
        // Given
        Matcher<Element> selected = selecting("p", hasItem(hasText("Some weird text")));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p><p>Some unlinked text</p></div>").body().children().first();

        // When
        boolean matches = selected.matches(element);

        // Then
        assertFalse(matches);
    }

    @Test
    public void testDescribeMismatch_noMismatch() throws Exception {
        // Given
        Matcher<Element> selected = selecting("p", hasItem(hasText("Some linked text")));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p><p>Some unlinked text</p></div>").body().children().first();
        Description description = new StringDescription();

        // When
        selected.describeMismatch(element, description);

        // Then
        assertEquals("", description.toString());
    }

    @Test
    public void testDescribeMismatch() throws Exception {
        // Given
        Matcher<Element> selected = selecting("p", hasItem(hasText("Some weird text")));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p><p>Some unlinked text</p></div>").body().children().first();
        Description description = new StringDescription();

        // When
        selected.describeMismatch(element, description);

        // Then
        assertEquals("has children selected by \"p\" do not match expected element with text matching (is \"Some weird text\") but was \"Some linked text\", expected element with text matching (is \"Some weird text\") but was \"Some unlinked text\"", description.toString());
    }

}
