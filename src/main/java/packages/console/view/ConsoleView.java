package packages.console.view;

import java.util.List;
import java.util.Scanner;

import packages.models.HeroModel;
import packages.console.controller.CliGame;
import packages.enums.CharacterType;
import packages.gui.controllers.*;
import packages.gui.views.*;
import packages.utils.HeroFactory;
import packages.utils.Menus;
import packages.utils.WriteFile;
import packages.utils.readFile;
import static packages.utils.Colours.*;

public class ConsoleView
{
    public static List<HeroModel> heroList;
    public static HeroModel _hero;

    public static void start()
    {

        try {
            heroList = readFile.simulateFile();
        }
        catch (Exception e)
        {
            System.out.println("Unable to read from file");
        }
        Scanner read = new Scanner(System.in);
        Menus.menu();
        while (read.hasNextLine())
        {

            if (read.hasNextInt())
                {
                    int n = read.nextInt();

                    switch (n) {
                        case 1:
                            createHero();
                            break;
                        case 2:
                            existingHero();
                            break;
                        case 3:
                            WelcomeView view = new WelcomeView();
                            view.setVisible(true);
                            new WelcomeController(view);
                            break;
                        case 4:
                            System.exit(0);
                        default:
                            System.out.println(ANSI_RED + "\nInvalid selection, please select a valid option\n" + ANSI_RESET);
                            break;
                    }

                }
                else
                {
                    System.out.println(ANSI_RED + "Only numeric values are accepted, please select a valid numeric option" + ANSI_RESET);
                    start();
                }
        }
        read.close();
    }

    public static void createHero()
    {
        Scanner reader = new Scanner(System.in);
        Menus.heroMenu();

        while (reader.hasNextLine())
        {
            if (reader.hasNextInt())
            {
                int n = reader.nextInt();
                switch (n) {
                    case 0:
                        start();
                        break;
                    case 1:
                        declareHero(CharacterType.knight);
                        break;
                    case 2:
                        declareHero(CharacterType.warrior);
                        break;
                    case 3:
                        declareHero(CharacterType.elf);
                        break;
                    case 4:
                        declareHero(CharacterType.hunter);
                        break;
                    case 5:
                        CreateHeroView view = new CreateHeroView();
                        view.setVisible(true);
                        new CreateHeroController(view, heroList);
                        break;
                    default:
                        System.out.println(ANSI_RED + "\nInvalid selection, please select a valid option\n" + ANSI_RESET);
                        break;
                }
            }
            else
            {
                System.out.println(ANSI_RED + "Only numeric values are accepted, please select a valid numeric option" + ANSI_RESET);
                createHero();
            }
        }
        reader.close();
    }

    public  static void declareHero(CharacterType htype)
    {
        System.out.print(ANSI_CYAN + "\nGive your " + htype + " a name: " + ANSI_RESET);
        Scanner reader = new Scanner(System.in);
        String name = reader.next();
        _hero = HeroFactory.newHero(name, htype.toString(), null);
        WriteFile.writeToFile(_hero);
        CliGame.run(_hero);
        backToStart();
    }


    public  static void existingHero() {
        boolean isMatch = false;
        System.out.println(ANSI_CYAN + "\nSelect a character or switch to GUI\n" + ANSI_RESET);
        int a = heroList.size();
        if (a == 0)
        {
            System.out.println(ANSI_RED + "No hero's available" + ANSI_RESET);
        }
        else {
            for (int index = 0; index < a; index++) {
                Menus.printStats(heroList.get(index));
            }
        }
        System.out.println(ANSI_CYAN + "\nType in the name of Hero you would like : " + ANSI_RESET);
        Scanner reader = new Scanner(System.in);
        String choice = reader.nextLine();
        int i = 0;
            for (HeroModel __hero : heroList)
            {
                if (choice.equalsIgnoreCase(heroList.get(i).getName()))
                {
                    CliGame.run(heroList.get(i));
                    isMatch = true;
                    backToStart();
                }
                else if (choice.toLowerCase().equals("gui"))
                {
                    SelectHeroView view = new SelectHeroView(heroList);
                    view.setVisible(true);
                    new SelectHeroController(view, heroList);
                    break;
                }
                else if(!(choice.toLowerCase().equals(heroList.get(i).getName().toLowerCase())))
                    isMatch = false;
                i++;
            }
            if (!isMatch)
            {
                System.out.println(ANSI_RED +  "Unfortunately the selected hero is non-existent. Redirecting back to Start.\n" + ANSI_RESET);
                start();
            }
        }

    public static void backToStart()
   {
       System.out.println("0. Back");
       Scanner reader = new Scanner(System.in);
       while (reader.hasNextLine())
       {
           int n = reader.nextInt();
               switch (n)
               {
                   case 0:
                       start();
                       break;
               }
       }
   }

}