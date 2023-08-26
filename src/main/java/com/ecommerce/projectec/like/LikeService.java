package com.ecommerce.projectec.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeMapper likeMapper;

    public boolean insert(Long userIdNo, Long productId) {
        if (userIdNo == null || productId == null ) {
            return false;
        }

        likeMapper.insertById(userIdNo, productId);
        return true;
    }

    public boolean delete(Long userIdNo, Long productId) {
        if (userIdNo == null || productId == null ) {
            return false;
        }

        likeMapper.deleteById(userIdNo, productId);
        return true;
    }

    public Integer size(Long userIdNo) {
        if (userIdNo == null)
            return 0;

        return likeMapper.selectCountById(userIdNo);
    }
}
