package nl.fd.hamcrest.jsoup.test;

import nl.fd.hamcrest.jsoup.ElementWithUniqueChild;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mohamnag on 25/04/16.
 */
public class ElementWithUniqueChildTest {

    @Test
    public void testMatches_match() {
        // Given
        Matcher<Element> hasChild = ElementWithUniqueChild.hasUniqueChild(".second-div a[href=testvalue]");
        Element body = Jsoup.parse(
                "<body>" +
                        "<div class=\"first-div\"></div>" +
                        "<div class=\"second-div\">" +
                        "<span>" +
                        "<a href=\"testvalue\">link</a>" +
                        "</span>" +
                        "</div>" +
                        "</body>"
        );

        // When
        boolean matches = hasChild.matches(body);

        // Then
        assertTrue(matches);
    }

    @Test
    public void testMatches_mismatch_notFound() {
        // Given
        Matcher<Element> hasChild = ElementWithUniqueChild.hasUniqueChild(".first-div a");
        Element body = Jsoup.parse(
                "<body>" +
                        "<div class=\"first-div\"></div>" +
                        "<div class=\"second-div\">" +
                        "<span>" +
                        "<a href=\"testvalue\">link</a>" +
                        "</span>" +
                        "</div>" +
                        "</body>"
        );

        // When
        boolean matches = hasChild.matches(body);

        // Then
        assertFalse(matches);
    }

    @Test
    public void testMatches_mismatch_manyFound() {
        // Given
        Matcher<Element> hasChild = ElementWithUniqueChild.hasUniqueChild("body > div");
        Element body = Jsoup.parse(
                "<body>" +
                        "<div class=\"first-div\"></div>" +
                        "<div class=\"second-div\">" +
                        "<span>" +
                        "<a href=\"testvalue\">link</a>" +
                        "</span>" +
                        "</div>" +
                        "</body>"
        );

        // When
        boolean matches = hasChild.matches(body);

        // Then
        assertFalse(matches);
    }


    @Test
    public void testDescribeMismatch_noMismatch() throws Exception {
        // Given
        Matcher<Element> selecting = ElementWithUniqueChild.hasUniqueChild("p");
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
        Matcher<Element> selecting = ElementWithUniqueChild.hasUniqueChild("span");
        Element element = Jsoup.parse("<div><p>Some <a href=\"abc\">linked</a> text</p></div>").body().children().first();
        Description description = new StringDescription();

        // When
        selecting.describeMismatch(element, description);

        // Then
        assertEquals(
                "expected element to have exactly one child matching selector \"span\" but nothing found.",
                description.toString()
        );
    }

}
