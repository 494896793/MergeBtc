package com.bochat.app.common.router;

public class RouterSearchApp extends AbstractRouter {

    public static final String PATH = "/path/RouterSearchApp";

    private String searchMessage;

    public RouterSearchApp() {
    }

    public RouterSearchApp(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
