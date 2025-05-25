package uk.rythefirst.chatter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import uk.rythefirst.chatter.bounties.BHolder;
import uk.rythefirst.chatter.bounties.DataHandler;
import uk.rythefirst.chatter.commands.BanSword;
import uk.rythefirst.chatter.commands.BountiesSave;
import uk.rythefirst.chatter.commands.BountyCMD;
import uk.rythefirst.chatter.commands.Cast;
import uk.rythefirst.chatter.commands.Chreload;
import uk.rythefirst.chatter.commands.ClearChat;
import uk.rythefirst.chatter.commands.Help;
import uk.rythefirst.chatter.commands.Jmessage;
import uk.rythefirst.chatter.commands.Lmessage;
import uk.rythefirst.chatter.commands.Mding;
import uk.rythefirst.chatter.commands.ModHelp;
import uk.rythefirst.chatter.commands.ModMode;
import uk.rythefirst.chatter.commands.Nick;
import uk.rythefirst.chatter.commands.Removebounty;
import uk.rythefirst.chatter.commands.RussianRoulette;
import uk.rythefirst.chatter.commands.SellXP;
import uk.rythefirst.chatter.commands.SetBounty;
import uk.rythefirst.chatter.commands.ToggleChat;
import uk.rythefirst.chatter.commands.Togglemention;
import uk.rythefirst.chatter.commands.Twitch;
import uk.rythefirst.chatter.layouts.ChatState;
import uk.rythefirst.chatter.liseners.CommandPreProc;
import uk.rythefirst.chatter.liseners.Dragon;
import uk.rythefirst.chatter.liseners.Headshots;
import uk.rythefirst.chatter.liseners.InventoryClick;
import uk.rythefirst.chatter.liseners.PlayerChat;
import uk.rythefirst.chatter.liseners.PlayerDamagePlayer;
import uk.rythefirst.chatter.liseners.PlayerDeath;
import uk.rythefirst.chatter.liseners.PlayerJoin;
import uk.rythefirst.chatter.liseners.PlayerLeave;
import uk.rythefirst.chatter.managers.ConfigManager;
import uk.rythefirst.chatter.managers.HiddenManager;
import uk.rythefirst.chatter.managers.JmessageMgr;
import uk.rythefirst.chatter.managers.LmessageMgr;
import uk.rythefirst.chatter.managers.MentionHandler;
import uk.rythefirst.chatter.managers.NickNameMgr;
import uk.rythefirst.chatter.managers.PlayerFileManager;
import uk.rythefirst.chatter.managers.PvpTimerMgr;
import uk.rythefirst.chatter.managers.Tablist;
import uk.rythefirst.chatter.speedtyper.ChatWordGame;
import uk.rythefirst.chatter.tabcomplete.JMessageTab;
import uk.rythefirst.chatter.tabcomplete.LMessageTab;
import uk.rythefirst.chatter.tabcomplete.NickTab;
import uk.rythefirst.chatter.twitch.TwitchAPI;
import uk.rythefirst.chatter.util.SaveWorker;
import uk.rythefirst.chatter.util.TpsCalc;

public class Main extends JavaPlugin {

	public static Plugin instance;
	public static LuckPerms lpermsAPI;

	public static Cache cache;
	
	public static ChatWordGame cwg;
	
	public static TwitchAPI tapi;
	
	public static ChatState chatState;
	
	private static Economy econ;
	private static Permission perms;
	private static Chat chat;

	public static Tablist tabMgr;
	public static TpsCalc tpscalc;
	public static SaveWorker sworker;

	public static ConfigManager configManager;
	public static PlayerFileManager PDataMgr;
	public static NickNameMgr NickMgr;
	public static JmessageMgr JoinMsgManager;
	public static LmessageMgr LeaveMessageMgr;
	public static MentionHandler mHandler;
	public static HiddenManager hManager;
	public static PvpTimerMgr pvpManager;
	
	public static Double xpValue;
	
	public static Boolean ChatGameAlt = true;

	@Override
	public void onEnable() {
		
		instance = this;
		lpermsAPI = LuckPermsProvider.get();
		cache = new Cache();
		tpscalc = new TpsCalc(this);
		tabMgr = new Tablist();
		configManager = new ConfigManager();
		NickMgr = new NickNameMgr();
		LeaveMessageMgr = new LmessageMgr();
		JoinMsgManager = new JmessageMgr();
		pvpManager = new PvpTimerMgr();
		chatState = ChatState.ENABLED;
		PDataMgr = new PlayerFileManager(this);
		
		cwg = new ChatWordGame();
		mHandler = new MentionHandler();
		tapi = new TwitchAPI();
		hManager = new HiddenManager();
		sworker = new SaveWorker();
		
		tapi.StartWatcher();
		hManager.runActionTimer();
		
		DataHandler.loadBounties();
		BHolder.loadInv();

		configManager.loadJoinMessage();
		configManager.loadLeaveMessage();
		configManager.loadNicks();

		// Currently unused as it wasn't quite correct
		// tpscalc.startCalc();

		Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDamagePlayer(), this);
		Bukkit.getPluginManager().registerEvents(new CommandPreProc(), this);
		Bukkit.getPluginManager().registerEvents(new Dragon(), this);
		Bukkit.getPluginManager().registerEvents(new Headshots(), this);

		this.getCommand("chreload").setExecutor(new Chreload());
		
		this.getCommand("modmode").setExecutor(new ModMode());

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
		
		this.getCommand("mding").setExecutor(new Mding());
		this.getCommand("togglemention").setExecutor(new Togglemention());
		this.getCommand("bounties").setExecutor(new BountyCMD());
		this.getCommand("bountiessave").setExecutor(new BountiesSave());
		this.getCommand("removebounty").setExecutor(new Removebounty());
		this.getCommand("setbounty").setExecutor(new SetBounty());
		this.getCommand("russianroulette").setExecutor(new RussianRoulette());
		this.getCommand("cast").setExecutor(new Cast());
		this.getCommand("twitch").setExecutor(new Twitch());
		
		this.getCommand("sellxp").setExecutor(new SellXP());
		
		this.getCommand("bansword").setExecutor(new BanSword());
		
		this.getCommand("clearchat").setExecutor(new ClearChat());
		this.getCommand("togglechat").setExecutor(new ToggleChat());
		
		this.getCommand("help").setExecutor(new Help());
		this.getCommand("modhelp").setExecutor(new ModHelp());

		for (Player player : Bukkit.getOnlinePlayers()) {
			NickMgr.setNickName(player, NickMgr.getNickName(player));
		}
		
		cwg.runGame();
		
		mHandler.loadDisables();
		
		// Setup Vault
		if (!setupEconomy()) {
			this.getLogger().severe("Disabled due to no Vault dependency found!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		this.setupPermissions();
		this.setupChat();
		
		xpValue = configManager.getXPValue();
		
	}

	@Override
	public void onDisable() {
		configManager.saveJoinMessage(true);
		configManager.saveLeaveMessage(true);
		configManager.saveNicks(true);
		configManager.saveDragonDamage(true);
		tabMgr.stopTimer();
		DataHandler.saveBounties(true);
		mHandler.saveDisables();
		sworker.stopWorker();
	}
	
	private boolean setupEconomy() {
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public static Economy getEconomy() {
		return econ;
	}

	public static Permission getPermissions() {
		return perms;
	}

	public static Chat getChat() {
		return chat;
	}

}
