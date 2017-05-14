package com.lavkesh.repository;

import com.lavkesh.entity.User;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u.userId FROM User u where u.userId in :userIds order by u.userId")
  Set<Long> getExistingIds(@Param("userIds") Collection<Long> userIds);
}
