using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class People : MonoBehaviour
{
    // Start is called before the first frame update
    Rigidbody2D rb;
    private Vector3 id;
    public AudioClip gunShot;
    public AudioClip deathSound;
    void Start()
    {
        rb = GetComponent<Rigidbody2D>();
        _ = StartCoroutine(Rotate());
    }

    // Update is called once per frame
    void Update()
    {
        //rb.velocity = new Vector2(0,5f);
        rb.AddForce(transform.up * 2);
        if (GameManager.instance.level == 6)
        {
            StopAllCoroutines();
            gameObject.SetActive(false);
        }
    }
    public IEnumerator Rotate()
    {
        while (true)
        {
            yield return new WaitForSeconds(1f);
            gameObject.transform.Rotate(new Vector3(0, 0, Random.Range(0, 360)));
        }
    }
    private void OnMouseOver()
    {
        Vector3 liar = GameManager.instance.liar;
        if((id==liar)||(id.x != liar.x && id.y != liar.y)||(id.x!=liar.x && id.z!=liar.z)||(id.y!=liar.y&&id.z!=liar.z))
        {
            gameObject.transform.GetChild(16).localScale = new Vector3(1.1f, 1.1f, 0);
        }
    }
    private void OnMouseExit()
    {
        gameObject.transform.GetChild(16).localScale = new Vector3(1f, 1f, 0);
    }
    public Vector3 getID()
    {
        return id;
    }
    public void setID(Vector3 input)
    {
        id = input;
    }
    private void OnMouseDown()
    {
        SoundManager.instance.PlaySingle(gunShot);
        Invoke("PeopleDeath", .3f);
    }
    public void PeopleDeath()
    {
        SoundManager.instance.PlaySingle(deathSound);
        gameObject.SetActive(false);
        if (id == GameManager.instance.liar)
        {
            GameManager.instance.LevelSetup();
        }
        else
        {
            GameManager.instance.GameOver();
        }
    }
}