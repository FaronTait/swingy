package packages.console.controller;

import packages.console.view.Maps;
import packages.models.HeroModel;
import packages.utils.Menus;
import static packages.utils.Colours.*;

public class CliGame
{
    private static HeroModel _hero;
    public static void run(HeroModel hero)
    {
        _hero = hero;
        Menus.printStats(_hero);
        System.out.println(ANSI_YELLOW + "\nYour mission is to reach either side of the map" + ANSI_RESET);
        Maps map = new Maps();
        map.init(_hero);
        map.drawMap(_hero);
    }
}