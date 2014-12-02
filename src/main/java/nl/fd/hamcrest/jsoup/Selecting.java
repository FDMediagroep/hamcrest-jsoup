package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
* Created by frans on 11/21/14.
*/
public class Selecting<T>  extends TypeSafeDiagnosingMatcher<Element> {
    private final String cssExpression;
    private final Matcher<T> selectionMatcher;
    private final Class<T> selectedClass;

    private Selecting(String cssExpression, Matcher<T> selectionMatcher, Class<T> selectedClass) {
        this.cssExpression = cssExpression;
        this.selectionMatcher = selectionMatcher;
        this.selectedClass = selectedClass;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} that has a list of child nodes
     * matching the specified cssExpression that are matched by the specified elementsMatcher.
     *
     * @param cssExpression The Jsoup CSS expression used to selected a list of child nodes
     * @param elementsMatcher the matcher that the selected child nodes will be matched against
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element}
     */
    @Factory
    @SuppressWarnings("unchecked")
    public static Matcher<Element> selecting(final String cssExpression, final Matcher<? super Iterable<Element>> elementsMatcher) {
        return new Selecting(cssExpression, elementsMatcher, List.class);
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
        return new Selecting(cssExpression, elementMatcher, Element.class);
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        Elements elements = item.select(cssExpression);

        if (selectedClass == Element.class) {
            Element element = elements.first();
            if (!selectionMatcher.matches(element)) {
                mismatchDescription.appendText("expected attribute with child selected by ").appendValue(cssExpression).
                        appendText(" matching ").appendDescriptionOf(selectionMatcher)
                        .appendText(" but ");
                selectionMatcher.describeMismatch(element, mismatchDescription);
                return false;
            }
        } else {
            if (!selectionMatcher.matches(elements)) {
                mismatchDescription.appendText("expected attribute with children selected by ").appendValue(cssExpression).
                        appendText(" matching ").appendDescriptionOf(selectionMatcher)
                        .appendText(" but ");
                selectionMatcher.describeMismatch(elements, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        if (selectedClass == Element.class) {
            description.appendText(" has child selected by ").appendValue(cssExpression).appendText(" matching ").appendDescriptionOf(selectionMatcher);
        } else {
            description.appendText(" has children selected by ").appendValue(cssExpression).appendText(" matching ").appendDescriptionOf(selectionMatcher);
        }
    }
}
