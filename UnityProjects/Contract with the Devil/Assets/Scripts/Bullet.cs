using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour
{
    public float speed;
    public float range;
    public float damage;
    public Rigidbody2D rb;
    float hAxis;
    float vAxis;

    // Start is called before the first frame update
    void Start()
    {
        range = GameManager.instance.range;
        speed = GameManager.instance.speed;

        rb = GetComponent<Rigidbody2D>();
        StartCoroutine(Destroy());
    }
    public void SetPosition(float h, float v)
    {
        if (h > 0)
        {
            hAxis = 1;
        }
        else if (h < 0)
        {
            hAxis = -1;
        }
        if (v > 0)
        {
            vAxis = 1;
        }
        else if (v < 0)
        {
            vAxis = -1;
        }
        if(System.Math.Abs(h) < float.Epsilon && System.Math.Abs(v) < float.Epsilon)
        {
            hAxis = 1f;
            vAxis = 1f;
        }
    }
    // Update is called once per frame
    private void FixedUpdate()
    {
        Vector2 movement = new Vector2(hAxis, vAxis) * speed * Time.deltaTime;
        rb.MovePosition(rb.position + movement);
    }
    public void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.CompareTag("Wall"))
        {
            Destroy(gameObject);
        }
        else if (collision.gameObject.CompareTag("Enemy"))
        {
            Destroy(gameObject);
            collision.gameObject.GetComponent<Enemy>().HitEnemy(damage);
        }
    }
    public IEnumerator Destroy()
    {
        yield return new WaitForSecondsRealtime(range);
        Destroy(gameObject);
    }
}
