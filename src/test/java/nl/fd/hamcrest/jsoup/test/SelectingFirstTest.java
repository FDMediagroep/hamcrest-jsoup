package nl.fd.hamcrest.jsoup.test;

import nl.fd.hamcrest.jsoup.Selecting;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static nl.fd.hamcrest.jsoup.ElementWithText.hasText;
import static nl.fd.hamcrest.jsoup.SelectingFirst.selectingFirst;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class SelectingFirstTest {

    @Test
    public void testMatches_match() throws Exception {
        // Given
        Matcher<Element> selecting = selectingFirst("p", hasText("Some linked text"));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();

        // When
        boolean matches = selecting.matches(element);

        // Then
        assertTrue(matches);
    }

    @Test
    public void testMatches_mismatch() throws Exception {
        // Given
        Matcher<Element> selecting = selectingFirst("p", hasText("Some weird text"));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();

        // When
        boolean matches = selecting.matches(element);

        // Then
        assertFalse(matches);
    }

    @Test
    public void testDescribeMismatch_noMismatch() throws Exception {
        // Given
        Matcher<Element> selecting = selectingFirst("p", hasText("Some linked text"));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();
        Description description = new StringDescription();

        // When
        selecting.describeMismatch(element, description);

        // Then
        assertEquals("", description.toString());
    }

    @Test
    public void testDescribeMismatch() throws Exception {
        // Given
        Matcher<Element> selecting = selectingFirst("p", hasText("Some weird text"));
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();
        Description description = new StringDescription();

        // When
        selecting.describeMismatch(element, description);

        // Then
        assertEquals("expected element with child selected by \"p\" matching element with text is \"Some weird text\" but expected element with text matching (is \"Some weird text\") but was \"Some linked text\"", description.toString());
    }
}
