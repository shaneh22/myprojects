using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraFollow : MonoBehaviour
{
    public Transform target;
    public float smoothSpeed;

    private void FixedUpdate()
    {
        Vector3 desiredPosition = target.position;
        Vector3 smoothedPosition = Vector3.Lerp(transform.position, desiredPosition, smoothSpeed *Time.deltaTime);
        smoothedPosition.z = -10;
        transform.position = smoothedPosition;

        transform.LookAt(target);
    }
}
