using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public static GameManager instance;
    public GameObject devilScene;
    public BoardManager boardScript;
    public bool devil = true;
    public Text levelText;
    public GameObject levelImage;
    public bool doingSetup = true;
    public int playerHealth = 5;
    public float range = .75f;
    public float speed = 8;
    public bool canShoot = true;

    public int level = 1;
    void Start()
    {
        if (instance == null)
        {
            instance = this;
        }
        else if (instance != this)
        {
            Destroy(gameObject);
        }
        DontDestroyOnLoad(gameObject);
        levelImage = GameObject.Find("LevelImage");
        boardScript = GetComponent<BoardManager>();
        SceneManager.LoadScene(1);
    }
    [RuntimeInitializeOnLoadMethod(RuntimeInitializeLoadType.AfterSceneLoad)]
    static public void CallbackInitialization()
    {
        SceneManager.sceneLoaded += OnSceneLoaded;
    }
    static private void OnSceneLoaded(Scene scene, LoadSceneMode loadSceneMode)
    {
        if (instance != null)
        {
            instance.StartLevel();
        }
    }
    private void StartLevel()
    {
        doingSetup = true;
        devilScene = GameObject.Find("Devil");
        if (!devil)
        {
            levelImage = GameObject.Find("LevelImage");
            levelText = GameObject.Find("LevelText").GetComponent<Text>();
            levelImage.SetActive(true);
            levelText.text = "Floor " + level;
            devilScene.SetActive(false);

            Invoke("HideLevelImage", 1f);
        }
    }
    public void GameOver()
    {
        doingSetup = true;
        levelImage.SetActive(true);
        levelText.text = "Game Over";
        SoundManager.instance.musicSource.Stop();
        Invoke("Restart", 2f);
    }
    public void Restart()
    {
        devil = true;
        level = 1;
        canShoot = true;
        range = .75f;
        speed = 8;
        playerHealth = 5;
        SoundManager.instance.musicSource.Play();
        SceneManager.LoadScene(1);
    }
    private void HideLevelImage()
    {
        boardScript.SetupScene(level);
        levelImage.SetActive(false);
        doingSetup = false;

    }
}
