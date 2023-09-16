package uk.rythefirst.chatter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import uk.rythefirst.chatter.commands.Chreload;
import uk.rythefirst.chatter.commands.Jmessage;
import uk.rythefirst.chatter.commands.Lmessage;
import uk.rythefirst.chatter.commands.Nick;
import uk.rythefirst.chatter.liseners.PlayerChat;
import uk.rythefirst.chatter.liseners.PlayerJoin;
import uk.rythefirst.chatter.liseners.PlayerLeave;
import uk.rythefirst.chatter.managers.ConfigManager;
import uk.rythefirst.chatter.managers.JmessageMgr;
import uk.rythefirst.chatter.managers.LmessageMgr;
import uk.rythefirst.chatter.managers.NickNameMgr;
import uk.rythefirst.chatter.managers.Tablist;
import uk.rythefirst.chatter.tabcomplete.JMessageTab;
import uk.rythefirst.chatter.tabcomplete.LMessageTab;
import uk.rythefirst.chatter.tabcomplete.NickTab;
import uk.rythefirst.chatter.util.TpsCalc;

public class Main extends JavaPlugin {

	public static Plugin instance;
	public static LuckPerms lpermsAPI;

	public static Cache cache;

	public static Tablist tabMgr;
	public static TpsCalc tpscalc;

	public static ConfigManager configManager;
	public static NickNameMgr NickMgr;
	public static JmessageMgr JoinMsgManager;
	public static LmessageMgr LeaveMessageMgr;

	@Override
	public void onEnable() {
		instance = this;
		lpermsAPI = LuckPermsProvider.get();
		cache = new Cache();
		tpscalc = new TpsCalc();
		tabMgr = new Tablist();
		configManager = new ConfigManager();
		NickMgr = new NickNameMgr();
		LeaveMessageMgr = new LmessageMgr();
		JoinMsgManager = new JmessageMgr();

		configManager.loadJoinMessage();
		configManager.loadLeaveMessage();
		configManager.loadNicks();

		// Currently unused as it wasn't quite correct
		// tpscalc.startCalc();

		Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);

		this.getCommand("chreload").setExecutor(new Chreload());

		this.getCommand("nick").setExecutor(new Nick());
		this.getCommand("nickname").setExecutor(new Nick());
		this.getCommand("nick").setTabCompleter(new NickTab());
		this.getCommand("nickname").setTabCompleter(new NickTab());

		this.getCommand("jmessage").setExecutor(new Jmessage());
		this.getCommand("joinmessage").setExecutor(new Jmessage());
		this.getCommand("jmessage").setTabCompleter(new JMessageTab());
		this.getCommand("joinmessage").setTabCompleter(new JMessageTab());

		this.getCommand("lmessage").setExecutor(new Lmessage());
		this.getCommand("leavemessage").setExecutor(new Lmessage());
		this.getCommand("lmessage").setTabCompleter(new LMessageTab());
		this.getCommand("leavemessage").setTabCompleter(new LMessageTab());

		for (Player player : Bukkit.getOnlinePlayers()) {
			NickMgr.setNickName(player, NickMgr.getNickName(player));
		}
	}

	@Override
	public void onDisable() {
		configManager.saveJoinMessage();
		configManager.saveLeaveMessage();
		configManager.saveNicks();
		tpscalc.stopCalc();
		tabMgr.stopTimer();
	}

}
