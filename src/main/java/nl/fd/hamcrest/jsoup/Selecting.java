package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 */
public class Selecting extends TypeSafeDiagnosingMatcher<Element> {
    private final String cssExpression;
    private final Matcher<Iterable<? super Element>> selectionMatcher;

    private Selecting(String cssExpression, Matcher<Iterable<? super Element>> selectionMatcher) {
        this.cssExpression = cssExpression;
        this.selectionMatcher = selectionMatcher;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} that has a list of child nodes
     * matching the specified cssExpression that are matched by the specified elementsMatcher.
     *
     * @param cssExpression   The Jsoup CSS expression used to selected a list of child nodes
     * @param elementsMatcher the matcher that the selected child nodes will be matched against
     *
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element}
     */
    @Factory
    @SuppressWarnings("unchecked")
    public static Matcher<Element> selecting(final String cssExpression, final Matcher<Iterable<? super Element>> elementsMatcher) {
        return new Selecting(cssExpression, elementsMatcher);
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        Elements elements = item.select(cssExpression);

        if (!selectionMatcher.matches(elements)) {
            mismatchDescription.appendText("has children selected by ").appendValue(cssExpression)
                    .appendText(" do not match ");
            selectionMatcher.describeMismatch(elements, mismatchDescription);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("to have children selected by ").appendValue(cssExpression).appendText(" matching ").appendDescriptionOf(selectionMatcher);
    }
}
