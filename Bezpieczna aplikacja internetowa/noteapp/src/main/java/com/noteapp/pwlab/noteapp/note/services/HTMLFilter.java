package com.noteapp.pwlab.noteapp.note.services;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class HTMLFilter {
    PolicyFactory policy;

    public HTMLFilter() {
		policy = new HtmlPolicyBuilder()
				.allowStandardUrlProtocols()
				.allowElements("a", "p", "div", "i", "b", "em", "blockquote", "tt", "strong",
						"br", "ul", "ol", "li", "quote", "ecode", "img", "h1", "h2", "h3", "h4", "h5", "body")
				.allowAttributes("href").matching(URL).onElements("a")
				.allowAttributes("src").matching(URL).onElements("img")
				.toFactory();
	}

	static final Pattern URL = Pattern.compile("(.*?)");

	public String sanitize(String html) {
		return policy.sanitize(html);
	}
}
