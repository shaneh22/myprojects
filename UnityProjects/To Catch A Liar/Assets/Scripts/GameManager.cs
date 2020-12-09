using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameManager : MonoBehaviour
{
    public GameObject[] peoples;
    public int level;
    public static GameManager instance;
    public readonly float xPosition = 8f;
    public readonly float yPosition = 0f;
    public Vector3 liar;
    private GameObject liarPeople;
    public Vector3 twoTruthsAndALie;

    public GameObject levelScreen;
    public GameObject gameOverScreen;
    public GameObject titleScreen;
    public GameObject sideBar;
    public GameObject MatHome;
    public GameObject endGame;

    private string[] dialogue;
    public DialogueTrigger dialogueTrigger;
    public bool tutorial = true;
    public bool watchOut = true;
    public bool endGameNotStarted = true;
    public bool bullets = true;
    // Start is called before the first frame update
    void Start()
    {
        if (instance == null)
        {
            instance = this;
        }
        else if (instance != gameObject)
        {
            Destroy(gameObject);
        }
        sideBar.SetActive(false);
        gameOverScreen.SetActive(false);
        levelScreen.SetActive(false);
        MatHome.SetActive(false);
        endGame.SetActive(false);
        StartCoroutine(Tutorial());
    }
    public IEnumerator Tutorial()
    {
        while (true)
        {
            if (Input.anyKeyDown && level == 0)
            {
                titleScreen.SetActive(false);
                gameOverScreen.SetActive(false);
                sideBar.SetActive(true);
                MatHome.SetActive(true);
                LevelSetup();
                dialogue = new string[] { "Hey, I'm Matthew Hege, but my friends call me Mat.", "Your goal is to catch the liar.", "In the bottom left are two correct traits, ...","And one LIE to help find the liar.", "The liar will also flash red, but be careful.","Some innocent people may flash red as well.","Use both of these tools to find the liar.","Once you've found the liar, point and shoot.", "Good Luck!" };
                dialogueTrigger.dialogue = dialogue;
                dialogueTrigger.TriggerDialogue();
                break;
            }
            yield return null;
        }
    }
    public void FinishTutorial()
    {
        _ = StartCoroutine(SpawnPeople());
        _ = StartCoroutine(LiarSpawner());
        tutorial = false;
    }
    public void LevelSetup()
    {
        sideBar.transform.GetChild((int)twoTruthsAndALie.x).gameObject.SetActive(false);
        sideBar.transform.GetChild((int)twoTruthsAndALie.x).gameObject.transform.GetChild((int)twoTruthsAndALie.y).gameObject.SetActive(false);
        sideBar.transform.GetChild((int)twoTruthsAndALie.x).gameObject.transform.GetChild((int)twoTruthsAndALie.z).gameObject.SetActive(false);
        level++;
        levelScreen.SetActive(true);
        liar = new Vector3(Random.Range(0, 7), Random.Range(0, 7), Random.Range(8, 15));
        twoTruthsAndALie = liar;
        int random = Random.Range(1, 3);
        if (random == 1)
        {
            while (twoTruthsAndALie == liar)
            {
                twoTruthsAndALie = new Vector3(Random.Range(0, 7), liar.y, liar.z);
            }
        }
        else if (random == 2)
        {
            while (twoTruthsAndALie == liar)
            {
                twoTruthsAndALie = new Vector3(liar.x, Random.Range(0, 7), liar.z);
            }
        }
        else
        {
            while (twoTruthsAndALie == liar)
            {
                twoTruthsAndALie = new Vector3(liar.x, Random.Range(0, 7), liar.z);
            }
        }
        sideBar.transform.GetChild((int)twoTruthsAndALie.x).gameObject.SetActive(true);
        sideBar.transform.GetChild((int)twoTruthsAndALie.x).gameObject.transform.GetChild((int)twoTruthsAndALie.y).gameObject.SetActive(true);
        sideBar.transform.GetChild((int)twoTruthsAndALie.x).gameObject.transform.GetChild((int)twoTruthsAndALie.z).gameObject.SetActive(true);
        Invoke("HideLevelImage", 2f);
    }
    public void HideLevelImage()
    {
        levelScreen.SetActive(false);
    }
    public Vector2 GetRandPosition()
    {
        int coinFlip = Random.Range(0, 2);
        float xPos = xPosition;
        if (coinFlip == 0)
        {
            xPos = -xPosition;
        }
        float yPos = Random.Range(yPosition - 4, yPosition + 4);
        return new Vector2(xPos, yPos);
    }
    public IEnumerator SpawnPeople()
    {
        while (true)
        {
            if (level > 3)
            {
                yield return new WaitForSeconds(1f);
            }
            else if (level == 3)
            {
                yield return new WaitForSeconds(1.5f);
            }
            else
            {
                yield return new WaitForSeconds(2f);
            }
            GenerateRandomCharacter();
        }
    }
    public IEnumerator LiarSpawner()
    {
        yield return new WaitForSeconds(5f);
        while (true)
        {
            yield return new WaitForSeconds(Random.Range(Random.Range(1, 3) * level, Random.Range(4, 6) * level));
            SpawnLiar();
        }
    }
    public void SpawnLiar()
    {
        liarPeople = Instantiate(peoples[(int)liar.x], GetRandPosition(), Quaternion.Euler(0, 0, 90));
        if (liarPeople.transform.localPosition.x < 0)
        {
            liarPeople.transform.Rotate(new Vector3(0, 0, 180));
        }
        if (Random.Range(0, 2) == 0)
        {
            int coinFlip = Random.Range(0, 2);
            float yPos = 4;
            float degrees = -90;
            if (coinFlip == 0)
            {
                yPos = -4;
                degrees = -270;
            }
            liarPeople.transform.SetPositionAndRotation(new Vector3(Random.Range(-4, 4), yPos, 0), Quaternion.Euler(0, 0, degrees));
        }
        liarPeople.GetComponent<People>().setID(liar);
        liarPeople.transform.GetChild((int)liar.y).gameObject.SetActive(true);
        liarPeople.transform.GetChild((int)liar.z).gameObject.SetActive(true);
    }
    public void GenerateRandomCharacter()
    {
        Vector3 id = new Vector3(Random.Range(0, 7), Random.Range(0, 7), Random.Range(8, 15));
        GameObject randomPeople = Instantiate(peoples[(int) id.x], GetRandPosition(), Quaternion.Euler(0,0,90));
        if(randomPeople.transform.localPosition.x < 0)
        {
            randomPeople.transform.Rotate(new Vector3(0, 0, 180));
        }
        if (Random.Range(0, 2) == 0)
        {
            int coinFlip = Random.Range(0, 2);
            float yPos = 4;
            float degrees = -90;
            if (coinFlip == 0)
            {
                yPos = -4;
                degrees = -270;
            }
            randomPeople.transform.SetPositionAndRotation(new Vector3(Random.Range(-4, 4), yPos, 0), Quaternion.Euler(0, 0, degrees));
        }
        randomPeople.transform.GetChild((int)id.y).gameObject.SetActive(true);
        randomPeople.transform.GetChild((int)id.z).gameObject.SetActive(true);
        randomPeople.GetComponent<People>().setID(id);
    }
    public void GameOver()
    {
        StopAllCoroutines();
        gameOverScreen.SetActive(true);
        _ = StartCoroutine(Restart());
    }
    public IEnumerator Restart()
    {
        yield return new WaitForSeconds(.5f);
        while (true)
        {
            if (Input.anyKeyDown)
            {
                level = 0;
                titleScreen.SetActive(false);
                gameOverScreen.SetActive(false);
                sideBar.SetActive(true);
                MatHome.SetActive(true);
                LevelSetup();
                _ = StartCoroutine(SpawnPeople());
                _ = StartCoroutine(LiarSpawner());
                break;
            }
            yield return null;
        }
    }
    public void EndGame()
    {
        StopAllCoroutines();
        sideBar.SetActive(false);
        MatHome.gameObject.transform.GetChild(0).gameObject.SetActive(false);
        dialogue = new string[] {"Congratulations!","You caught all the liars!","NOT!"};
        dialogueTrigger.dialogue = dialogue;
        dialogueTrigger.TriggerDialogue();
    }
    public void CommenceTheEndGame()
    {
        _ = StartCoroutine(EndGameStarted());
        endGameNotStarted = false;
    }
    public IEnumerator EndGameStarted()
    {
        endGame.SetActive(true);
        endGame.gameObject.transform.GetChild(0).gameObject.SetActive(true);
        yield return new WaitForSeconds(.2f);
        endGame.gameObject.transform.GetChild(1).gameObject.SetActive(true);
        yield return new WaitForSeconds(.2f);
        endGame.gameObject.transform.GetChild(2).gameObject.SetActive(true);
        yield return new WaitForSeconds(.2f);
        endGame.gameObject.transform.GetChild(3).gameObject.SetActive(true);
        yield return new WaitForSeconds(.2f);
        endGame.gameObject.transform.GetChild(4).gameObject.SetActive(true);
        yield return new WaitForSeconds(.2f);
        endGame.gameObject.transform.GetChild(5).gameObject.SetActive(true);
        yield return new WaitForSeconds(.2f);
        endGame.gameObject.transform.GetChild(6).gameObject.SetActive(true);
        yield return new WaitForSeconds(.2f);
        endGame.gameObject.transform.GetChild(7).gameObject.SetActive(true);
        endGame.gameObject.transform.GetChild(8).gameObject.SetActive(true);
        endGame.gameObject.transform.GetChild(9).gameObject.SetActive(true);
        endGame.gameObject.transform.GetChild(10).gameObject.SetActive(true);
        endGame.gameObject.transform.GetChild(11).gameObject.SetActive(true);
        endGame.gameObject.transform.GetChild(12).gameObject.SetActive(true);
        endGame.gameObject.transform.GetChild(13).gameObject.SetActive(true);
    }
    public void WatchOut()
    {
        dialogue = new string[] { "Watch where you aim that thing!" };
        dialogueTrigger.dialogue = dialogue;
        dialogueTrigger.TriggerDialogue();
        watchOut = false;
    }
    public void OutOfBullets()
    {
        dialogue = new string[] { "FOOL, you are out of bullets!", "You used them all to kill my enemies!","I was the only liar all along!" ,"Mwahahahaha!"};
        dialogueTrigger.dialogue = dialogue;
        dialogueTrigger.TriggerDialogue();
        bullets = false;
    }
}
