package loxleyshadow.healworld.event;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import loxleyshadow.healworld.HealWorldMain;


public class WorldDamagedListener implements Listener {

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (HealWorldMain.getInstance().getConfig().getStringList("worlds").contains(block.getWorld().getName()))
			healWorld(block, block.getType());
	}

	public static void healWorld(Block block, Material type) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(HealWorldMain.getInstance(), new Runnable() {

			public void run() {
				if (!HealWorldMain.classifier.isDeveloped(block.getChunk())) {

					if (HealWorldMain.getInstance().getConfig().getStringList("naturalBlocks").contains(type.toString())) {
						block.setType(type);
						block.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 7);
					}

					else if (HealWorldMain.getInstance().getConfig().getStringList("rareBlocks").contains(type.toString())) {
						block.setType(Material.STONE);
					}

				}
			}

		}, (long) HealWorldMain.getInstance().getConfig().getInt("regenerationTime"));
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		Iterator<Block> blocks = e.blockList().iterator();

		while(true) {
			int delay;

			if (!blocks.hasNext()) {
				return;
			}

			Block b = blocks.next();
			BlockState state = b.getState();
			World world = b.getWorld();
			Location loc = new Location(world, (double)b.getX(), (double)(b.getY() + 1), (double)b.getZ());

			delay = HealWorldMain.getInstance().getConfig().getInt("regenerationTime");
			if (b.getType() == Material.SAND || b.getType() == Material.GRAVEL) {
				++delay;
			}

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HealWorldMain.getInstance(), new Runnable() {
				public void run() {
					if (!HealWorldMain.classifier.isDeveloped(b.getChunk())) {
						state.update(true, false);
						world.playEffect(loc, Effect.SMOKE, 7);
					}
				}
			}, (long)delay);

		}
		
	}
}
