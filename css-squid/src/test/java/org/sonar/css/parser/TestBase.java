package org.sonar.css.parser;

import com.google.common.base.Joiner;

public class TestBase {

	protected static String code(String... lines) {
		return Joiner.on("\n").join(lines);
	}

}
