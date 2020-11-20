package com.behemoth.repeat.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Book implements Parcelable {

    private String id;
    private String author;
    private String title;
    private long createdDate;
    private Uri imageUri;
    private String imageName;
    private int chapterCount;
    private List<Chapter> chapter;

    public Book(){}

    public Book(String title){
        this.title = title;
    }

    public Book(String id, String author, String title, String imageName, long createdDate){
        this.id = id;
        this.author = author;
        this.title = title;
        this.imageName = imageName;
        this.createdDate = createdDate;
    }

    protected Book(Parcel in) {
        id = in.readString();
        author = in.readString();
        title = in.readString();
        createdDate = in.readLong();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
        imageName = in.readString();
        chapterCount = in.readInt();

        chapter = new ArrayList<>();
        in.readTypedList(chapter, Chapter.CREATOR);
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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getTitle() {
        return title;
    }
    public void setTitle(String text) {
        this.title = text;
    }

    public long getCreatedDate() { return createdDate; }
    public void setCreatedDate(long createdDate) { this.createdDate = createdDate; }

    public Uri getImageUri() { return imageUri; }
    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public int getChapterCount() { return chapterCount; }
    public void setChapterCount(int chapterCount) { this.chapterCount = chapterCount; }

    public List<Chapter> getChapter() { return chapter; }
    public void setChapter(List<Chapter> chapter) { this.chapter = chapter; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeLong(createdDate);
        dest.writeParcelable(imageUri, i);
        dest.writeString(imageName);
        dest.writeInt(chapterCount);
        dest.writeList(chapter);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", createdDate=" + createdDate +
                ", imageUri=" + imageUri +
                ", imageName='" + imageName + '\'' +
                ", chapterCount=" + chapterCount +
                ", chapter=" + chapter +
                '}';
    }
}
