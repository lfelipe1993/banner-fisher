package pescador;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Utils {

	public static Timestamp localDateTimeToTimeStamp(LocalDateTime dttm) {
		return Timestamp.valueOf(dttm);
	}

	public static LocalDateTime timeStampToLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}

	public static String firstLetterToUpperCase(String letter) {
		// second substring contains remaining letters
		String firstLetter = letter.substring(0, 1);
		String remainingLetters = letter.substring(1, letter.length());

		// change the first letter to uppercase
		firstLetter = firstLetter.toUpperCase();

		// join the two substrings
		return firstLetter + remainingLetters;
	}
}
