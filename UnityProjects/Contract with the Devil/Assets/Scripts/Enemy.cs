using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    public float health;
    public float speed;
    public Rigidbody2D rb;

    public void Start()
    {
        rb = gameObject.GetComponent<Rigidbody2D>();
    }

    public void FixedUpdate()
    {
        Vector2 enemyPosition = GameObject.FindWithTag("Player").transform.position;
        Vector2 targetPosition = gameObject.transform.position;
        if (enemyPosition.x - targetPosition.x > 0)
        {
            targetPosition.x += speed * Time.deltaTime;
        }
        else if (enemyPosition.x - targetPosition.x < 0)
        {
            targetPosition.x -= speed * Time.deltaTime;
        }

        if (enemyPosition.y - targetPosition.y > 0)
        {
            targetPosition.y += speed * Time.deltaTime;
        }
        else if (enemyPosition.y - targetPosition.y < 0)
        {
            targetPosition.y -= speed * Time.deltaTime;
        }
        rb.MovePosition(targetPosition);
    }
    public void HitEnemy(float hit)
    {
        health -= hit;
        if (health <= 0)
        {
            Destroy(gameObject);
        }
    }
}
