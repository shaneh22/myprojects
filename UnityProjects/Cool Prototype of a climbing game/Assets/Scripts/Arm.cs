using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Arm : MonoBehaviour
{
    private bool canPunch;
    public Player player;
    public float force;

    private void OnTriggerEnter2D(Collider2D collision)
    {
        canPunch = true;
        Debug.Log(canPunch);
    }
    private void OnTriggerExit2D(Collider2D collision)
    {
        canPunch = false;
        Debug.Log(canPunch);
    }

    private void Update()
    {
        if (Input.GetMouseButtonDown(0) && canPunch)
        {
            Vector2 distanceVector = Camera.main.ScreenToWorldPoint(Input.mousePosition) - player.gameObject.transform.position;
            distanceVector.Normalize();
            Debug.Log("Punched :" + distanceVector);
            player.Punch(-distanceVector * force);
        }
    }
}