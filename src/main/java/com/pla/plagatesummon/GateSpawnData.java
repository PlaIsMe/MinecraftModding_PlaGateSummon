package com.pla.plagatesummon;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class GateSpawnData extends SavedData {
    private static final String DATA_NAME = "gate_spawn_data";

    public int nextSpawnTick = -1;
    public boolean shouldSpawnToday = false;
    public BlockPos spawnPos = null;
    public BlockPos oldSpawnPos = null;
    public boolean isPromptPlayer = false;
    public String mainMessage = "";
    public int hexColor = 0;
    public String randomGate = "";
    public String subMessage = "";
    public String waypointName = "";
    public boolean skippedToday = false;

    public static GateSpawnData load(CompoundTag nbt) {
        GateSpawnData data = new GateSpawnData();
        data.nextSpawnTick = nbt.getInt("NextSpawnTick");
        data.shouldSpawnToday = nbt.getBoolean("ShouldSpawnToday");
        if (nbt.contains("SpawnPos")) {
            int[] pos = nbt.getIntArray("SpawnPos");
            data.spawnPos = new BlockPos(pos[0], pos[1], pos[2]);
        }
        if (nbt.contains("OldSpawnPos")) {
            int[] pos = nbt.getIntArray("OldSpawnPos");
            data.spawnPos = new BlockPos(pos[0], pos[1], pos[2]);
        }
        data.isPromptPlayer = nbt.getBoolean("IsPromptPlayer");
        data.mainMessage = nbt.getString("MainMessage");
        data.hexColor = nbt.getInt("HexColor");
        data.randomGate = nbt.getString("RandomGate");
        data.subMessage = nbt.getString("SubMessage");
        data.waypointName = nbt.getString("WaypointName");
        data.skippedToday = nbt.getBoolean("SkippedToday");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putInt("NextSpawnTick", nextSpawnTick);
        nbt.putBoolean("ShouldSpawnToday", shouldSpawnToday);
        if (spawnPos != null) {
            nbt.putIntArray("SpawnPos", new int[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()});
        }
        if (oldSpawnPos != null) {
            nbt.putIntArray("OldSpawnPos", new int[]{oldSpawnPos.getX(), oldSpawnPos.getY(), oldSpawnPos.getZ()});
        }
        nbt.putBoolean("IsPromptPlayer", isPromptPlayer);
        nbt.putString("MainMessage", mainMessage);
        nbt.putInt("HexColor", hexColor);
        nbt.putString("RandomGate", randomGate);
        nbt.putString("SubMessage", subMessage);
        nbt.putString("WaypointName", waypointName);
        nbt.putBoolean("SkippedToday", skippedToday);
        return nbt;
    }

    public static GateSpawnData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(GateSpawnData::load, GateSpawnData::new, DATA_NAME);
    }
}
