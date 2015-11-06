package cc.client.gui;

import cc.utils.cfg.Config;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class GuiModConfiguration extends GuiConfig {

    @SuppressWarnings("unchecked")
    public GuiModConfiguration(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), "cc", false,false, GuiConfig.getAbridgedConfigPath(Config.config.toString()));
        // TODO Auto-generated constructor stub
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static List getConfigElements()
    {
        List list = new ArrayList();
        list.addAll((new ConfigElement(Config.config.getCategory("misc"))).getChildElements());
        list.addAll((new ConfigElement(Config.config.getCategory("general"))).getChildElements());
        list.addAll((new ConfigElement(Config.config.getCategory("worldgen"))).getChildElements());
        list.addAll((new ConfigElement(Config.config.getCategory("difficulty"))).getChildElements());
        list.addAll((new ConfigElement(Config.config.getCategory("tileentities"))).getChildElements());
        return list;
    }
}