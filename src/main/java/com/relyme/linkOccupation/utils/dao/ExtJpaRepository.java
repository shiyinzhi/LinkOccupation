package com.relyme.linkOccupation.utils.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface ExtJpaRepository<T, ID extends Serializable> extends JpaRepository<T,ID> {

}
