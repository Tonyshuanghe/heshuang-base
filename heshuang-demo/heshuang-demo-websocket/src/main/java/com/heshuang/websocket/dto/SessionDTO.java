package com.heshuang.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/11/7 16:54
 * Description: sessionTDO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private String userId;
    private Session session;
}
