package com.pla.plagatesummon;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkImpl;
import dev.ftb.mods.ftbchunks.data.ChunkTeamDataImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class ClaimChunkHelper {
    private static ClaimChunkHelper instance;
    private final ClaimedChunkManagerImpl claimedChunkManager;

    public ClaimChunkHelper(ClaimedChunkManagerImpl claimedChunkManager) throws CommandSyntaxException {
        this.claimedChunkManager = claimedChunkManager;
    }

    public static synchronized ClaimChunkHelper getInstance(MinecraftServer server) throws CommandSyntaxException {
        if (instance == null) {
            instance = new ClaimChunkHelper((ClaimedChunkManagerImpl) FTBChunksAPI.api().getManager());
        }
        return instance;
    }

    public void claimChunk(CommandSourceStack source, ServerPlayer player, BlockPos pos) throws CommandSyntaxException {
        ChunkPos chunkPos = new ChunkPos(pos);
        ResourceKey<Level> dimension = player.level().dimension();
        ChunkDimPos chunkDimPos = new ChunkDimPos(dimension, chunkPos.x, chunkPos.z);

        ChunkTeamDataImpl teamData = claimedChunkManager.getOrCreateData(player);
        teamData.claim(source, chunkDimPos, false);
        teamData.forceLoad(source, chunkDimPos, false);
    }

    public void unClaimChunk(CommandSourceStack source, ServerPlayer player, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        ResourceKey<Level> dimension = player.level().dimension();
        ChunkDimPos chunkDimPos = new ChunkDimPos(dimension, chunkPos.x, chunkPos.z);

        ChunkTeamDataImpl teamData = claimedChunkManager.getOrCreateData(player);
        teamData.unclaim(source, chunkDimPos, false);
    }
}
