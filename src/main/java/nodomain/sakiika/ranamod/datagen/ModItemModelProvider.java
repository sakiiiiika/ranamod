package nodomain.sakiika.ranamod.datagen;

import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nodomain.sakiika.ranamod.RanaMod;
import nodomain.sakiika.ranamod.item.ModItems;
import net.minecraft.data.PackOutput;



public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RanaMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.RANA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    /*
    public void buttonItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(RanaMod.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RanaMod.MOD_ID,"block/" + item.getId().getPath()));
    }
    */
}
