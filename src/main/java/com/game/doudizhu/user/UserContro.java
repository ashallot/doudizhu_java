package com.game.doudizhu.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserContro {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login")
    public Map login(@RequestParam("username") Integer username,@RequestParam("password") String password){
        Map params = new HashMap();
        System.out.println(username+','+password);
        if(userRepository.findByUsername(username).getUsername() == null){
            params.put("status",0);
            params.put("message","this username isn't exist");
        }else if(!userRepository.findByUsername(username).getPassword().equals(password)){
            params.put("status",2);
            params.put("message","password is wrong");
        }else if(userRepository.findByUsername(username).getPassword().equals(password)){
            params.put("status",1);
            params.put("username",username);
            params.put("core",userRepository.findByUsername(username).getCore());
            params.put("message","login success");
        }
        return params;
    }

    @PostMapping(value="/register")
    public Map register(@RequestParam("username") Integer username,@RequestParam("password") String password,@RequestParam("repassword") String repassword){
        Map params = new HashMap();

        if (userRepository.findByUsername(username).getUsername()!= null){
            params.put("status",0);
            params.put("message","this username is used!");
        }else{
            if(password.equals(repassword)){
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setCore(0);
                user.setRoomid(0);
                userRepository.save(user);
            }
            if(userRepository.findByUsername(username).getUsername()!=null){
                params.put("status",1);
                params.put("message","regieter success");
            }else{
                params.put("status",2);
                params.put("message","there're some error");
            }
        }

        return params;
    }

    @PutMapping(value = "/safe")
    public Map safe(@RequestBody Map<String,Object> paramaters){
        Map params = new HashMap();
        Integer username = Integer.parseInt(paramaters.get("username").toString());
        String newpassword = paramaters.get("newpassword").toString();
        String oldpassword = paramaters.get("oldpassword").toString();
        String safestatus = "false";

        if(userRepository.findByUsername(username).getPassword().equals(oldpassword)){
            User user = userRepository.findByUsername(username);
            user.setPassword(newpassword);
            userRepository.save(user);
            if(userRepository.findByUsername(username)!=null){
                safestatus = "true";
            }
        }

        params.put("safestatus",safestatus);
        return params;
    }

}
