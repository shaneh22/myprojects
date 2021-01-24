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
    public float speed = 15;
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
        StartLevel();
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
    public void StartLevel()
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

            Invoke(nameof(HideLevelImage), 1f);
        }
    }
    public void GameOver()
    {
        doingSetup = true;
        Time.timeScale = 0;
        levelImage.SetActive(true);
        levelText.text = "Game Over";
        SoundManager.instance.musicSource.Stop();
        _ = StartCoroutine(nameof(Restart));
    }
    public IEnumerator Restart()
    {
        while (true)
        {
            if (Input.GetKeyDown(KeyCode.Return))
            {
                devil = true;
                level = 1;
                canShoot = true;
                range = .75f;
                speed = 12;
                playerHealth = 5;
                SoundManager.instance.musicSource.Play();
                Time.timeScale = 1;
                SceneManager.LoadScene(1);
                break;
            }
            yield return null;
        }
    }
    private void HideLevelImage()
    {
        boardScript.SetupScene(level);
        levelImage.SetActive(false);
        doingSetup = false;
    }
}
