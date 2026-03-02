package com.city.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfileMediaRepository extends JpaRepository<ProfileMedia, Long> {
    List<ProfileMedia> findByProfile_Id(Long profileId);
    void deleteByProfile_IdAndId(Long profileId, Long mediaId);
}