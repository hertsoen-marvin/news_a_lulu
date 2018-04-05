package com.example.projet_mobile;

/**
 * Created by herts on 29/03/2018.
 */
public class RowItems {

    private String title;
    private String resume;
    private String author;

    private String imageURL;

    public RowItems(String title,String resume,String author, String imageURL)
    {
        this.title = title;
        this.resume = resume;
        this.author = author;

        this.imageURL = imageURL;
    }

    public String getTitle()
    {
        return this.title;
    }
    public String getResume()
    {
        return this.resume;
    }

    public String getAuthor()
    {
        return this.author;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setResume(String resume)
    {
        this.resume = resume;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getImageURL()
    {
        return this.imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

}