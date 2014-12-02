package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;

/**
 * @author Frans Flippo
*/
public class ElementWithText extends TypeSafeDiagnosingMatcher<Element> {
    private final Matcher<? super String> textMatcher;

    public ElementWithText(Matcher<? super String> textMatcher) {
        this.textMatcher = textMatcher;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code text} as its own text content. Also
     * see {@link #withText(org.hamcrest.Matcher)} for use with matchers.
     *
     * @param text The text content to match
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code text} as its own text content
     */
    public static Matcher<Element> withText(final String text) {
        return withText(Matchers.is(text));
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code text} as its own text content.
     *
     * @param textMatcher Matcher for the element's text
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code text} as its own text content
     */
    public static Matcher<Element> withText(final Matcher<? super String> textMatcher) {
        return new ElementWithText(textMatcher);
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        String actualText = item.text();
        if (!textMatcher.matches(actualText)) {
            mismatchDescription.appendText("expected element with text matching (").appendDescriptionOf(textMatcher)
                    .appendText(") but was ").appendValue(actualText);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("element with text ").appendDescriptionOf(textMatcher);
    }
}
