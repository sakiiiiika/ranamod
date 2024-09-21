package nodomain.sakiika.ranamod.datagen.loot;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import nodomain.sakiika.ranamod.entity.ModEntities;

import java.util.stream.Stream;

import static net.minecraft.world.level.storage.loot.LootPool.lootPool;

public class ModEntityLootTableProvider extends EntityLootSubProvider {
    private HolderLookup.Provider provider;
    public ModEntityLootTableProvider(HolderLookup.Provider provider) {
        super(FeatureFlags.REGISTRY.allFlags(), provider);
        this.provider = provider;
    }

    @Override
    public void generate() {
        //Rana drops...
        this.add(ModEntities.RANA.get(), LootTable.lootTable().withPool(
                //apples
                lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.APPLE)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(1.0F, 3.0F) //amount of 1-3
                                ))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider,
                                        UniformGenerator.between(0.0F, 1.0F) //increasing amount by looting enchant
                                ))
                                .setWeight(975) //97.5% chance to drop
                        )
                        //golden apple
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                .apply(SetItemCountFunction.setCount(
                                        ConstantValue.exactly(1.0F)
                                ))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider,
                                        UniformGenerator.between(0.0F, 1.0F)
                                ))
                                .setWeight(20) //2% chance to drop
                        )
                        //enchanted golden apple
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                .apply(SetItemCountFunction.setCount(
                                        ConstantValue.exactly(1.0F)
                                ))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider,
                                        UniformGenerator.between(0.0F, 1.0F)
                                ))
                                .setWeight(5) //0.5% chance to drop
                        )
        ));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntities.ENTITY_TYPES.getEntries().stream().map(Holder::value);
    }
}
