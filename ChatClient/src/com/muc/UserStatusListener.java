package com.muc;

public interface UserStatusListener {
    public void online(String login);
    public void offline(String login);
}
