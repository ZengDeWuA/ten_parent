package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {
    public void streamAllByCount();
}
