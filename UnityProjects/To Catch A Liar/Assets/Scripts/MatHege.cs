using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MatHege : MonoBehaviour
{
    // Start is called before the first frame update
    private Rigidbody2D rb;
    void Start()
    {
        rb = GetComponent<Rigidbody2D>();
    }
    private void Update()
    {
        if (!GameManager.instance.endGameNotStarted)
        {
            rb.MovePosition(new Vector2(0, 0));
        }
    }
    private void OnMouseOver()
    {
        if (GameManager.instance.watchOut == true && GameManager.instance.tutorial==false) {
            GameManager.instance.WatchOut();
        }
        if (!GameManager.instance.endGameNotStarted)
        {
            if(GameManager.instance.bullets)
                GameManager.instance.OutOfBullets();
        }
        else
        {
            gameObject.transform.GetChild(0).localScale = new Vector3(1.1f, 1.1f, 0);
            rb.AddForce(gameObject.transform.up * 20f);
            Invoke("Stop", 2f);
        }
    }
    private void OnMouseExit()
    {
        gameObject.transform.GetChild(0).localScale = new Vector3(1f, 1f, 0);
    }
    public void Stop()
    {
        rb.AddForce(gameObject.transform.up * -20f);
        gameObject.transform.position = new Vector3(7.24f, 3.9f, 0);
    }
}
