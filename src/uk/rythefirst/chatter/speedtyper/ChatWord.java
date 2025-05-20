package uk.rythefirst.chatter.speedtyper;

import javax.annotation.Nullable;

public class ChatWord {

	private String wordStr;
	private Boolean guessed;

	public @Nullable ChatWord(String word) {
		wordStr = word;
		guessed = false;
	}

	public String getWord() {
		return wordStr;
	}

	public Boolean isGuessed() {
		return guessed;
	}

	public void setGuessed(Boolean setting) {
		guessed = setting;
	}

}
