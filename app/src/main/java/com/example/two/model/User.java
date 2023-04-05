package com.example.two.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable , Parcelable {


    private String name;

    private String nickname;

    private String userEmail;
    private String password;

    private int gender;

    private int age;

    protected User(Parcel in) {
        name = in.readString();
        nickname = in.readString();
        userEmail = in.readString();
        password = in.readString();
        gender = in.readInt();
        age = in.readInt();
        questionNum = in.readInt();
        questionAnswer = in.readString();
        profileImgUrl = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int questionNum;

    private String questionAnswer;

    private String profileImgUrl;

    private String createdAt;

    public User(String userEmail, String password, String nickname, String profileImgUrl) {
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }


    public User(String userEmail, String password, String nickname) {
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    public User(String name, String nickname, String userEmail, String password, int gender, int questionNum, String questionAnswer) {
        this.name = name;
        this.nickname = nickname;
        this.userEmail = userEmail;
        this.password = password;
        this.gender = gender;
        this.questionNum = questionNum;
        this.questionAnswer = questionAnswer;
    }

    public User(String name, int questionNum, String questionAnswer) {
        this.name = name;
        this.questionNum = questionNum;
        this.questionAnswer = questionAnswer;
    }

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(nickname);
        parcel.writeString(userEmail);
        parcel.writeString(password);
        parcel.writeInt(gender);
        parcel.writeInt(age);
        parcel.writeInt(questionNum);
        parcel.writeString(questionAnswer);
        parcel.writeString(profileImgUrl);
        parcel.writeString(createdAt);
    }
}
