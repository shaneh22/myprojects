using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Base : MonoBehaviour
{
    /*   private readonly int UP = 0;
       private readonly int LEFT = 90;
       private readonly int DOWN = 180;
       private readonly int RIGHT = 270;
       void Update()
       {
           if (Input.anyKeyDown)
           {
               if(Input.GetKeyDown(KeyCode.UpArrow))
               {
                   gameObject.transform.rotation = Quaternion.Euler(0, 0, UP);
               }
               else if (Input.GetKeyDown(KeyCode.DownArrow))
               {
                   gameObject.transform.rotation = Quaternion.Euler(0, 0, DOWN);
               }
               else if (Input.GetKeyDown(KeyCode.RightArrow))
               {
                   gameObject.transform.rotation = Quaternion.Euler(0, 0, RIGHT);
               }
               else if (Input.GetKeyDown(KeyCode.LeftArrow))
               {
                   gameObject.transform.rotation = Quaternion.Euler(0, 0, LEFT);
               }
           }
       }*/
    private void FixedUpdate()
    {
        for (int i = 0; i < 5; i++)
        {
            if (Input.GetAxis("Horizontal") < 0)
            {
                gameObject.transform.Rotate(0, 0, 1);
            }
            else if (Input.GetAxis("Horizontal") > 0)
            {
                gameObject.transform.Rotate(0, 0, -1);
            }
        }
    }
}
