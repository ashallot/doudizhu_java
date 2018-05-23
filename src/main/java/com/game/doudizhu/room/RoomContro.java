package com.game.doudizhu.room;

import com.game.doudizhu.room.RoomRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/room")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomContro {

    @Autowired
    private RoomRespository roomRespository;

    @PostMapping(value = "/create_private_room")
    public Map login(@RequestParam("userid") Integer user1){
        Map params = new HashMap();
        int rid = 0;
        for(;;){
            rid = (int) ((Math.random()*9+1)*100000);
            if(roomRespository.findByRoomid(rid)==null){
                break;
            }
        }
        System.out.println(user1+ "》》》" + rid );
        Room room = new Room();
        room.setRoomid(rid);
        room.setUser1(user1);
        room.setUser2(0);
        room.setUser3(0);
        roomRespository.save(room);
        params.put("success","create room success");
        params.put("roomid",rid);
        return params;
    }
    @PutMapping(value = "/leave_room")
//    @RequestBody Map<String,Object> paramaters
    public Map leave(@RequestParam("userid") Integer username,@RequestParam("roomid") Integer roomid){
        Map params = new HashMap();
//        Integer username = Integer.parseInt(paramaters.get("userid").toString());
//        Integer roomid = Integer.parseInt(paramaters.get("roomid").toString());

        if(roomRespository.findByRoomid(roomid).getUser1().equals(username)){
            Room room = roomRespository.findByRoomid(roomid);
            room.setUser1(0);
            roomRespository.save(room);
        }
        if(roomRespository.findByRoomid(roomid).getUser2().equals(username)){
            Room room = roomRespository.findByRoomid(roomid);
            room.setUser2(0);
            roomRespository.save(room);
        }
        if(roomRespository.findByRoomid(roomid).getUser3().equals(username)){
            Room room = roomRespository.findByRoomid(roomid);
            room.setUser3(0);
            roomRespository.save(room);
        }

        params.put("success","leave success");
        return params;
    }

}
