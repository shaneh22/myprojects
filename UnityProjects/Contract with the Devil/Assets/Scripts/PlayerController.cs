using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class PlayerController : MonoBehaviour
{
    public float speed;
    public Text healthText;
    public GameObject bullet0;
    public Rigidbody2D rb;
    public int health;
    [HideInInspector] public float lastHAxis;
    [HideInInspector] public float lastVAxis;

    // Start is called before the first frame update
    private void Awake()
    {
        if (GameManager.instance != null)
        {
            health = GameManager.instance.playerHealth;
        }
        healthText.text = "Health: " + health;
        rb = GetComponent <Rigidbody2D>();
        StartCoroutine(Fire());
    }

    // Update is called once per frame
    private void FixedUpdate()
    {
        if (!GameManager.instance.doingSetup)
        {
            float hAxis = Input.GetAxis("Horizontal");
            float vAxis = Input.GetAxis("Vertical");
            Vector2 movement = new Vector2(hAxis, vAxis) * speed * Time.deltaTime;
            rb.MovePosition(rb.position + movement);
            if (!(System.Math.Abs(hAxis) < .15f && System.Math.Abs(vAxis) < .15f))
            {
                lastHAxis = hAxis;
                lastVAxis = vAxis;
            }
        }
    }
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "Exit")
        {
            GameManager.instance.level++;
            SceneManager.LoadScene(1);
        }
        else if (collision.tag == "Enemy")
        {
            LoseHealth();
        }
    }
    private IEnumerator Fire()
    {
        yield return new WaitForSeconds(.1f);
        while (GameManager.instance.canShoot)
        {
            if (!GameManager.instance.doingSetup && Input.anyKeyDown && (Input.GetKeyDown(KeyCode.Z) || Input.GetKeyDown(KeyCode.Return)))
            {
                GameObject temp = Instantiate(bullet0, rb.position + GetBulletPos(), Quaternion.identity);
                temp.GetComponent<Bullet>().SetPosition(lastHAxis, lastVAxis);
            }
            yield return null;
        }
    }
    public void LoseHealth()
    {
        health--;
        healthText.text = "Health: " + health;
        if (health <= 0)
        {
            GameManager.instance.GameOver();
        }
    }
    public Vector2 GetBulletPos()
    {
        Vector2 position;
        if (lastHAxis < 0)
        {
            position.x = -.4f;
        }
        else
        {
            position.x = .4f;
        }
        if (lastVAxis < 0)
        {
            position.y = -.4f;
        }
        else 
        {
            position.y = .4f;
        }
        return position;
    }
}
