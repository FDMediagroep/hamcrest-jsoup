package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;

/**
 * @author Frans Flippo
*/
public class ElementWithOwnText extends TypeSafeDiagnosingMatcher<Element> {
    private final String text;

    private ElementWithOwnText(String text) {
        this.text = text;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code text} as its own text content.
     *
     * @param text The text content to match
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the given {@code text} as its own text content
     */
    @Factory
    public static Matcher<Element> hasOwnText(final String text) {
        return new ElementWithOwnText(text);
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        String actualText = item.ownText();
        if (!actualText.equals(text)) {
            mismatchDescription.appendText("expected ").appendValue(text)
                    .appendText(" but was ").appendValue(actualText);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(text);
    }
}
