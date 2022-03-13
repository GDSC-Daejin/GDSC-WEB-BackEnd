package Gdsc.web.repository.memberPortfolioUrl;

import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.MemberPortfolioUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberPortfolioUrl extends JpaRepository<MemberPortfolioUrl, Integer> {
    void deleteMemberPortfolioUrlsByMemberInfo(MemberInfo memberInfo);
}
