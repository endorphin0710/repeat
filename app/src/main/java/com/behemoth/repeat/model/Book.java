package com.behemoth.repeat.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private String title;
    private Uri imageUri;
    private int chapterCount;

    public Book(){};

    public Book(String title){
        this.title = title;
    }

    protected Book(Parcel in) {
        title = in.readString();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
        chapterCount = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getTitle() {
        return title;
    }
    public void setTitle(String text) {
        this.title = text;
    }

    public Uri getImageUri() { return imageUri; }
    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public int getChapterCount() { return chapterCount; }
    public void setChapterCount(int chapterCount) { this.chapterCount = chapterCount; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeParcelable(imageUri, i);
        parcel.writeInt(chapterCount);
    }
}
