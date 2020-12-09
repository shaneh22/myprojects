using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    private readonly int UP = 0;
    private readonly int LEFT = 90;
    private readonly int DOWN = 180;
    private readonly int RIGHT = 270;
    // Start is called before the first frame update
    public static GameManager instance;
    public int health = 5;
    public int damage = 0;
    public Enemy[] enemies;
    public float time = 4f;

    public GameObject gameOverScreen;
    public GameObject baseObject;

    public Text bossHealth;
    public Text playerHealth;

    public float xSpawn = 9.5f;
    public float ySpawn = 5.5f;

    void Start()
    {
        if (instance == null)
        {
            instance = this;
        }
        else if(instance!=this)
        {
            Destroy(gameObject);
        }
        StartCoroutine("Spawner");
        gameOverScreen.SetActive(false);
    }
    public void Update()
    {
        bossHealth.text = "Points: " + damage;
        playerHealth.text = "Health: " + health;
        if (health <= 0)
        {
            gameOverScreen.SetActive(true);
            StopAllCoroutines();
            StartCoroutine(Restart());
        }

        switch (damage)
        {
            case 10:
                time = 3.5f;
                break;
            case 20:
                time = 3f;
                break;
            case 25:
                time = 2.5f;
                break;
            case 30:
                time = 2f;
                break;
            case 35:
                time = 1f;
                break;
            case 40:
                time = .5f;
                break;
            default:
                break;
        }
    }
    public IEnumerator Restart()
    {
        while (true)
        {
            if (Input.anyKeyDown && Input.GetKeyDown(KeyCode.Return))
            {
                health = 5;
                damage = 0;
                gameOverScreen.SetActive(false);
                StartCoroutine(Spawner());
                baseObject.transform.SetPositionAndRotation(new Vector2(0, 0), Quaternion.identity);
            }
            yield return null;
        }
    }
    public int Random()
    {
        return UnityEngine.Random.Range(1, 5);
    }
    public Vector3 RandPosition()
    {
        int rand = Random();
        Vector3 pos = new Vector3();
        switch (rand)
        {
            case 1:
                pos.x = 0;
                pos.y = ySpawn;
                pos.z = DOWN;
                break;
            case 2:
                pos.x = xSpawn;
                pos.y = 0;
                pos.z = LEFT;
                break;
            case 3:
                pos.x = 0;
                pos.y = -ySpawn;
                pos.z = UP;
                break;
            case 4:
                pos.x = -xSpawn;
                pos.y = 0;
                pos.z = RIGHT;
                break;
        }
        return pos;
    }
    public void SpawnEnemy()
    {
        Vector3 rand = RandPosition();
        int rotation = (int)rand.z;
        Instantiate(enemies[Random()-1], new Vector3(rand.x,rand.y), Quaternion.Euler(0, 0, rotation));
    }
    private IEnumerator Spawner()
    {
        while (true)
        {
            yield return new WaitForSeconds(time);
            SpawnEnemy();
        }
    }
}
