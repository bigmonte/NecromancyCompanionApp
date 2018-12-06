package com.portalmafia.androidcardgame;

import android.app.Application;

public class NecromancyData extends Application {

    private String Username;
    private String Password;
    private int Coins;

    public void onCreate()
    {
        super.onCreate();

        AppManager.initInstance();
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
