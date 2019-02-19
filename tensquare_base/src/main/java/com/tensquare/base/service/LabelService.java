package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public void add(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }
    public List<Label> findAll(){
        return labelDao.findAll();
    }
    public Label findById(String id){
         Label label = labelDao.getOne(id);
        return label;
    }
    public void update(Label label){
        labelDao.save(label);
    }
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label){

        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate =cb.like(root.get("labelname"), "%"+label.getLabelname()+"%");
                    list.add(predicate);
                }else if (label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state"), label.getState());
                    list.add(predicate);
                }
                Predicate[]arr=new Predicate[list.size()];
                list.toArray(arr);
                return cb.and(arr);
            }
        });
    }

    public Page<Label> pageQuary(Label label,int page,int size){

        Pageable pageable = new PageRequest(page-1, size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname"), "%"+label.getLabelname()+"%");
                    list.add(predicate);
                }
                Predicate[]arr = new Predicate[list.size()];
                list.toArray(arr);
                return cb.and(arr);
            }
        }, pageable);
    }
}
