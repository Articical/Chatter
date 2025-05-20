package uk.rythefirst.chatter.layouts;

import org.bukkit.entity.Player;

public class SMPPlayer {
	
	private Player player;
	private String nickname;
	private String JoinMsg;
	private String leaveMsg;
	private Double DragonDamage;
	private Double Bounty;
	
	public SMPPlayer(Player player, String nick, String Jmsg, String Lmsg, Double Dragondamage, Double bounty) {
		
		this.setPlayer(player);
		setNickname(nick);
		setJoinMsg(Jmsg);
		setLeaveMsg(Lmsg);
		setDragonDamage(Dragondamage);
		setBounty(bounty);
		
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the joinMsg
	 */
	public String getJoinMsg() {
		return JoinMsg;
	}

	/**
	 * @param joinMsg the joinMsg to set
	 */
	public void setJoinMsg(String joinMsg) {
		JoinMsg = joinMsg;
	}

	/**
	 * @return the leaveMsg
	 */
	public String getLeaveMsg() {
		return leaveMsg;
	}

	/**
	 * @param leaveMsg the leaveMsg to set
	 */
	public void setLeaveMsg(String leaveMsg) {
		this.leaveMsg = leaveMsg;
	}

	/**
	 * @return the dragonDamage
	 */
	public Double getDragonDamage() {
		return DragonDamage;
	}

	/**
	 * @param dragonDamage the dragonDamage to set
	 */
	public void setDragonDamage(Double dragonDamage) {
		DragonDamage = dragonDamage;
	}

	/**
	 * @return the bounty
	 */
	public Double getBounty() {
		return Bounty;
	}

	/**
	 * @param bounty the bounty to set
	 */
	public void setBounty(Double bounty) {
		this.Bounty = bounty;
	}

}
