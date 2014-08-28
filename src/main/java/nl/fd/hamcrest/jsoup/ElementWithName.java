package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;

/**
 * Matcher for {@link Element} with a certain name. E.g. &lt;div&gt; has name "div".
 *
 * @author Quinten Krijger
 */
public class ElementWithName extends TypeSafeDiagnosingMatcher<Element> {

    private final Matcher<? super String> matcher;

    /**
     * @see JSoupMatchers#withName(Matcher)
     */
    ElementWithName(Matcher<? super String> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        String actualName = item.tagName();
        if (!matcher.matches(actualName)) {
            matcher.describeMismatch(actualName, mismatchDescription);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("element with name ");
        matcher.describeTo(description);
    }
}
