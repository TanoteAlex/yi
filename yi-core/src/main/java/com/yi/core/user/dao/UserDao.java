//package com.yi.core.user.dao;
//
//import com.yi.core.user.domain.entity.User;
//import com.yihz.common.orm.BaseDao;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//
//public interface UserDao extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User>, BaseDao<User> {
////
////    @Query("select u from User u where u.mobile=?1 or u.email=?1 or u.account=?1")
////    User findByMobileOrEmailOrAccount(String userName);
////
////    User findByAccountAndUserIdNot(String account, int userId);
////
////    User findByMobileAndUserIdNot(String mobile, int userId);
////
////    User findByEmailAndUserIdNot(String email, int userId);
////
////    User findByMobile(String mobile);
////
////    User findByEmail(String email);
////
////    User findByAccount(String account);
////
//    User findByUserCode(String userCode);
//
//}