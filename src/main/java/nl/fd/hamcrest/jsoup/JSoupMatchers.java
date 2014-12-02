package nl.fd.hamcrest.jsoup;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Provides access to Hamcrest {@link Matcher}s for JSoup {@link Element}s and {@link Elements}.
 * <p/>
 *
 * @author Quinten Krijger
 * @author Frans Flippo
 */
public final class JSoupMatchers {

    private JSoupMatchers() { }

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
            elementWithTextMatchers[i] = ElementWithOwnText.hasOwnText(texts[i]);
        }
        return Matchers.contains(
                elementWithTextMatchers
        );
    }

}
