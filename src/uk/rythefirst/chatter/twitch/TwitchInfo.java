package uk.rythefirst.chatter.twitch;

import javax.annotation.Nullable;

public class TwitchInfo {

	private String username;

	private String title;

	private String game;

	private Boolean islive;

	private Integer viewercount;

	public TwitchInfo(@Nullable String Username, @Nullable String Title, @Nullable String Game, Boolean Live,
			Integer Viewercount) {

		username = Username;
		title = Title;
		game = Game;
		islive = Live;
		viewercount = Viewercount;

	}

	public String getUsername() {
		return username;
	}

	public String getTitle() {
		return title;
	}

	public String getGame() {
		return game;
	}

	public Integer getViewerCount() {
		return viewercount;
	}

	public Boolean isLive() {
		return islive;
	}

}
