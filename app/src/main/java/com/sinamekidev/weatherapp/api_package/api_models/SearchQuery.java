package com.sinamekidev.weatherapp.api_package.api_models;

import com.google.gson.annotations.SerializedName;

public class SearchQuery {
    @SerializedName("answer_box")
    private AnswerBox answerBox;
    public AnswerBox getAnswerBox(){
        return this.answerBox;
    }
}
