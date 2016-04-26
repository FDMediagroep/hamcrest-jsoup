package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Matches a given element, if it contains at least one child selectable
 * by given CSS selector.
 *
 * Created by mohamnag on 25/04/16.
 */
public class ElementWithUniqueChild extends TypeSafeDiagnosingMatcher<Element> {

    private String cssSelector;

    private ElementWithUniqueChild(String cssSelector) {
        this.cssSelector = cssSelector;
    }

    @Override
    protected boolean matchesSafely(Element parent, Description description) {
        Elements elements = parent.select(cssSelector);

        if (elements.isEmpty()) {
            description
                    .appendText("expected element to have exactly one child matching selector ")
                    .appendValue(cssSelector)
                    .appendText(" but nothing found.");

            return false;

        } else if (elements.isEmpty() || elements.size() > 1) {
            description
                    .appendText("expected element to have exactly one child matching selector ")
                    .appendValue(cssSelector)
                    .appendText(" but ")
                    .appendText(String.valueOf(elements.size()))
                    .appendText(" found.");

            return false;
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" has child selected by ").appendValue(cssSelector);
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with exactly one child element that matches {@code expectedValue}.
     *
     * @param cssSelector The CSS selector to match the child node on.
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element}
     */
    @Factory
    public static Matcher<Element> hasUniqueChild(String cssSelector) {
        return new ElementWithUniqueChild(cssSelector);
    }
}
