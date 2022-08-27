package com.nac.chatroom.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage {
    private Integer onlineCount;
    private Boolean isSystemMessage;
    private Object message;
    private String fromUsername;
    private String toUsername;
    private Set<String> onlineUsernames;

    public Boolean getIsSystemMessage() {
        return isSystemMessage;
    }

}
