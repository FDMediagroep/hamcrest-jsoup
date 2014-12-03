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
public class SelectingFirst extends TypeSafeDiagnosingMatcher<Element> {

    private final String cssExpression;
    private final Matcher<Element> selectionMatcher;

    public SelectingFirst(String cssExpression, Matcher<Element> selectionMatcher) {
        this.cssExpression = cssExpression;
        this.selectionMatcher = selectionMatcher;
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        Elements elements = item.select(cssExpression);
        Element element = elements.first();
        if (!selectionMatcher.matches(element)) {
            mismatchDescription.appendText("expected element with child selected by ").appendValue(cssExpression).
                    appendText(" matching ").appendDescriptionOf(selectionMatcher)
                    .appendText(" but ");
            selectionMatcher.describeMismatch(element, mismatchDescription);
            return false;
        }
        return true;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} that has a list of child nodes
     * matching the specified cssExpression that are matched by the specified elementsMatcher.
     *
     * @param cssExpression The Jsoup CSS expression used to selected a list of child nodes
     * @param elementMatcher the matcher that the selected child nodes will be matched against
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element}
     */
    @Factory
    @SuppressWarnings("unchecked")
    public static Matcher<Element> selectingFirst(final String cssExpression, final Matcher<Element> elementMatcher) {
        return new SelectingFirst(cssExpression, elementMatcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" has child selected by ").appendValue(cssExpression).appendText(" matching ").appendDescriptionOf(selectionMatcher);
    }

}
