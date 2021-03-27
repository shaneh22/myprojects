using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ArmPivot : MonoBehaviour
{
    private void FixedUpdate()
    {
        Vector3 distanceVector = Camera.main.ScreenToWorldPoint(Input.mousePosition) - transform.position;
        distanceVector.Normalize();
        float angle = Mathf.Atan2(distanceVector.y, distanceVector.x) * Mathf.Rad2Deg;
        transform.rotation = Quaternion.Euler(0f, 0f, angle);
    }
}
