package uk.rythefirst.chatter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Cache {

	public String prefix = new String();
	public String format;
	public String defaultLeaveMsg;
	public String defaultJoinMsg;
	public Boolean prefixUsed;
	public Boolean setTabPrefix;
	public Boolean setTabNick;
	public Boolean tabEnabled;
	public TreeMap<String, String> nickMap = new TreeMap<String, String>();
	public TreeMap<String, String> jMessageMap = new TreeMap<String, String>();
	public TreeMap<String, String> lMessageMap = new TreeMap<String, String>();
	public TreeMap<String, Double> DragonDamageMap = new TreeMap<String, Double>();

	public List<String> tabHeader = new ArrayList<String>();
	public List<String> tabFooter = new ArrayList<String>();

	public Boolean isPrefixUsedInFormat() {
		return prefix.contains("<lp_prefix>");
	}

}
