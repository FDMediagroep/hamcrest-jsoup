package nl.fd.hamcrest.jsoup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

/**
 * Provides access to Hamcrest {@link Matcher}s for JSoup {@link Element}s and {@link Elements}.
 * <p/>
 * TODO give each Matcher its own named class instead of having anonymous inner classes
 *
 * @author Quinten Krijger
 */
public final class JSoupMatchers {

    private JSoupMatchers() { }

    /**
     * Creates a {@link ElementWithName}.
     *
     * @param matcher matcher to match the {@link Element}s name against
     * @return the created {@link ElementWithName}
     * @see ElementWithName
     */
    public static Matcher<Element> withName(final Matcher<? super String> matcher) {
        return new ElementWithName(matcher);
    }

    /**
     * Creates a {@link ElementWithName}.
     *
     * @param name string that the {@link Element}s name should be equal to
     * @return the created {@link ElementWithName}
     * @see ElementWithName
     */
    public static Matcher<Element> withName(final String name) {
        return withName(equalTo(name));
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code text} as its own text content.
     *
     * @param text The text content to match
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code text} as its own text content
     */
    public static Matcher<Element> withOwnText(final String text) {
        return new TypeSafeDiagnosingMatcher<Element>() {
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
        };
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code text} as its own text content. Also
     * see {@link #withText(Matcher)} for use with matchers.
     *
     * @param text The text content to match
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code text} as its own text content
     */
    public static Matcher<Element> withText(final String text) {
        return withText(Matchers.is(text));
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code text} as its own text content.
     *
     * @param textMatcher Matcher for the element's text
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code text} as its own text content
     */
    public static Matcher<Element> withText(final Matcher<? super String> textMatcher) {
        return new TypeSafeDiagnosingMatcher<Element>() {
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
        };
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Elements} containing {@link Element}s with the given {@code texts}
     * as their own text content. The order of the elements is important.
     *
     * @param texts The texts for which the {@link Element}s content should match
     * @return a {@link Matcher} for a JSoup {@link Elements} containing {@link Element}s with the given {@code texts}
     * as their own text content
     * @deprecated in favour of {@link Matchers#contains(List)}
     */
    @Deprecated
    public static Matcher<Elements> containingElementsWithOwnTexts(final String... texts) {
        Matcher[] elementWithTextMatchers = new Matcher[texts.length];
        for (int i = 0; i < elementWithTextMatchers.length; i++) {
            elementWithTextMatchers[i] = withOwnText(texts[i]);
        }
        return Matchers.contains(
                elementWithTextMatchers
        );
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code data} as its own data content.
     *
     * @param matcher The Matcher to use for matching the data content
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code text} as its own data content
     */
    public static Matcher<Element> withData(final Matcher<String> matcher) {
        return new TypeSafeDiagnosingMatcher<Element>() {
            @Override
            protected boolean matchesSafely(Element item, Description mismatchDescription) {
                return matcher.matches(item.data());
            }

            @Override
            public void describeTo(Description description) {
                matcher.describeTo(description);
            }
        };
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the specified css class(es). Multiple classes can be
     * separated by whitespace as valid in HTML. E.g. {@code <div class='a b c'></div>} would be matched by
     * withClass('c b').
     *
     * @param className The css class content to match
     * @return a {@link Matcher} for a JSoup {@link Element} with the specified css class(es)
     */
    public static Matcher<Element> withClass(final String className) {
        return new TypeSafeDiagnosingMatcher<Element>() {
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
        };
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the "href"
     * attribute. See {@link #withAttribute(String, org.hamcrest.Matcher)} for use with Matchers.
     *
     * @param expectedValue The attribute value that is expected
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the "href"
     */
    public static Matcher<Element> withHref(final String expectedValue) {
        return JSoupMatchers.withHref(Matchers.is(expectedValue));
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the "href"
     * attribute. See {@link #withAttribute(String, org.hamcrest.Matcher)} for use with Matchers.
     *
     * @param attributeValueMatcher A matcher for the attribute value
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the "href"
     */
    public static Matcher<Element> withHref(final Matcher<? super String> attributeValueMatcher) {
        return JSoupMatchers.withAttribute("href", attributeValueMatcher);
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the given {@code
     * attributeName}. See {@link #withAttribute(String, org.hamcrest.Matcher)} for use with Matchers.
     *
     * @param attributeName The attribute name whose value to check
     * @param expectedValue The attribute value that is expected
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the given {@code
     * attributeName}
     */
    public static Matcher<Element> withAttribute(final String attributeName,
                                                 final String expectedValue) {
        return JSoupMatchers.withAttribute(attributeName, Matchers.is(expectedValue));
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the given {@code
     * attributeName}.
     *
     * @param attributeName         The attribute name whose value to check
     * @param attributeValueMatcher A matcher for the attribute value
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the given {@code
     * attributeName}
     */
    public static Matcher<Element> withAttribute(final String attributeName, final Matcher<? super String> attributeValueMatcher) {
        return new TypeSafeDiagnosingMatcher<Element>() {
            @Override
            protected boolean matchesSafely(Element item, Description mismatchDescription) {
                if (item.hasAttr(attributeName)) {
                    String value = item.attr(attributeName);
                    if (!attributeValueMatcher.matches(value)) {
                        mismatchDescription.appendText("expected attribute with name ").appendValue(attributeName)
                                .appendText(" and with value matching ").appendDescriptionOf(attributeValueMatcher)
                                .appendText(" but was ").appendValue(value);
                        return false;
                    }
                } else {
                    mismatchDescription.appendText("expected attribute with name ").appendValue(attributeName)
                            .appendText(" but there were none ");
                    return false;
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(attributeName).appendText(" ").appendDescriptionOf(attributeValueMatcher);
            }
        };
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the given {@code
     * attributeName}. See {@link #withAttribute(String, org.hamcrest.Matcher)} for use with Matchers.
     *
     * @param expectedValue The attribute value that is expected
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the given {@code
     * attributeName}
     */
    public static Matcher<Element> withHtml(final String expectedValue) {
        return new TypeSafeDiagnosingMatcher<Element>() {
            @Override
            protected boolean matchesSafely(Element item, Description mismatchDescription) {
                boolean result = Matchers.is(expectedValue).matches(item.html());
                if (!result) {
                    Matchers.is(expectedValue).describeMismatch(item.html(), mismatchDescription);
                }
                return result;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("element with HTML content '").appendText(expectedValue).appendText("'");
            }
        };
    }

    /**
     * Creates a {@link Matcher} for a JSoup {@link Element}'s children
     *
     * @param cssExpression   The css expression to apply to the element being matched
     * @param elementsMatcher A matcher for the selected elements iterable
     * @return a {@link Matcher} for a JSoup {@link Element} with the given {@code expectedValue} for the given {@code
     * attributeName}
     */
    // TODO: make arg Matcher<Elements>
    public static Matcher<Element> withChildElements(final String cssExpression, final Matcher<? super Iterable<Element>> elementsMatcher) {
        return new TypeSafeDiagnosingMatcher<Element>() {
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
        };
    }
}
