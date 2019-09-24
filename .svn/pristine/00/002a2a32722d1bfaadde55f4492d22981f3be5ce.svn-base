package com.bochat.app.common.router;

import com.bochat.app.model.bean.TokenEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 16:12
 * Description :
 */

public class RouterFastSpeed extends AbstractRouter {
    public static final String PATH ="/path/RouterFastSpeed";
    
    private TokenEntity tokenEntity;
    
    private boolean isStartToken;

    public RouterFastSpeed() {
    }

    public RouterFastSpeed(TokenEntity tokenEntity, boolean isStartToken) {
        this.tokenEntity = tokenEntity;
        this.isStartToken = isStartToken;
    }

    public TokenEntity getTokenEntity() {
        return tokenEntity;
    }

    public void setTokenEntity(TokenEntity tokenEntity) {
        this.tokenEntity = tokenEntity;
    }

    public boolean isStartToken() {
        return isStartToken;
    }

    public void setStartToken(boolean startToken) {
        isStartToken = startToken;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
