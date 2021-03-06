package com.swim.community.dao;

import com.swim.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    // 查询当前用户的会话列表，针对每个会话只返回一条最新的私信
    List<Message> selectConversations(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    int selectConversationCount(@Param("userId") int userId);

    // 查询某个会话所包含的私信列表
    List<Message> selectLetters(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    int selectLetterCount(@Param("conversationId") String conversationId);

    // 查询未读私信数量
    int selectLetterUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    int insertMessage(Message message);

    int updateStatus(@Param("ids") List<Integer> ids, @Param("status") int status);

    // 查询某个主题下的最新通知
    Message selectLatestNotice(@Param("userId") int userId, @Param("topic") String topic);

    // 查询某个主题包含的通知数量
    int selectNoticeCount(@Param("userId") int userId, @Param("topic") String topic);

    // 查询未读的通知的数量
    int selectNoticeUnreadCount(@Param("userId") int userId, @Param("topic") String topic);

    // 查询某个主题所包含的通知列表
    List<Message> selectNotices(@Param("userId") int userId, @Param("topic") String topic, @Param("offset") int offset, @Param("limit") int limit);

}
