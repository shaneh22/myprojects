using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    public float health;
    public float speed;
    public Rigidbody2D rb;

    public AudioClip hurtSound;

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
            //targetPosition.x += speed * Time.deltaTime;
            rb.velocity = new Vector2(speed, rb.velocity.y);
        }
        else if (enemyPosition.x - targetPosition.x < 0)
        {
            //targetPosition.x -= speed * Time.deltaTime;
            rb.velocity = new Vector2(-speed, rb.velocity.y);
        }

        if (enemyPosition.y - targetPosition.y > 0)
        {
            //targetPosition.y += speed * Time.deltaTime;
            rb.velocity = new Vector2(rb.velocity.x, speed);
        }
        else if (enemyPosition.y - targetPosition.y < 0)
        {
            //targetPosition.y -= speed * Time.deltaTime;
            rb.velocity = new Vector2(rb.velocity.x, -speed);
        }
        //rb.MovePosition(targetPosition);
    }
    public void HitEnemy(float hit)
    {
        health -= hit;
        SoundManager.instance.PlaySingle(hurtSound);
        if (health <= 0)
        {
            Destroy(gameObject);
        }
    }
}
