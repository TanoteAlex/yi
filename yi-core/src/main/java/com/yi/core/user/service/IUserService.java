//package com.yi.core.user.service;
//
//import com.yi.core.common.ObjectChooseQuery;
//import com.yi.core.user.domain.bo.UserBo;
//import com.yi.core.user.domain.entity.User;
//import com.yi.core.user.domain.simple.UserSimple;
//import com.yi.core.user.domain.vo.UserListVo;
//import com.yi.core.user.domain.vo.UserVo;
//import com.yihz.common.convert.EntityListVoBoSimpleConvert;
//import com.yihz.common.persistence.Pagination;
//import org.springframework.data.domain.Page;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
//public interface IUserService {
//    /**
//     * 查找某个部门的员工
//     *
//     * @param deptId
//     * @return
//     */
//    List<User> findByDept(int deptId);
//
//    /**
//     * 按条件查询员工实体, 注意该方法只限于各个Service调用, 不能在Controller调用
//     *
//     * @param query
//     * @return
//     */
//    Page<User> query(Pagination<User> query);
//
//    Page<UserVo> queryVo(Pagination<UserVo> query);
//
//    Page<UserListVo> queryListVo(Pagination<UserListVo> query);
//
//    List<UserSimple> queryObjectsByConditions(ObjectChooseQuery chooseQuery);
//
//    Page<UserSimple> querySimple(Pagination query);
//
//    Page<UserBo> queryBo(Pagination<UserBo> query);
//
//    /**
//     * 按ID获取员工实体, 注意该方法只限于各个Service调用, 不能在Controller调用
//     *
//     * @param userId
//     * @return
//     */
//    User getById(int userId);
//
//    /**
//     * 按ID获取员工实体, 注意该方法只限于各个Service调用, 不能在Controller调用
//     *
//     * @param userIds
//     * @return
//     */
//    Set<User> findByIds(int... userIds);
//
//    /**
//     * 按ID获取员工实体, 注意该方法只限于各个Service调用, 不能在Controller调用
//     *
//     * @param userIds
//     * @return
//     */
//    Set<User> findByIds(Collection<Integer> userIds);
//
//    UserBo getBoById(int userId);
//
//    UserVo getVoById(int userId);
//
//    Collection<UserSimple> findSimpleByIds(Set<Integer> userIds);
//
//    UserSimple getSimpleById(int userId);
//
//    /**
//     * 增加员工
//     *
//     * @param userBo
//     * @return
//     */
//    User add(UserBo userBo);
//
//    /**
//     * 修改员工资料, 不包括手机
//     *
//     * @param userBo
//     * @return
//     */
//    User update(UserBo userBo);
//
//    /**
//     * 删除员工
//     *
//     * @param userId
//     */
//    void removeById(int userId);
//
//    /**
//     * 添加员工至指定部门, 重复添加时忽略不报错
//     *
//     * @param userIdList
//     * @param deptId
//     */
//    void addUsers2Dept(List<Integer> userIdList, Integer deptId);
//
//    /* ---------------------- 实体, VO, BO, Simple 转换 ---------------------- */
//    EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> getUserConvert();
//}
