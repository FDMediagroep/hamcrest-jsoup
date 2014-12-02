package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
* Created by frans on 11/21/14.
*/
public class Selecting extends TypeSafeDiagnosingMatcher<Element> {
    private final String cssExpression;
    private final Matcher<? super Iterable<Element>> elementsMatcher;

    public Selecting(String cssExpression, Matcher<? super Iterable<Element>> elementsMatcher) {
        this.cssExpression = cssExpression;
        this.elementsMatcher = elementsMatcher;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element}'s children
     *
     * @param cssExpression   The css expression to apply to the element being matched
     * @param elementsMatcher A matcher for the selected elements
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code expectedValue} for the given {@code
     * attributeName}
     * @deprecated Use {@link #selecting(String, org.hamcrest.Matcher)} instead
     */
    public static Matcher<Element> withChildElements(final String cssExpression, final Matcher<? super Iterable<Element>> elementsMatcher) {
    }

    public static Matcher<Element> selecting(final String cssExpression, final Matcher<? super Iterable<Element>> elementsMatcher) {
        return new Selecting(cssExpression, elementsMatcher);
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        Elements elements = item.select(cssExpression);

        if (!elementsMatcher.matches(elements)) {
            mismatchDescription.appendText("expected attribute with children selected by ").appendValue(cssExpression).
                    appendText(" matching ").appendDescriptionOf(elementsMatcher)
                    .appendText(" but ");
            elementsMatcher.describeMismatch(elements, mismatchDescription);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" has children selected by ").appendValue(cssExpression).appendText(" matching ").appendDescriptionOf(elementsMatcher);
    }
}
