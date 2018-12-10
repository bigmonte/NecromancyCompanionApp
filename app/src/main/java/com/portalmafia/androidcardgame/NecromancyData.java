package com.portalmafia.androidcardgame;

import android.app.Application;

public class NecromancyData extends Application {

    private String Username;
    private String Password;

    private int BestRound;
    private int EnemiesKilled;
    private int MoneyEarned;

    private int SkillDamage;
    private int SkillVelocity;
    private int SkillExtraHealth;
    private int SkillReloadTime;


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

    public int GetBestRound()
    {
        return BestRound;
    }

    public void SetBestRound(int bestRoundToSet)
    {
        BestRound = bestRoundToSet;
    }

    public int GetEnemiesKilled()
    {
        return EnemiesKilled;
    }

    public void SetEnemiesKilled(int enemiesKilledToSet)
    {
        EnemiesKilled = enemiesKilledToSet;

    }

    public int GetMoneyEarned()
    {
        return MoneyEarned;
    }

    public void SetMoneyEarned(int moneyEarnedToSet)
    {
        MoneyEarned = moneyEarnedToSet;
    }

    public int getCoins()
    {
        return Coins;
    }

    public void SetCoins(int coins)
    {
        Coins = coins;
    }

    public int getSkillDamage()
    {
        return SkillDamage;
    }

    public void SetSkillDamage(int points)
    {
        SkillDamage = points;
    }

    public int getSkillVelocity()
    {
        return SkillVelocity;
    }

    public void SetSkillVelocity(int points)
    {
        SkillVelocity = points;
    }

    public int getSkillExtraHealth()
    {
        return SkillExtraHealth;
    }

    public void SetSkillExtraHealth(int points)
    {
        SkillExtraHealth = points;
    }

    public int getSkillReloadTime()
    {
        return SkillExtraHealth;
    }

    public void SetSkillReloadTime(int points)
    {
        SkillReloadTime = points;
    }
}
