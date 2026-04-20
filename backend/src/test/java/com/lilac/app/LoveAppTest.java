package com.lilac.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void doChat() {
        String chatId = UUID.randomUUID().toString();
        String content = "你好，我是lilac";
        String answer = loveApp.doChat(content, chatId);

        content = "我想让对象(xxx)更爱我";
        answer = loveApp.doChat(content, chatId);
        Assertions.assertNotNull(answer);

        content = "我的对象是谁来着，刚刚好像给你说过，帮我回忆一下";
        answer = loveApp.doChat(content, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithRAG() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer = loveApp.doChatWithRAG(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        String message = "帮我搜索一些可爱的图片";
        String answer =  loveApp.doChatWithMCP(message, chatId);
        Assertions.assertNotNull(answer);
    }

}