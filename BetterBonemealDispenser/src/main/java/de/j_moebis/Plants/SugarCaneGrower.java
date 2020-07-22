package de.j_moebis.Plants;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;

import de.j_moebis.BetterBonemealDispenser;

public class SugarCaneGrower implements IGrower {
    final BetterBonemealDispenser plugin;
    private Random random;

    public SugarCaneGrower(BetterBonemealDispenser betterDispenserBonemeal) {
        this.plugin = betterDispenserBonemeal;
        this.random = new Random();
    }

    @Override
    public void Grow(Block sugarCaneBlock, Block dispenserBlock) {
        if (sugarCaneBlock.getBlockData().getMaterial() != Material.SUGAR_CANE) {
            return;
        }
        Boolean canGrow = CanSugarCaneGrow(sugarCaneBlock);
        if (!canGrow) {
            return;
        }
        GrowSugarCane(sugarCaneBlock, dispenserBlock);

        // chance to double proc
        float chance = random.nextFloat();
        float chanceToDoubleProc = (float)plugin.getConfig().getDouble("allowed-plants-grow.sugarcane.proc-chance");
        if (chance < chanceToDoubleProc && CanSugarCaneGrow(sugarCaneBlock)) {
            GrowSugarCane(sugarCaneBlock, dispenserBlock);
        }
        RemoveBonemealFromDispenser(dispenserBlock);
    }

    protected void GrowSugarCane(Block sugarCaneBlock, Block dispenserBlock) {
        Block lowestBlock = GetLowestSugarCaneBlock(sugarCaneBlock);
        Boolean alreadyGrow = false;
        if (!lowestBlock.equals(sugarCaneBlock)) {
            sugarCaneBlock.getRelative(BlockFace.UP).setType(Material.SUGAR_CANE);
            alreadyGrow = true;
        }
        if (!alreadyGrow && sugarCaneBlock.getRelative(BlockFace.UP).getBlockData().getMaterial() == Material.AIR) {
            sugarCaneBlock.getRelative(BlockFace.UP).setType(Material.SUGAR_CANE);
            alreadyGrow = true;
        }
        if (!alreadyGrow && sugarCaneBlock.getRelative(BlockFace.UP, 2).getBlockData().getMaterial() == Material.AIR) {
            sugarCaneBlock.getRelative(BlockFace.UP, 2).setType(Material.SUGAR_CANE);
        }
    }

    protected Boolean CanSugarCaneGrow(Block sugarCaneBlock) {
        if (sugarCaneBlock.getBlockData().getMaterial() != Material.SUGAR_CANE) {
            return false;
        }
        Block lowestSugarCaneBlock = GetLowestSugarCaneBlock(sugarCaneBlock);
        Block aboveBlock = lowestSugarCaneBlock.getRelative(BlockFace.UP);

        if (aboveBlock.getBlockData().getMaterial() == Material.AIR) {
            return true;
        }
        if (aboveBlock.getBlockData().getMaterial() != Material.SUGAR_CANE) {
            return false;
        }

        Block secondAboveBlock = aboveBlock.getRelative(BlockFace.UP);
        if (secondAboveBlock.getBlockData().getMaterial() == Material.AIR) {
            return true;
        }

        return false;
    }

    protected Block GetLowestSugarCaneBlock(Block sugarCaneBlock) {
        if (sugarCaneBlock.getBlockData().getMaterial() != Material.SUGAR_CANE) {
            return null;
        }

        Block underneathBlock = sugarCaneBlock.getRelative(BlockFace.DOWN);
        if (underneathBlock.getBlockData().getMaterial() != Material.SUGAR_CANE) {
            return sugarCaneBlock;
        }

        Block nextUnderneathBlock = underneathBlock.getRelative(BlockFace.DOWN);
        if (nextUnderneathBlock.getBlockData().getMaterial() != Material.SUGAR_CANE) {
            return underneathBlock;
        }

        return nextUnderneathBlock;
    }

    protected void RemoveBonemealFromDispenser(Block dispenserBlock) {
        Dispenser dispenser = (Dispenser) dispenserBlock.getState();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            @Override
            public void run(){
                dispenser.getInventory().removeItem(new ItemStack(Material.BONE_MEAL, 1));
            }
        }, 1L);
    }
}