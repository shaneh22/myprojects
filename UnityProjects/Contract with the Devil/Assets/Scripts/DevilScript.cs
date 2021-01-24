using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class DevilScript : MonoBehaviour
{
    public AudioClip coinSound;

    public void Pay5()
    {
        GameManager.instance.playerHealth = 1;
        GameManager.instance.range = 10000000;
        GameManager.instance.speed = 25;
        GameManager.instance.devil = false;
        SoundManager.instance.PlaySingle(coinSound);
        GameManager.instance.StartLevel();
        //SceneManager.LoadScene(1);
    }
    public void Pay3()
    {
        GameManager.instance.playerHealth = 3;
        GameManager.instance.devil = false;
        SoundManager.instance.PlaySingle(coinSound);
        GameManager.instance.StartLevel();
        //SceneManager.LoadScene(1);
    }
    public void Pay1()
    {
        GameManager.instance.playerHealth = 5;
        GameManager.instance.canShoot = false;
        GameManager.instance.devil = false;
        SoundManager.instance.PlaySingle(coinSound);
        GameManager.instance.StartLevel();
        //SceneManager.LoadScene(1);
    }
}
