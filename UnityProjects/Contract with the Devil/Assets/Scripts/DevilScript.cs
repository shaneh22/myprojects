using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class DevilScript : MonoBehaviour
{
    public void pay5()
    {
        GameManager.instance.playerHealth = 1;
        GameManager.instance.range = 10000000;
        GameManager.instance.speed = 16;
        GameManager.instance.devil = false;
        SceneManager.LoadScene(1);
    }
    public void pay3()
    {
        GameManager.instance.playerHealth = 3;
        GameManager.instance.devil = false;
        SceneManager.LoadScene(1);
    }
    public void pay1()
    {
        GameManager.instance.playerHealth = 5;
        GameManager.instance.canShoot = false;
        GameManager.instance.devil = false;
        SceneManager.LoadScene(1);
    }
}
