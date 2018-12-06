package com.portalmafia.androidcardgame;

public class AppManager {
    private static AppManager appManagerInstance;
    private int Coins;
    private String Username;
    private String Password;



    public static void initInstance() {
        if(appManagerInstance == null)
        {
            appManagerInstance = new AppManager();
        }
    }

    public static AppManager getInstance()
    {
        if(appManagerInstance == null)
        {
            appManagerInstance = new AppManager();
        }
        return appManagerInstance;
    }

    private AppManager()
    {
        Coins = 30;
        Username = null;
        Password = null;
    }

    public String GetUsername()
    {
        return Username;
    }

    public void SetUsername(String username)
    {
        Username = username;
    }

    public String GetPassword()
    {
        return Password;
    }

    public void SetPassword(String password)
    {
        Password = password;
    }

    public int getCoins()
    {
        return Coins;
    }

    public void SetCoins(int coins)
    {
        Coins = coins;
    }


}
