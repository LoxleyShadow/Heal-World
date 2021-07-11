package loxleyshadow.healworld;

import org.bukkit.plugin.java.JavaPlugin;

import loxleyshadow.healworld.classifier.ChunkClassifier;
import loxleyshadow.healworld.event.WorldDamagedListener;

public class HealWorldMain extends JavaPlugin {

	private static HealWorldMain plugin;
	public static ChunkClassifier classifier;
	
	public static HealWorldMain getInstance() {
		return plugin;
	}

	public void onEnable() {
		plugin = this;
		classifier = new ChunkClassifier(1);
		
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(new WorldDamagedListener(), this);
		this.getLogger().info("HealWorld has been enabled!");
	}

	public void onDisable() {
		this.getLogger().info("HealWorld has been disabled!");
	}
}
