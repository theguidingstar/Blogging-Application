package com.example.yashshah.blogger;

/**
 * Created by Yash shah on 11-07-2017.
 */

public class PostElement {
    String Username;
    String Category;
    String Date;
    String Post;
    String Image_link;
    String title;
    String Content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return Content;
    }

    public String getPrimaryImageUrl() {
        return PrimaryImageUrl;
    }

    public String getSecondaryImageUrl1() {
        return SecondaryImageUrl1;
    }

    public String getSecondaryImageUrl2() {
        return SecondaryImageUrl2;
    }

    public String getSecondaryImageUrl3() {
        return SecondaryImageUrl3;
    }

    String PrimaryImageUrl;
    String SecondaryImageUrl1,SecondaryImageUrl2,SecondaryImageUrl3;

    PostElement(String Username,String title,String Category,String DateStamp,String Content,String PrimaryImageUrl,
            String SecondaryImageUrl1, String SecondaryImageUrl2,String SecondaryImageUrl3)
    {
        this.Username=Username;
        this.title=title;
        this.Category=Category;
        this.Date=DateStamp;
        this.Content=Content;
        this.PrimaryImageUrl=PrimaryImageUrl;
        this.SecondaryImageUrl1=SecondaryImageUrl1;
        this.SecondaryImageUrl2=SecondaryImageUrl2;
        this.SecondaryImageUrl3=SecondaryImageUrl3;
    }


    PostElement(String Username,
            String Category,
            String Date,
            String Post,
            String Image_link)
    {
        this.Username=Username;
        this.Category=Category;
        this.Date=Date;
        this.Post=Post;
        this.Image_link=Image_link;
    }

    public String getUsername() {
        return Username;
    }

    public String getCategory() {
        return Category;
    }

    public String getDate() {
        return Date;
    }

    public String getPost() {
        return Post;
    }

    public String getImage_link() {
        return Image_link;
    }
}
