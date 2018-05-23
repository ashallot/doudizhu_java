package com.game.doudizhu.room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRespository extends JpaRepository<Room,Integer> {
    Room findByRoomid(Integer roomid);
    Room findByUser1(Integer user1);
    Room findByUser2(Integer user2);
    Room findByUser3(Integer user3);
}