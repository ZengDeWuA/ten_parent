package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    public void addFriend(String userid,String friendid) {
        Friend friend = friendDao.selectFriend(userid, friendid);
        if (null==friend){
            friend= new Friend();
            friend.setUserid(userid);
            friend.setFriendid(friendid);
            friend.setIslike("0");
            friendDao.save(friend);
        }
    }
}
