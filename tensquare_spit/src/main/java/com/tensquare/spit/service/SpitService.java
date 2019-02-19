package com.tensquare.spit.service;

import com.tensquare.spit.Dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

@Service
@Transactional
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setThumbup(0);
        spitDao.save(spit);
    }
    @CacheEvict(value = "spit",key = "#id")
    public void deleteBtId(String id){
        spitDao.deleteById(id);
    }

    @CacheEvict(value = "spit",key = "#spit._id")
    public Spit update(Spit spit){
        return spitDao.save(spit);
    }

    @Cacheable(value = "spit",key = "#id")
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    public Page<Spit> findByParentId(String ParentId,int page,int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return spitDao.findByParentid(ParentId, pageable);
    }

    @CacheEvict(value = "spit",key = "#id")
    public void thumpubAdd(String id){
        //第一种方式
        /*Spit spit = spitDao.findById(id).get();
        spit.setThumbup(spit.getThumbup()+1);
        spitDao.save(spit);*/
        //第二种方式
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
