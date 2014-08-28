package nl.fd.hamcrest.jsoup.test;

import nl.fd.hamcrest.jsoup.JSoupMatchers;
import org.hamcrest.Matchers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class ElementWithNameTest {

    String html =
            "<html>" +
                "<head></head>" +
                "<body>" +
                    "<div></div>" +
                    "<article></article>" +
                "</body>" +
            "</html>";
    Document document = Jsoup.parse(html);

    @Test
    public void name_matching_based_on_a_string() {
        assertThat(document.select("div").get(0), JSoupMatchers.withName("div"));
        assertThat(document.select("article").get(0), JSoupMatchers.withName("article"));
        assertThat(document.select("div").get(0), not(JSoupMatchers.withName("article")));
    }

    @Test
    public void name_matching_based_on_a_matcher() {
        assertThat(document.select("div").get(0), JSoupMatchers.withName(startsWith("di")));
        assertThat(document.select("article").get(0), not(JSoupMatchers.withName(endsWith("arti"))));
        assertThat(document.select("div").get(0), JSoupMatchers.withName(Matchers.containsString("i")));
    }

}