using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class Intro : MonoBehaviour
{
    public Slider slider;
    public GameObject snowbear;
    public GameObject title;

    public int increment = 10;

    void Awake()
    {
        title.SetActive(false);
        StartCoroutine(Slider());
    }
    public IEnumerator Slider()
    {
        yield return new WaitForSeconds(1f);
        while (slider.value < 1)
        {
            slider.value += increment * Time.deltaTime;
            yield return null;
        }
        yield return new WaitForSeconds(.75f);
        snowbear.SetActive(false);
        title.SetActive(true);
        StartCoroutine(Title());
    }
    public IEnumerator Title()
    {
        while (true)
        {
            if (Input.mousePresent && Input.GetMouseButtonDown(0))
            {
                SceneManager.LoadScene(1);
            }
            yield return null;
        }
    }

}
