package cf.dotexe.bdsm.utils.world;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cf.dotexe.bdsm.ClientSupport;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CEntityActionPacket.Action;
import net.minecraft.network.play.client.CPlayerPacket.RotationPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class BlockUtils implements ClientSupport {

	public static List<Block> emptyBlocks;
	public static List<Block> rightclickableBlocks;
	
	static {
		emptyBlocks = Arrays.asList(Blocks.AIR, Blocks.LAVA, Blocks.WATER, Blocks.VINE, Blocks.SNOW, Blocks.TALL_GRASS, Blocks.FIRE);
		rightclickableBlocks = Arrays.asList(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.ACACIA_BUTTON, Blocks.BIRCH_BUTTON, Blocks.CRIMSON_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.OAK_BUTTON, Blocks.POLISHED_BLACKSTONE_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.WARPED_BUTTON, Blocks.STONE_BUTTON, Blocks.COMPARATOR, Blocks.REPEATER, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTE_BLOCK, Blocks.JUKEBOX, Blocks.BEACON, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.RED_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED, Blocks.FURNACE, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
	}
	
    public static Block getBlock(double x, double y, double z) {
        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlockAbovePlayer(PlayerEntity inPlayer, double blocks) {
        blocks += inPlayer.getHeight();
        return getBlockAtPos(new BlockPos(inPlayer.getPosX(), inPlayer.getPosY() + blocks, inPlayer.getPosZ()));
    }

    public static Block getBlockAtPos(BlockPos inBlockPos) {
        return mc.world.getBlockState(inBlockPos).getBlock();
    }

    public static Block getBlockAtPosC(PlayerEntity inPlayer, double x, double y, double z) {
        return getBlockAtPos(new BlockPos(inPlayer.getPosX() - x, inPlayer.getPosY() - y, inPlayer.getPosZ() - z));
    }

    public static float getBlockDistance(float xDiff, float yDiff, float zDiff) {
        return MathHelper.sqrt(((xDiff - 0.5F) * (xDiff - 0.5F)) + ((yDiff - 0.5F) * (yDiff - 0.5F))
                + ((zDiff - 0.5F) * (zDiff - 0.5F)));
    }

    public static BlockPos getBlockPos(BlockPos inBlockPos) {
        return inBlockPos;
    }

    public static BlockPos getBlockPos(double x, double y, double z) {
        return getBlockPos(new BlockPos(x, y, z));
    }

    public static BlockPos getBlockPosUnderPlayer(PlayerEntity inPlayer) {
        return new BlockPos(inPlayer.getPosX(), (inPlayer.getPosY() + (mc.player.getMotion().getY() + 0.1D)) - 1D, inPlayer.getPosZ());
    }

    public static Block getBlockUnderPlayer(PlayerEntity inPlayer) {
        return getBlockAtPos(
                new BlockPos(inPlayer.getPosX(), (inPlayer.getPosY() + (mc.player.getMotion().getY() + 0.1D)) - 1D, inPlayer.getPosZ()));
    }

    public static float getHorizontalPlayerBlockDistance(BlockPos blockPos) {
        float xDiff = (float) (mc.player.getPosX() - blockPos.getX());
        float zDiff = (float) (mc.player.getPosZ() - blockPos.getZ());
        return MathHelper.sqrt(((xDiff - 0.5F) * (xDiff - 0.5F)) + ((zDiff - 0.5F) * (zDiff - 0.5F)));
    }
    
    public static boolean canSeeBlock(BlockPos pos) {
        return mc.player != null && mc.world.rayTraceBlocks(new Vector3d(mc.player.getPosX(), mc.player.getPosY() + mc.player.getEyeHeight(), mc.player.getPosZ()), new Vector3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5), pos, mc.world.getBlockState(pos).getShape(mc.world, pos), mc.world.getBlockState(pos)) == null;
    }
    
    public static void placeCrystalOnBlock(BlockPos pos, Hand hand) {
        BlockRayTraceResult result = mc.world.rayTraceBlocks(new Vector3d(mc.player.getPosX(), mc.player.getPosY() + mc.player.getEyeHeight(), mc.player.getPosZ()), new Vector3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5), pos, mc.world.getBlockState(pos).getShape(mc.world, pos), mc.world.getBlockState(pos));
        if(result != null) mc.player.connection.sendPacket(new CPlayerTryUseItemOnBlockPacket(hand, result));
    }
    
    public static boolean isBlockEmpty(BlockPos pos) {
        try {
            if (emptyBlocks.contains(mc.world.getBlockState(pos).getBlock())) {
                AxisAlignedBB box = new AxisAlignedBB(pos);
                Iterator entityIter = mc.world.getAllEntities().iterator();
                Entity e;
                do {
                    if (!entityIter.hasNext()) {
                        return true;
                    }
                    e = (Entity) entityIter.next();
                } while (!(e instanceof LivingEntity) || !box.intersects(e.getBoundingBox()));

            }
        } catch (Exception ignored) { }
        return false;
    }

    public static void rotatePacket(double x, double y, double z)
    {
        double diffX = x - mc.player.getPosX();
        double diffY = y - (mc.player.getPosY() + (double) mc.player.getEyeHeight());
        double diffZ = z - mc.player.getPosZ();
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));

        mc.player.connection.sendPacket(new RotationPacket(yaw, pitch, mc.player.isOnGround()));
    }
    
    public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack) {
        int old_slot = -1;
        if (slot != mc.player.inventory.currentItem) {
            old_slot = mc.player.inventory.currentItem;
            mc.player.inventory.currentItem = slot;
        }
        Direction[] facings = Direction.values();
        for (Direction f : facings) {
            Block neighborBlock = mc.world.getBlockState(pos.offset(f)).getBlock();
            Vector3d vec = new Vector3d(pos.getX() + 0.5D + (double) f.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) f.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) f.getZOffset() * 0.5D);
            float[] rot = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};
            if (rotate) {
                rotatePacket(vec.x, vec.y, vec.z);
            }
            if (rightclickableBlocks.contains(neighborBlock)) {
            	mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, Action.PRESS_SHIFT_KEY));
            }
            mc.playerController.func_217292_a(mc.player, mc.world, Hand.MAIN_HAND, mc.world.rayTraceBlocks(new Vector3d(mc.player.getPosX(), mc.player.getPosY() + mc.player.getEyeHeight(), mc.player.getPosZ()), new Vector3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5), pos, mc.world.getBlockState(pos).getShape(mc.world, pos), mc.world.getBlockState(pos)));
            if (rightclickableBlocks.contains(neighborBlock)) {
            	mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, Action.RELEASE_SHIFT_KEY));
            }
            if (rotateBack) {
                mc.player.connection.sendPacket(new RotationPacket(rot[0], rot[1], mc.player.isOnGround()));
            }
            mc.player.swingArm(Hand.MAIN_HAND);
            if (old_slot != -1) {
                mc.player.inventory.currentItem = old_slot;
            }
            return true;
        }

        return false;
    }

    
}