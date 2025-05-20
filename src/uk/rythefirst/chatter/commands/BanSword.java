package uk.rythefirst.chatter.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BanSword implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("chatter.staff.bsword")) {

			ItemStack is = new ItemStack(Material.DIAMOND_SWORD, 1);

			ItemMeta im = is.getItemMeta();

			String name = ChatColor.translateAlternateColorCodes('&', "&4&lBan Sword");

			im.setDisplayName(name);

			im.addEnchant(Enchantment.SHARPNESS, 10, true);
			im.addEnchant(Enchantment.UNBREAKING, 5, true);
			im.addEnchant(Enchantment.FIRE_ASPECT, 1, true);

			List<String> l = new ArrayList<String>();

			l.add(ChatColor.DARK_RED + "BAN THEM.");
			l.add(ChatColor.DARK_RED + "THOU HATH SINNED");

			im.setLore(l);

			is.setItemMeta(im);

			if (sender instanceof Player) {
				Player p = (Player) sender;

				p.getInventory().setItemInHand(is);
			}

			return true;

		}

		return false;

	}

}
