using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class DialogueManager : MonoBehaviour
{
    private Queue<string> sentences;
    public TextMeshPro dialogueText;
    public Animator animator;
    private void Awake()
    {
        sentences = new Queue<string>();
    }
    public void StartDialogue(string[] dialogue)
    {
        animator.SetBool("IsOpen", true);
        sentences.Clear();
        foreach (string sentence in dialogue)
        {
            sentences.Enqueue(sentence);
        }
        DisplayNextSentence();
    }
    public void DisplayNextSentence()
    {
        if (sentences.Count == 0)
        {
            EndDialogue();
            return;
        }
        string sentence = sentences.Dequeue();
        StopAllCoroutines();
        StartCoroutine(TypeSentence(sentence));
    }
    public IEnumerator TypeSentence(string sentence)
    {
        dialogueText.text = "";
        foreach (char letter in sentence.ToCharArray())
        {
            dialogueText.text += letter;
            if (dialogueText.text.Equals(sentence))
            {
                StartCoroutine(NextSentence());
            }
            yield return null;
        }
    }
    public IEnumerator NextSentence()
    {
        while (true)
        {
            if (Input.anyKeyDown)
            {
                DisplayNextSentence();
            }
            yield return null;
        }
    }
    void EndDialogue()
    {
        animator.SetBool("IsOpen", false);
        if (GameManager.instance.tutorial)
        {
            GameManager.instance.FinishTutorial();
        }
        else if (GameManager.instance.endGameNotStarted && GameManager.instance.level==6)
        {
            GameManager.instance.CommenceTheEndGame();
        }
    }
}
