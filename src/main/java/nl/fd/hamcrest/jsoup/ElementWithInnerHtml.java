package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;

/**
 * @author Frans Flippo
*/
public class ElementWithInnerHtml extends TypeSafeDiagnosingMatcher<Element> {
    private final String expectedValue;

    private ElementWithInnerHtml(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the inner HTML equal to {@code expectedValue}.
     *
     * @param expectedValue The expected inner HTML
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element}
     * @deprecated Use {@link #hasInnerHtml(String)} instead
     */
    @Factory
    public static Matcher<Element> hasHtml(final String expectedValue) {
        return hasInnerHtml(expectedValue);
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the inner HTML equal to {@code expectedValue}.
     *
     * @param expectedValue The expected inner HTML
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element}
     */
    @Factory
    public static Matcher<Element> hasInnerHtml(final String expectedValue) {
        return new ElementWithInnerHtml(expectedValue);
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        boolean result = Matchers.is(expectedValue).matches(item.html());
        if (!result) {
            Matchers.is(expectedValue).describeMismatch(item.html(), mismatchDescription);
        }
        return result;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("element with inner HTML '").appendText(expectedValue).appendText("'");
    }
}
