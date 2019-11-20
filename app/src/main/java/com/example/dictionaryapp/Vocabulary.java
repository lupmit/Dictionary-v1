package com.example.dictionaryapp;

public class Vocabulary {
    private  String word;
    private  String pronunciation;
    private  int button;
    private String score;
    private String spokenword;
    public Vocabulary(){}

    public Vocabulary(String word, String pronunciation, int button, String score, String spokenword) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.button = button;
        this.score = score;
        this.spokenword = spokenword;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSpokenword() {
        return spokenword;
    }

    public void setSpokenword(String spokenword) {
        this.spokenword = spokenword;
    }
}
