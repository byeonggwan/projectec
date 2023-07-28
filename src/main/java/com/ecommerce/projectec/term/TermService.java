package com.ecommerce.projectec.term;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/*
 * @author 강병관
 *
 * 권한 서비스
 * HashMap Keys: ["TERM_ID_NO", "USER_ID_NO", "TERM_NAME", "TERM_FLAG"]
 */
@RequiredArgsConstructor
@Service
public class TermService implements TermMapper{
    private final TermMapper termMapper;


    @Override
    public void insertAll(List<HashMap<String, Object>> terms) {
        if (terms == null)
            return;
        termMapper.insertAll(terms);
        return;
    }

    @Override
    public List<HashMap<String, Object>> selectAllByUserId(long userIdNo) {
        List<HashMap<String, Object>> terms = termMapper.selectAllByUserId(userIdNo);

        if (terms == null)
            return null;
        return terms;
    }
}
