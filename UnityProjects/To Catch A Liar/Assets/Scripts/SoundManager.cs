using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SoundManager : MonoBehaviour
{
    public AudioSource efxSource;
    public AudioSource musicSource;
    public static SoundManager instance;

    public float lowPitchRange = .95f;
    public float highPitchRange = 1.05f;

    void Awake()
    {
        if (instance == null)
        {
            instance = this;
        }
        else if (instance != this)
        {
            Destroy(gameObject);
        }
        DontDestroyOnLoad(gameObject);
        _ = StartCoroutine(Mute());
    }

    public void PlaySingle(AudioClip clip)
    {
        efxSource.clip = clip;
        efxSource.pitch = Random.Range(lowPitchRange, highPitchRange);
        efxSource.Play();
    }
    public IEnumerator Mute()
    {
        while (true)
        {
            if(Input.anyKeyDown && Input.GetKeyDown(KeyCode.M))
            {
                if (musicSource.mute == true)
                {
                    musicSource.mute = false;
                }
                else
                {
                    musicSource.mute = true;
                }
            }
            yield return null;
        }
    }
}
