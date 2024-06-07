package com.elice.meetstudy.domain.audit;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.util.EntityFinder;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    private final EntityFinder entityFinder;

    @Override
    public Optional<String> getCurrentAuditor() {
        // 접근한 유저 정보 가져오는 로직
        User user = entityFinder.getUser();
        if (user != null) {
            if (user.getNickname() != null)
                return Optional.of(user.getNickname());
            return Optional.of(user.getUsername());
        }
        return Optional.empty();
    }
}