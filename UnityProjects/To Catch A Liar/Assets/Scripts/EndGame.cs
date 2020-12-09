using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EndGame : MonoBehaviour
{
    public Animator animator;
    // Start is called before the first frame update
    private void OnEnable()
    {
        StartCoroutine(Animate());
    }
    public IEnumerator Animate()
    {
        yield return new WaitForSeconds(1.5f);
        animator.SetTrigger("TheGame");
    }
}
