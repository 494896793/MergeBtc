package com.bochat.app.common.router;

import com.bochat.app.model.bean.TokenEntity;
import com.bochat.app.model.bean.TokenListEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 16:40
 * Description :
 */

public class RouterTokenSelect extends AbstractRouter {
    public static final String PATH ="/path/RouterTokenSelect";
    
    private TokenListEntity tokenList;
    private TokenEntity selectToken;
    private boolean isStartToken;
    private boolean isIgnoreBx;
    
    public RouterTokenSelect(TokenListEntity tokenList, boolean isStartToken, boolean isIgnoreBx) {
        this.tokenList = tokenList;
        this.isStartToken = isStartToken;
        this.isIgnoreBx = isIgnoreBx;
    }

    public RouterTokenSelect(TokenListEntity tokenList, TokenEntity selectToken, boolean isStartToken, boolean isIgnoreBx) {
        this.tokenList = tokenList;
        this.selectToken = selectToken;
        this.isStartToken = isStartToken;
        this.isIgnoreBx = isIgnoreBx;
    }

    public TokenEntity getSelectToken() {
        return selectToken;
    }

    public void setSelectToken(TokenEntity selectToken) {
        this.selectToken = selectToken;
    }

    public TokenListEntity getTokenList() {
        return tokenList;
    }

    public void setTokenList(TokenListEntity tokenList) {
        this.tokenList = tokenList;
    }

    public boolean isStartToken() {
        return isStartToken;
    }

    public void setStartToken(boolean startToken) {
        isStartToken = startToken;
    }

    public boolean isIgnoreBx() {
        return isIgnoreBx;
    }

    public void setIgnoreBx(boolean ignoreBx) {
        isIgnoreBx = ignoreBx;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
