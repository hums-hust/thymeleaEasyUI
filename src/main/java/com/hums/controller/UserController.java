package com.hums.controller;

import com.hums.dao.UserDao;
import com.hums.entity.User;
import com.hums.service.UserService;
import com.hums.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author hums
 * @Date 2021/3/26 10:52:00
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    /**
    * @Description:  进入增删改查界面
    * @return:
    * @Author: hums
    * @Date: 2021/3/26
    */
    @RequestMapping("/crud")
    public String crud(){
        return "crud";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Map<String,Object> list(final User user, @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "rows",required = false)Integer rows){
        Pageable pageable = new PageRequest(page-1, rows, Sort.Direction.ASC, "id");
        Page<User> pageUser = userDao.findAll(new Specification<User>(){
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate predicate = cb.conjunction();
                if (user != null) {
                    if (StringUtils.isNotEmpty(user.getName())) {
                        predicate.getExpressions().add(cb.like(root.<String>get("name"), "%" + user.getName().trim() + "%"));
                    }
                    if (StringUtils.isNotEmpty(user.getPwd())) {
                        predicate.getExpressions().add(cb.like(root.<String>get("word"), "%" + user.getPwd().trim() + "%"));
                    }
                }
                return predicate;
            }

        }, pageable);
        // ①获取rows
        List<User> list = pageUser.getContent();
        // ②获取count
        Long count = userDao.count(new Specification<User>() {
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate predicate = cb.conjunction();
                if (user != null) {
                    if (StringUtils.isNotEmpty(user.getName())) {
                        predicate.getExpressions().add(cb.like(root.<String>get("name"), "%" + user.getName().trim() + "%"));
                    }
                    if (StringUtils.isNotEmpty(user.getPwd())) {
                        predicate.getExpressions().add(cb.like(root.<String>get("word"), "%" + user.getPwd().trim() + "%"));
                    }
                }
                return predicate;
            }

        });
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("rows", list);
        resultMap.put("total", count);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 添加、修改
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Map<String, Object> save(User user) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        userDao.save(user);
        resultMap.put("success", true);
        return resultMap;
    }
    /**
     * 删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestParam(value = "ids") String ids) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String[] idsStr = ids.split(",");
        //for (int i = 0; i < ids.length; i++) {
        for (int i = 0; i < idsStr.length; i++) {
            userDao.delete(Integer.parseInt(idsStr[i]));
        }
        resultMap.put("success", true);

        return resultMap;
    }

}
