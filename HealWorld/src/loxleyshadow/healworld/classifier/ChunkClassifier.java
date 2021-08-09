package loxleyshadow.healworld.classifier;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Material;

import loxleyshadow.healworld.HealWorldMain;

public class ChunkClassifier {

	private static int devThreshold;
	
	public ChunkClassifier(int threshold) {
		devThreshold = threshold;
	}
	
	public boolean isDeveloped(Chunk chunk) {
		List<String> playerBlocks = HealWorldMain.getInstance().getConfig().getStringList("playerBlocks");
		int devCount = 0;
        
		for (String str : playerBlocks) {
			if (chunk.contains(Material.getMaterial(str).createBlockData())) {
				devCount++;
			}
		}

		return devCount >= devThreshold;
	}
}
