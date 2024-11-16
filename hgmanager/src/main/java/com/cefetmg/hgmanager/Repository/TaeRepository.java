package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Tae;
import org.springframework.stereotype.Repository;

@Repository
public interface TaeRepository extends org.springframework.data.jpa.repository.JpaRepository<Tae, Long> {
}
