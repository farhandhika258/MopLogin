package com.test.farhandhika.moplogin;

/**
 * Created by farhandhika on 20/12/17.
 */

public class Book {

    private int id;
    private String title;
    private String author;
    private String acctoken;
    //private String tokentype;
    //private String scope;


    public Book(){}

    public Book(String acctoken)
    {
        super();
        this.title = title;
        this.author = author;
        this.acctoken = acctoken;
        //this.expires = expires;
        //this.tokentype = tokentype;
        //this.scope = scope;
    }

    //getters & setters

    @Override
    public String toString() {
        return "Book [id= "+ id + ", access_token=" + acctoken  + "]";
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAcctoken() {
        return acctoken;
    }





    public void setId(int id) {
        this.id = id;
    }

    public void setAcctoken(String acctoken) {
        this.acctoken = acctoken;
    }



    public int getId() {
        return id;
    }
}