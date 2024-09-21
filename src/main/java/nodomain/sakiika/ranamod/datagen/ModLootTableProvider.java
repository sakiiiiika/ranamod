package nodomain.sakiika.ranamod.datagen;

import net.minecraft.core.HolderLookup;
import nodomain.sakiika.ranamod.datagen.loot.ModEntityLootTableProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new, LootContextParamSets.ENTITY)
        ), provider);
    }
}
