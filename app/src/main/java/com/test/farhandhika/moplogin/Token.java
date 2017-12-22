package com.test.farhandhika.moplogin;

import android.support.annotation.IntDef;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by farhandhika on 21/12/17.
 */

@Entity(nameInDb = "token")

public class Token {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "accesstoken")
    private String accesstoken;

    @Property(nameInDb = "expiresin")
    private String expiresin;

    @Property(nameInDb = "tokentype")
    private String tokentype;

    @Property(nameInDb = "scope")
    private String scope;

    @Property(nameInDb = "refreshtoken")
    private String refreshtoken;

    @Generated(hash = 371418101)
    public Token(Long id, String accesstoken, String expiresin, String tokentype,
            String scope, String refreshtoken) {
        this.id = id;
        this.accesstoken = accesstoken;
        this.expiresin = expiresin;
        this.tokentype = tokentype;
        this.scope = scope;
        this.refreshtoken = refreshtoken;
    }

    @Generated(hash = 79808889)
    public Token() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccesstoken() {
        return this.accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getExpiresin() {
        return this.expiresin;
    }

    public void setExpiresin(String expiresin) {
        this.expiresin = expiresin;
    }

    public String getTokentype() {
        return this.tokentype;
    }

    public void setTokentype(String tokentype) {
        this.tokentype = tokentype;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshtoken() {
        return this.refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }
}
