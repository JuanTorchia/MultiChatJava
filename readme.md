# Multi Chat JAVA


## Technical documentation

This Java APP is to make a CHAT with multiple users.
We use ServerSocket class and Socket to make the connection. the use a Worker to handle multiple connections.
We use Logback Classic & core module. Apache Commons Lang for implement login and logoff commands.

## Develop process 

1. Create chat Server. handle multiple connections.
2. Handling User Presence (Online & Offline Status)


## Protocols develop

We have User & Server for use the chat.

1. User communicate to Server

    * user can log or logoff
    * status of the user

2. The Server communicate to User

    * communicate to other user come online or offline

3. Users communicate to another Users

    * users can send direct messages to other users 
    * users can send group messages (the groups messages work with a #)
    * users can broadcast messages


Commands that use for login or logoff:

    login <user> <password>
    logoff

    msg <user> body....
    guest: "msg jim Hello World" <--- sent
    jim: "msg guest Hello World" <--- recv


    #topic <--- chatroom / group chat 
    join #topic
    leave #topic
    send msg #topic body---
    recive msg #topic:<login> body ...


## Related Articles

### Libraries that we use
Commons Lang
https://commons.apache.org/proper/commons-lang/

### Java Class

ServerSocket
https://docs.oracle.com/javase/8/docs/api/java/net/ServerSocket.html

Socket
https://docs.oracle.com/javase/8/docs/api/java/net/Socket.html


