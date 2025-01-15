package com.team5.nbe341team05.domain.menu.repository;

import com.team5.nbe341team05.domain.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
