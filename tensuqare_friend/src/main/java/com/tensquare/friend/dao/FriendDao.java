package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String>, JpaSpecificationExecutor<Friend> {

    @Query(value = "SELECT * FROM tb_friend WHERE userid=? AND friendid=?",nativeQuery = true)
    public Friend selectFriend(String userid , String friendid);

}
