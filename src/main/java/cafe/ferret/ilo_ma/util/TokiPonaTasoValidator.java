package cafe.ferret.ilo_ma.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokiPonaTasoValidator {
	private static final Pattern PATTERN = Pattern.compile("^(?:[k-nps]?[aeiou]|[jt][aeou]|w[aei])(?:(?:n?[klps]|[mn])[aeiou]|n?[jt][aeou]|n?w[aei])*n?$", Pattern.CASE_INSENSITIVE);
	private static final Pattern EXEMPTIONS = Pattern.compile("^(a*|n*|u*)$");

	/**
	 * Validates a string against a toki pona taso regex.
	 *
	 * @param content The string to validate.
	 * @return True if the string passed the regex.
	 */
	public static boolean validateMessage(String content) {
		// Split each word into its own string
		List<String> wordsWithSymbols = Arrays.stream(content.split(" ")).toList();

		// We don't want to count words that contain only symbols (such as emoticons)
		List<String> alphabeticWords = new ArrayList<>();
		for (String word : wordsWithSymbols) {
			if (word.matches("^[a-zA-Z]*?")) {
				alphabeticWords.add(word);
			}
		}

		// Check each word with the pattern
		for (String word : alphabeticWords) {
			Matcher matcher = PATTERN.matcher(word);
			Matcher exemptions = EXEMPTIONS.matcher(word);

			if (!matcher.find()) {
				if (!exemptions.find()) {
					return false;
				}
			}
		}

		return true;
	}
}
