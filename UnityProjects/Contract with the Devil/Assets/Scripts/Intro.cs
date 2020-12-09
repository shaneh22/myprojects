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
    public GameObject dialogue;
    public Text dialogue1;
    public Text dialogue2;
    public Text dialogue3;
    public Text dialogue4;
    public Text forthe;
    public Text right;
    public Text price;
    public int increment = 10;
    public bool nextDialogue;

    public readonly char[] DIALOGUE_1 = "You find yourself waking up in a dark and mysterious place...".ToCharArray();
    public readonly char[] DIALOGUE_2 = "Suddenly, you hear a deep and ominous voice cackling...".ToCharArray();
    public readonly char[] DIALOGUE_3 = "\"If you want any shot of getting out of here alive...\"".ToCharArray();
    public readonly char[] DIALOGUE_4 = "...you'll need my help. I'd be glad to help you...".ToCharArray();
    public readonly char[] FORTHE = "FOR THE".ToCharArray();
    public readonly char[] RIGHT = "RIGHT".ToCharArray();
    public readonly char[] PRICE = "PRICE".ToCharArray();

    void Awake()
    {
        title.SetActive(false);
        dialogue.SetActive(false);
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
            if(Input.mousePresent && Input.GetMouseButtonDown(0))
            {
                _ = StartCoroutine(Skip());
                yield return new WaitForSeconds(.4f);
                title.SetActive(false);
                StartCoroutine(Dialogue());
                break;
            }
            yield return null;
        }
    }
    public IEnumerator Dialogue()
    {
        dialogue.SetActive(true);
        _ = StartCoroutine(TypeText(DIALOGUE_1, dialogue1));
        while (!nextDialogue)
        {
            yield return null;
        }
        _ = StartCoroutine(TypeText(DIALOGUE_2, dialogue2));
        while (!nextDialogue)
        {
            yield return null;
        }
        _ = StartCoroutine(TypeText(DIALOGUE_3, dialogue3));
        while (!nextDialogue)
        {
            yield return null;
        }
        _ = StartCoroutine(TypeText(DIALOGUE_4, dialogue4));
        while (!nextDialogue)
        {
            yield return null;
        }
        _ = StartCoroutine(TypeBoldedText(FORTHE, forthe));
        while (!nextDialogue)
        {
            yield return null;
        }
        _ = StartCoroutine(TypeBoldedText(RIGHT, right));
        while (!nextDialogue)
        {
            yield return null;
        }
        _ = StartCoroutine(TypeBoldedText(PRICE, price));
        while (!nextDialogue)
        {
            yield return null;
        }
        yield return new WaitForSeconds(.75f);
        SceneManager.LoadScene(1);
    }
    public IEnumerator TypeText(char[] array, Text text)
    {
        nextDialogue = false;
        foreach (char c in array)
        {
            text.text += c;
            yield return new WaitForSeconds(.1f);
        }
        for (float i = 1; i >= 0; i -= .1f)
        {
            text.color = new Color(i, i, i);
            yield return new WaitForSeconds(.125f);
        }
        text.color = new Color(0, 0, 0);
        nextDialogue = true;
    }
    public IEnumerator TypeBoldedText(char[] array, Text text)
    {
        nextDialogue = false;
        foreach (char c in array)
        {
            text.text += c;
            yield return new WaitForSeconds(.075f);
        }
        nextDialogue = true;
    }
    public IEnumerator Skip()
    {
        while (true)
        {
            if(Input.anyKeyDown && Input.GetKeyDown(KeyCode.Return))
            {
                SceneManager.LoadScene(1);
            }
            yield return null;
        }
    }
}