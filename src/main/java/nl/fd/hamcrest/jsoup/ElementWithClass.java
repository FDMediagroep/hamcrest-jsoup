package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;

/**
 * @author Frans Flippo
*/
public class ElementWithClass extends TypeSafeDiagnosingMatcher<Element> {
    private final String className;

    private ElementWithClass(String className) {
        this.className = className;
    }

    /**
     * Creates a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the specified css class(es). Multiple classes can be
     * separated by whitespace as valid in HTML. E.g. {@code <div class='a b c'></div>} would be matched by
     * hasClass('c b').
     *
     * @param className The css class content to match
     * @return a {@link org.hamcrest.Matcher} for a JSoup {@link org.jsoup.nodes.Element} with the specified css class(es)
     */
    @Factory
    public static Matcher<Element> hasClass(final String className) {
        return new ElementWithClass(className);
    }

    @Override
    protected boolean matchesSafely(Element item, Description mismatchDescription) {
        if (item.hasAttr("class")) {

            String value = item.attr("class");
            List<String> classes = Arrays.asList(value.split("\\s+"));
            List<String> expectedClasses = Arrays.asList(className.split("\\s+"));

            if (!classes.containsAll(expectedClasses)) {
                mismatchDescription.appendText("expected classes '").appendValue(expectedClasses)
                        .appendText("', but found ").appendValue(classes);
                return false;
            }
        } else {
            mismatchDescription.appendText("expected attribute class ").appendValue(className)
                    .appendText(" but there were none ");
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(className);
    }
}
