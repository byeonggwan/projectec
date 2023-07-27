package com.ecommerce.projectec.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserMapper{
    private final UserMapper userMapper;

    @Override
    public void userInsert(HashMap<String, Object> user) {
        if (user == null)
            return;
        // 비밀번호 인코딩
        // user.put("userPwd", passwordEncoder.encode((String)user.get("userPwd")));
        userMapper.userInsert(user);
        return;
    }

    @Override
    public List<HashMap<String, Object>> userSelectAll() {
        List<HashMap<String, Object>> users = userMapper.userSelectAll();

        if (users == null)
            return null;
        return users;
    }

    @Override
    public HashMap<String, Object> userSelectByEmail(String userEmail) {
        HashMap<String, Object> user = userMapper.userSelectByEmail(userEmail);

        if (user == null)
            return null;
        return user;
    }
}
