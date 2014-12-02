package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;

import static org.hamcrest.Matchers.equalTo;

/**
 * Matcher for {@link Element} with a certain name. E.g. &lt;div&gt; has name "div".
 *
 * @author Quinten Krijger
 * @author Frans Flippo
 */
public class ElementWithName extends TypeSafeDiagnosingMatcher<Element> {

    private final Matcher<? super String> matcher;

    /**
     * @see ElementWithName#withName(org.hamcrest.Matcher)
     */
    ElementWithName(Matcher<? super String> matcher) {
        this.matcher = matcher;
    }

    /**
     * Creates a {@link nl.fd.hamcrest.jsoup.ElementWithName}.
     *
     * @param matcher matcher to match the {@link org.jsoup.nodes.Element}s name against
     * @return the created {@link nl.fd.hamcrest.jsoup.ElementWithName}
     * @see nl.fd.hamcrest.jsoup.ElementWithName
     */
    public static Matcher<Element> withName(final Matcher<? super String> matcher) {
        return new ElementWithName(matcher);
    }

    /**
     * Creates a {@link nl.fd.hamcrest.jsoup.ElementWithName}.
     *
     * @param name string that the {@link org.jsoup.nodes.Element}s name should be equal to
     * @return the created {@link nl.fd.hamcrest.jsoup.ElementWithName}
     * @see nl.fd.hamcrest.jsoup.ElementWithName
     */
    public static Matcher<Element> withName(final String name) {
        return withName(equalTo(name));
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        String actualName = item.tagName();
        if (!matcher.matches(actualName)) {
            mismatchDescription.appendText("element name ");
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
