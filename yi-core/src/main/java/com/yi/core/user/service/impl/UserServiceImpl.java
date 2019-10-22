//package com.yi.core.user.service.impl;
//
//import com.google.common.collect.Sets;
//import com.yi.core.auth.model.SessionData;
//import com.yi.core.auth.session.ThreadLocalSession;
//import com.yi.core.common.ObjectChooseQuery;
//import com.yi.core.dept.domain.bo.DeptBo;
//import com.yi.core.dept.domain.entity.Dept;
//import com.yi.core.dept.domain.simple.DeptSimple;
//import com.yi.core.dept.domain.vo.DeptListVo;
//import com.yi.core.dept.domain.vo.DeptVo;
//import com.yi.core.dept.service.IDeptService;
//import com.yi.core.user.dao.UserDao;
//import com.yi.core.user.domain.bo.UserBo;
//import com.yi.core.user.domain.entity.User;
//import com.yi.core.user.domain.simple.UserSimple;
//import com.yi.core.user.domain.vo.UserListVo;
//import com.yi.core.user.domain.vo.UserVo;
//import com.yi.core.user.service.IUserService;
//import com.yi.core.utils.ObjectPathUtils;
//import com.yihz.common.convert.AbstractBeanConvert;
//import com.yihz.common.convert.BeanConvert;
//import com.yihz.common.convert.BeanConvertManager;
//import com.yihz.common.convert.EntityListVoBoSimpleConvert;
//import com.yihz.common.exception.BusinessException;
//import com.yihz.common.persistence.Pagination;
//import com.yihz.common.utils.ValueUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.criteria.Predicate;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//@Service
//@Transactional
//public class UserServiceImpl implements IUserService, InitializingBean {
//    private final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
//
//    @Autowired
//    private BeanConvertManager beanConvertManager;
//
//    @Autowired
//    private IDeptService deptService;
//
//    @Autowired
//    private UserDao userDao;
//
////    @Autowired
////    private DeptUserDao deptUserDao;
//
//    private EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> userConvert;
//
//    @Override
//    public List<User> findByDept(int deptId) {
//        List<User> users = userDao.findAll((root, query, cb) -> {
////            return cb.and(
////                    cb.equal(root.join(User_.deptUsers, JoinType.INNER).get(DeptUser_.dept).get(Dept_.deptId), deptId),
////                    cb.isTrue(root.get(User_.enabled))
////            );
//            return null;
//        });
//        return users;
//    }
//
//    @Override
//    public Page<User> query(Pagination query) {
//        query.setEntityClazz(User.class);
//        query.setPredicate(((root, criteriaQuery, builder, predicates, orders) -> {
//            Map<String, Object> params = query.getParams();
//            int deptId = null != params.get("currentDeptId") ? Integer.valueOf(params.get("currentDeptId").toString()) : 0;
//            if (deptId > 0) {
////                predicates.add(builder.equal(root.join(User_.deptUsers, JoinType.INNER).join(DeptUser_.dept, JoinType.INNER).get(Dept_.deptId), deptId));
//            }
//        }));
//
//        Page<User> page = userDao.findAll(query, query.getPageRequest());
//
//        return page;
//    }
//
//    @Override
//    public Page<UserVo> queryVo(Pagination query) {
//        Page<User> pages = this.query(query);
//
//        List<UserVo> vos = userConvert.toVos(pages.getContent());
//        Page<UserVo> voPage = new PageImpl<UserVo>(vos, query.getPageRequest(), pages.getTotalElements());
//        return voPage;
//    }
//
//    @Override
//    public Page<UserListVo> queryListVo(Pagination query) {
//        Page<User> pages = this.query(query);
//
//        List<UserListVo> vos = userConvert.toListVos(pages.getContent());
//        Page<UserListVo> voPage = new PageImpl<UserListVo>(vos, query.getPageRequest(), pages.getTotalElements());
//        return voPage;
//    }
//
//    @Override
//    public List<UserSimple> queryObjectsByConditions(ObjectChooseQuery chooseQuery) {
//        List<User> users = userDao.findAll((root, query, cb) -> {
//            Predicate predicate = cb.conjunction();
//            int deptId = chooseQuery.getDeptId();
//            if (StringUtils.isNotBlank(chooseQuery.getQueryText())) {
////                predicate = cb.and(predicate, cb.like(root.get(User_.userName), "%" + chooseQuery.getQueryText() + "%"));
//            }
////            predicate = cb.and(predicate, cb.equal(root.join(User_.deptUsers, JoinType.INNER).get(DeptUser_.dept).get(Dept_.deptId), deptId));
////            predicate = cb.and(predicate, cb.isTrue(root.get(User_.enabled)));
////            query.orderBy(cb.asc(root.get(User_.userId)));
//            return predicate;
//        });
//        return userConvert.toSimples(users);
//    }
//
//    @Override
//    public Page<UserSimple> querySimple(Pagination query) {
//        Page<User> pages = this.query(query);
//
//        List<UserSimple> simples = userConvert.toSimples(pages.getContent());
//        Page<UserSimple> listVoPage = new PageImpl<UserSimple>(simples, query.getPageRequest(), pages.getTotalElements());
//        return listVoPage;
//    }
//
//    @Override
//    public Page<UserBo> queryBo(Pagination query) {
//        Page<User> pages = this.query(query);
//
//        List<UserBo> bos = userConvert.toBos(pages.getContent());
//        Page<UserBo> boPage = new PageImpl<UserBo>(bos, query.getPageRequest(), pages.getTotalElements());
//        return boPage;
//    }
//
//    @Override
//    public User getById(int userId) {
//        return userDao.getOne(userId);
//    }
//
//    @Override
//    public Set<User> getOnes(int... userIds) {
//        if (ArrayUtils.isEmpty(userIds)) {
//            return null;
//        }
//
//        Set<User> set = Sets.newLinkedHashSet();
//        for (int index = 0; index < userIds.length; ++index) {
//            User user = userDao.getOne(userIds[index]);
//            if (user != null) {
//                set.add(user);
//            }
//        }
//
//        return set;
//    }
//
//    @Override
//    public Set<User> getOnes(Collection<Integer> userIds) {
//        if (CollectionUtils.isEmpty(userIds)) {
//            return null;
//        }
//
//        Set<User> set = Sets.newLinkedHashSet();
//        for (Integer userId : userIds) {
//            if (userId == null) {
//                continue;
//            }
//
//            User user = userDao.getOne(userId);
//            if (user != null) {
//                set.add(user);
//            }
//        }
//
//        return set;
//    }
//
//    @Override
//    public UserBo getBoById(int userId) {
//        return userConvert.toBo(userDao.getOne(userId));
//    }
//
//    @Override
//    public UserVo getVoById(int userId) {
//        return userConvert.toVo(userDao.getOne(userId));
//    }
//
//    @Override
//    public Collection<UserSimple> findSimpleByIds(Set<Integer> userIds) {
//        Collection<User> users = getOnes(userIds);
//        return userConvert.toSimples(users);
//    }
//
//    @Override
//    public UserSimple getSimpleById(int userId) {
//        return userConvert.toSimple(userDao.getOne(userId));
//    }
//
//    @Override
//    public User add(UserBo userBo) {
//        userBo.setUserId(0);
//        if (StringUtils.isBlank(userBo.getGuid())) {
//            userBo.setGuid(ValueUtils.newGUID());
//        }
//
//        // 校验员工数据是否符合要求
//        validateUser(userBo);
//
//        User user = userConvert.toEntity(userBo);
//
//        User dbUser = userDao.findByUserCode(user.getUserCode());
//        if (dbUser != null) {
//            throw new BusinessException("userCodeExist", "userCode=" + user.getUserCode() + " 已经被 " + dbUser.getUserName() + " 使用");
//        }
//        user.setEnabled(true);
//        user.setState("INIT");
//
//        // 增加员工资料
//        dbUser = userDao.save(user);
//
//        // 保存员工所属部门以及是否部门主管
//        saveDeptUsers(userBo, dbUser);
//
//        return dbUser;
//    }
//
//    @Override
//    public User update(UserBo userBo) {
//        User user = userConvert.toEntity(userBo);
//
//        User dbUser = userDao.getOne(user.getUserId());
//
//        // 不能修改手机, 如果效果手机需要使用 changeUserPhone 方法
////        beanConvertManager.copying(user, dbUser,
////                User_.userCode, User_.userName, User_.email, User_.telephone, User_.position,
////                User_.jobNumber, User_.workPlace, User_.remark,
////                User_.enabled, User_.state, User_.adminType, User_.pinyin
////        );
//
//        dbUser = userDao.save(dbUser);
//
////        List<DeptUser> deptUsers = deptUserDao.getDeptUserByUserId(dbUser.getUserId());
////        deptUserDao.deleteById(deptUsers);
//
//        // 保存员工所属部门以及是否部门主管
//        saveDeptUsers(userBo, dbUser);
//
//        return dbUser;
//    }
//
//    /**
//     * 校验员工资料是否满足要求, 手机号码和邮箱必须唯一
//     *
//     * @param userBo
//     */
//    private void validateUser(UserBo userBo) {
//        int userId = userBo.getUserId();
//        String mobile = StringUtils.trimToNull(userBo.getPhone());
//        if (StringUtils.isNotBlank(mobile)) {
//            long count = userDao.count((root, query, cb) -> {
////                if (userId <= 0) {
////                    return cb.equal(root.get(User_.phone), mobile);
////                }
////
////                return cb.and(
////                        cb.equal(root.get(User_.phone), mobile),
////                        cb.notEqual(root.get(User_.userId), userId)
////                );
//                return null;
//            });
//            if (count > 0) {
//                throw new BusinessException("MobileDuplicate", "手机号码已经被其他员工使用");
//            }
//        }
//
//        String email = StringUtils.trimToNull(userBo.getEmail());
//        if (StringUtils.isNotBlank(email)) {
//            long count = userDao.count((root, query, cb) -> {
////                if (userId <= 0) {
////                    return cb.equal(root.get(User_.email), email);
////                }
////                return cb.and(
////                        cb.equal(root.get(User_.email), email),
////                        cb.notEqual(root.get(User_.userId), userId)
////                );
//                return null;
//            });
//            if (count > 0) {
//                throw new BusinessException("EmailDuplicate", "邮箱已经被其他员工使用");
//            }
//        }
//
//        // 设置去除空格后的内容
//        userBo.setPhone(mobile);
//        userBo.setEmail(email);
//    }
//
//    /**
//     * 根据工作部门和负责部门增加 DeptUser 记录
//     *
//     * @param userBo
//     * @param user
//     */
//    private void saveDeptUsers(UserBo userBo, User user) {
//        // 保存员工所属部门
//        Set<Integer> deptIds = Sets.newHashSet();
//        Set<Integer> deptManages = Sets.newHashSet();
//
//        Collection<DeptSimple> workDepts = userBo.getWorkDepts();
//        if (workDepts != null && !workDepts.isEmpty()) {
//            for (DeptSimple dept : workDepts) {
//                deptIds.add(dept.getDeptId());
//            }
//        }
//
//        int index = 0;
////        for (Integer deptId : deptIds) {
////            DeptUser deptUser = new DeptUser();
//////            deptUser.setUser(user);
//////            deptUser.setDept(deptService.getById(deptId));
////            deptUser.setId(new DeptUserId(deptId, user.getUserId()));
////            deptUser.setManager(deptManages.contains(deptId));
////            deptUser.setShowOrder(++index);
////            deptUserDao.save(deptUser);
////        }
//    }
//
//    @Override
//    public void removeById(int userId) {
//        SessionData sessionData = ThreadLocalSession.getSessionData();
//
//        User user = userDao.getOne(userId);
//        if (user == null) {
//            throw new BusinessException("notFoundData", "找不到需要删除的员工资料");
//        }
//
//        userDao.deleteById(user);
//
//    }
//
//    @Override
//    public void addUsers2Dept(List<Integer> userIdList, Integer deptId) {
//        if (CollectionUtils.isEmpty(userIdList) || deptId == null) {
//            return;
//        }
//
//        Dept dept = deptService.getById(deptId);
//
//        Set<User> users = Sets.newHashSet();
//        for (Integer userId : userIdList) {
//            User user = userDao.getOne(userId);
//            if (user != null) {
//                users.add(user);
//            }
//        }
//
////        for (User user : users) {
////            DeptUserId id = new DeptUserId(deptId, user.getUserId());
////            if (deptUserDao.getOne(id) != null) {
////                continue;
////            }
////
////            DeptUser deptUser = new DeptUser();
//////            deptUser.setUser(user);
//////            deptUser.setDept(dept);
////            deptUser.setId(id);
////            deptUser.setManager(false);
////            deptUser.setShowOrder(0);
////            deptUserDao.save(deptUser);
////        }
//    }
//
//    /* --------------------- 实体,VO,BO 转换实现 --------------------- */
//
//    /**
//     * 获取员工的对象路径
//     *
//     * @param user
//     * @return
//     */
//    private String getUserObjectPath(User user) {
//        return ObjectPathUtils.concatObjectPath("USER", user.getUserId());
//    }
//
//    @Override
//    public EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> getUserConvert() {
//        return userConvert;
//    }
//
//    protected void initConvert() {
//        this.userConvert = new EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo>(beanConvertManager) {
//            @Override
//            protected BeanConvert<User, UserVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<User, UserVo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(UserVo userVo, User user) {
//                        EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> deptConvert = deptService.getDeptConvert();
////                        userVo.setWorkDepts(deptConvert.toSimples(deptService.findWorkDeptByUser(user.getUserId())));
//
//                        userVo.setObjectPath(getUserObjectPath(user));
//                    }
//                };
//            }
//
//            @Override
//            protected BeanConvert<User, UserBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<User, UserBo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(UserBo userBo, User user) {
//                        EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> deptConvert = deptService.getDeptConvert();
////                        userBo.setWorkDepts(deptConvert.toSimples(deptService.findWorkDeptByUser(user.getUserId())));
//
//                        userBo.setObjectPath(getUserObjectPath(user));
//                    }
//                };
//            }
//
//            @Override
//            protected BeanConvert<User, UserListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<User, UserListVo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(UserListVo userListVo, User user) {
//                        userListVo.setObjectPath(getUserObjectPath(user));
//                    }
//                };
//            }
//
//            @Override
//            protected BeanConvert<UserBo, User> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<UserBo, User>(beanConvertManager) {
//                };
//            }
//
//            @Override
//            protected BeanConvert<User, UserSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<User, UserSimple>(beanConvertManager) {
//                };
//            }
//
//            @Override
//            protected BeanConvert<UserSimple, User> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<UserSimple, User>(beanConvertManager) {
//                    @Override
//                    public User convert(UserSimple userSimple) throws BeansException {
//                        return userDao.getOne(userSimple.getUserId());
//                    }
//                };
//            }
//        };
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        initConvert();
//    }
//}
