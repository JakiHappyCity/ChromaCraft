package cc.integration.nei;

import cc.api.ChromaInfusionRecipe;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import java.util.ArrayList;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class ChromaInfusionRecipeHandler extends TemplateRecipeHandler {

    public class ChromaInfusionCraftingPair extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        PositionedStack[] ingred;
        PositionedStack result;
        public int chromaRequired;

        @Override
        public PositionedStack getResult() {
            return null;
        }

        public ChromaInfusionCraftingPair(ChromaInfusionRecipe recipe)
        {
            this.ingred = new PositionedStack[9];
            Object[] craftMatrix = recipe.getInput();
            for(int t = 0; t < craftMatrix.length; ++t)
            {
                if(craftMatrix[t]!=null)
                {
                    this.ingred[t] = new PositionedStack(craftMatrix[t], 25 + t%3*18, 6 + t/3*18);
                }
            }
            this.result = new PositionedStack(recipe.getRecipeOutput(), 119, 23);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(craftMatrix);
            this.chromaRequired = recipe.chromaRequired;
        }

        public void setIngredients(Object[] items)
        {
            for(int i = 0; i < items.length; ++i)
            {
                if(items[i] != null)
                {
                    PositionedStack ps = new PositionedStack(items[i], 25 + i%3*18, 6 + i/3*18);
                    ps.setMaxSize(1);
                    this.ingredients.add(ps);
                }
            }
        }

    }

    @Override
    public String getGuiTexture() {
        return "cc:textures/gui/chroma_infusion.png";
    }

    @Override
    public String getRecipeName() {
        return "Chroma Infusion";
    }

}
