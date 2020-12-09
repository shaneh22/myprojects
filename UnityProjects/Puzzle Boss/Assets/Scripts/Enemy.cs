using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    Rigidbody2D rb;
    public void OnTriggerEnter2D(Collider2D collision)
    {
        gameObject.SetActive(false);
        if (tag.Equals(collision.tag))
        {
            GameManager.instance.damage++;
        }
        else
        {
            GameManager.instance.health--;
        }
    }
    public void Awake()
    {
        rb = GetComponent<Rigidbody2D>();
    }
    // Update is called once per frame
    void Update()
    {
        Vector2 newPos = -rb.position *Time.deltaTime;
        rb.MovePosition(rb.position + newPos);
    }
}
