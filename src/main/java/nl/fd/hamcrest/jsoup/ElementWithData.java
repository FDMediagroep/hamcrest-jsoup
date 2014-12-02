package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;

/**
 * @author Frans Flippo
*/
class ElementWithData extends TypeSafeDiagnosingMatcher<Element> {
    private final Matcher<String> matcher;

    private ElementWithData(Matcher<String> matcher) {
        this.matcher = matcher;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code data} as its own data content.
     *
     * @param matcher The Matcher to use for matching the data content
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code text} as its own data content
     */
    public static Matcher<Element> withData(final Matcher<String> matcher) {
        return new ElementWithData(matcher);
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        return matcher.matches(item.data());
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }
}
