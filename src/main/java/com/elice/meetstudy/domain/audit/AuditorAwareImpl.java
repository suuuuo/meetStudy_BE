package com.elice.meetstudy.domain.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
        /**
         * 테스트를 위한 임시코드, jwt 토큰으로 아이디 가져오는 부분으로 대체해야함
         */
        return Optional.of("user");
    }
}
