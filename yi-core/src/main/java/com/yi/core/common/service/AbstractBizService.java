//package com.yi.core.common.service;
//
//import com.google.common.collect.Sets;
//import com.yi.core.auth.model.SessionData;
//import com.yi.core.auth.session.ThreadLocalSession;
////import com.yi.core.dept.domain.bo.DeptBo;
////import com.yi.core.dept.domain.entity.Dept;
////import com.yi.core.dept.domain.simple.DeptSimple;
////import com.yi.core.dept.domain.vo.DeptListVo;
////import com.yi.core.dept.domain.vo.DeptVo;
////import com.yi.core.dept.service.IDeptService;
////import com.yi.core.user.domain.bo.UserBo;
////import com.yi.core.user.domain.entity.User;
////import com.yi.core.user.domain.simple.UserSimple;
////import com.yi.core.user.domain.vo.UserListVo;
////import com.yi.core.user.domain.vo.UserVo;
////import com.yi.core.user.service.IUserService;
//import com.yi.core.permission.domain.entity.User;
//import com.yi.core.permission.service.IDeptService;
//import com.yi.core.permission.service.IUserService;
//import com.yihz.common.convert.BeanConvertManager;
//import com.yihz.common.convert.EntityListVoBoSimpleConvert;
//import com.yihz.common.utils.date.MiscUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.ArrayUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
//public abstract class AbstractBizService {
//    @Autowired
//    protected BeanConvertManager beanConvertManager;
//
//    protected abstract IDeptService getDeptService();
//
//    protected abstract IUserService getUserService();
//
//    /**
//     * 获取服务器时间的年度
//     *
//     * @return
//     */
//    protected int getCurrentYear() {
//        Calendar calendar = Calendar.getInstance();
//        return calendar.get(Calendar.YEAR);
//    }
//
//    /**
//     * 获取当前登录员工
//     *
//     * @return
//     */
//    protected User getCurrentUser() {
//        SessionData sessionData = ThreadLocalSession.getSessionData();
//        if (sessionData == null) {
//            return null;
//        }
//
//        User user = getUserService().getById(sessionData.getId());
//        return user;
//    }
//
//    /**
//     * 获取指定ID的员工资料
//     *
//     * @param userId
//     * @return
//     */
//    protected User getUserById(int userId) {
//        if (userId <= 0) {
//            return null;
//        }
//        return getUserService().getById(userId);
//    }
//
//    /**
//     * 获取指定ID的部门资料
//     *
//     * @param deptId
//     * @return
//     */
//    protected Dept getDeptById(int deptId) {
//        if (deptId <= 0) {
//            return null;
//        }
//        return getDeptService().getById(deptId);
//    }
//
//    /**
//     * 按员工ID获取员工对象, 自动去除重复的数据
//     *
//     * @param userIds
//     * @return
//     */
//    protected Set<User> toUsers(int... userIds) {
//        if (ArrayUtils.isEmpty(userIds)) {
//            return null;
//        }
//        return getUserService().findByIds(userIds);
//    }
//
//    /**
//     * 按员工ID获取员工对象, 自动去除重复的数据
//     *
//     * @param userIds
//     * @return
//     */
//    protected Set<User> toUsers(Collection<Integer> userIds) {
//        if (CollectionUtils.isEmpty(userIds)) {
//            return null;
//        }
//        return getUserService().findByIds(userIds);
//    }
//
//    /**
//     * 按UserSimple获取员工对象, 自动去除重复的数据
//     *
//     * @param userSimples
//     * @return
//     */
//    protected List<User> getUserBySimples(Collection<UserSimple> userSimples) {
//        if (CollectionUtils.isEmpty(userSimples)) {
//            return null;
//        }
//        return getUserConvert().getEntitiesBySimple(userSimples);
//    }
//
//    /**
//     * 按UserSimple获取员工对象
//     *
//     * @param userSimple
//     * @return
//     */
//    protected User getUserBySimple(UserSimple userSimple) {
//        if (userSimple == null) {
//            return null;
//        }
//        return getUserConvert().getEntityBySimple(userSimple);
//    }
//
//    /**
//     * 按部门ID获取部门对象, 自动去除重复的数据
//     *
//     * @param deptIds
//     * @return
//     */
//    protected Set<Dept> toDepts(int... deptIds) {
//        if (ArrayUtils.isEmpty(deptIds)) {
//            return null;
//        }
//        return getDeptService().findByIds(deptIds);
//    }
//
//    /**
//     * 按部门ID获取部门对象, 自动去除重复的数据
//     *
//     * @param deptIds
//     * @return
//     */
//    protected Set<Dept> toDepts(Collection<Integer> deptIds) {
//        if (CollectionUtils.isEmpty(deptIds)) {
//            return null;
//        }
//        return getDeptService().findByIds(deptIds);
//    }
//
//    /**
//     * 按DeptSimple获取部门对象, 自动去除重复的数据
//     *
//     * @param deptSimples
//     * @return
//     */
//    protected List<Dept> getDeptBySimples(Collection<DeptSimple> deptSimples) {
//        if (CollectionUtils.isEmpty(deptSimples)) {
//            return null;
//        }
//        return getDeptConvert().getEntitiesBySimple(deptSimples);
//    }
//
//    /**
//     * 按DeptSimple获取部门对象
//     *
//     * @param deptSimple
//     * @return
//     */
//    protected Dept getDeptBySimple(DeptSimple deptSimple) {
//        if (deptSimple == null) {
//            return null;
//        }
//        return getDeptConvert().getEntityBySimple(deptSimple);
//    }
//
//    /**
//     * 将部门集合转换为 DeptSimple 集合
//     *
//     * @param depts
//     * @return
//     */
//    protected List<DeptSimple> toDeptSimples(Collection<Dept> depts) {
//        if (CollectionUtils.isEmpty(depts)) {
//            return null;
//        }
//        return getDeptConvert().toSimples(depts);
//    }
//
//    /**
//     * 将部门转换为 DeptSimple
//     *
//     * @param dept
//     * @return
//     */
//    protected DeptSimple toDeptSimple(Dept dept) {
//        if (dept == null) {
//            return null;
//        }
//        return getDeptConvert().toSimple(dept);
//    }
//
//
//    /**
//     * 将员工集合转换为 UserSimple 集合
//     *
//     * @param users
//     * @return
//     */
//    protected List<UserSimple> toUserSimples(Collection<User> users) {
//        if (CollectionUtils.isEmpty(users)) {
//            return null;
//        }
//        return getUserConvert().toSimples(users);
//    }
//
//    /**
//     * 将员工转换为 UserSimple
//     *
//     * @param user
//     * @return
//     */
//    protected UserSimple toUserSimple(User user) {
//        if (user == null) {
//            return null;
//        }
//        return getUserConvert().toSimple(user);
//    }
//
//    /**
//     * 获取部门数据转换器
//     *
//     * @return
//     */
//    protected EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> getDeptConvert() {
//        return getDeptService().getDeptConvert();
//    }
//
//    /**
//     * 获取员工数据转换器
//     *
//     * @return
//     */
//    protected EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> getUserConvert() {
//        return getUserService().getUserConvert();
//    }
//
//
//    /**
//     * 将集合转换为Set, 主要用于去重复
//     *
//     * @param items
//     * @param <E>
//     * @return
//     */
//    protected <E> Set<E> toSet(Collection<E> items) {
//        return MiscUtils.toSet(items);
//    }
//}
