using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BoardManager : MonoBehaviour
{
    private readonly int columns = 16;
    private readonly int rows = 16;
    public GameObject wall;
    public GameObject floor;
    public GameObject exit;
    public GameObject ghost;
    public int numEnemies;

    public Transform boardHolder;
    private List<Vector2> gridPositions = new List<Vector2>();

    void InitializeList()
    {
        gridPositions.Clear();
        for (int x = 1; x < columns - 1; x++)
        {
            for (int y = 1; y < rows - 1; y++)
            {
                gridPositions.Add(new Vector2(x, y));
            }
        }
    }
    Vector2 RandomPosition()
    {
        int randomIndex = Random.Range(0, gridPositions.Count);
        Vector2 randomPosition = gridPositions[randomIndex];
        gridPositions.RemoveAt(randomIndex);
        return randomPosition;
    }
    private void BoardSetup()
    {
        boardHolder = new GameObject("Board").transform;
        for (int x = -1; x < columns + 1; x++)
        {
            for (int y = -1; y < rows + 1; y++)
            {
                GameObject tile;
                if (x == -1 || x == columns || y == -1 || y == rows)
                {
                    tile = wall;
                }
                else
                {
                    tile = floor;
                }
                GameObject instance = Instantiate(tile, new Vector2(x, y), Quaternion.identity) as GameObject;
                instance.transform.SetParent(boardHolder);
            }
        }
    }
    public void SetupScene(int level)
    {
        BoardSetup();
        InitializeList();
        _ = Instantiate(exit, new Vector2(columns - 1, rows - 1), Quaternion.identity);
        numEnemies = Random.Range((level / 2) + 5, level + 3);
        for (int i = 0; i < numEnemies; i++)
        {
            Vector2 randomPosition = RandomPosition();
            if(randomPosition.x < 5)
            {
                randomPosition.x += 5;
            }
            else if (randomPosition.y < 5)
            {
                randomPosition.y += 5;
            }
            _ = Instantiate(ghost, randomPosition, Quaternion.identity);
        }
    }
}
