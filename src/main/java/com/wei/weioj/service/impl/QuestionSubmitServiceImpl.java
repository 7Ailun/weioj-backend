package com.wei.weioj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.weioj.common.ErrorCode;
import com.wei.weioj.constant.CommonConstant;
import com.wei.weioj.exception.BusinessException;
import com.wei.weioj.mapper.QuestionSubmitMapper;
import com.wei.weioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.wei.weioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.wei.weioj.model.entity.*;
import com.wei.weioj.model.enums.QuestionSubmitLanguageEnum;
import com.wei.weioj.model.enums.QuestionSubmitStatusEnum;
import com.wei.weioj.model.vo.QuestionSubmitVO;
import com.wei.weioj.model.vo.QuestionSubmitVO;
import com.wei.weioj.model.vo.UserVO;
import com.wei.weioj.service.QuestionService;
import com.wei.weioj.service.QuestionSubmitService;
import com.wei.weioj.service.QuestionSubmitService;
import com.wei.weioj.service.UserService;
import com.wei.weioj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 99301
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2024-04-13 13:25:43
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {
    @Resource
    private UserService userService;
    @Resource
    private QuestionService questionService;

    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        String code = questionSubmitAddRequest.getCode();
        if (StringUtils.isBlank(code)) {

            throw new BusinessException(ErrorCode.PARAMS_ERROR, "代码不能为空");
        }

        Long userId = loginUser.getId();
        // 插入数据
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setCode(code);
        questionSubmit.setLanguage(language);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);

        boolean save = this.save(questionSubmit);
        if (!save) {

            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入失败，系统错误");
        }

        return questionSubmit.getId();
    }

    /**
     * 获取查询包装类
     *
     * @param postQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest postQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (postQueryRequest == null) {
            return queryWrapper;
        }
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        Long userId = postQueryRequest.getUserId();
        String language = postQueryRequest.getLanguage();
        Integer status = postQueryRequest.getStatus();
        Long questionId = postQueryRequest.getQuestionId();

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotEmpty(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(QuestionSubmitStatusEnum.getEnumByValue(status)), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 脱敏：仅本人和管理员能看见自己（提交 userId 和登录用户 id 不同）提交的代码
        long userId = loginUser.getId();
        if (userId != questionSubmit.getUserId() && !userService.isAdmin(loginUser)) {
            questionSubmitVO.setCode("");
        }
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 关联查询题目信息
        List<Long> questionIdList = questionSubmitList.stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toList());
        Map<Long, List<Question>> questionIdQuestionMap = questionService.listByIds(questionIdList).stream()
                .collect(Collectors.groupingBy(Question::getId));
        // 填充信息
        User loginUser = userService.getLoginUser(request);
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            QuestionSubmitVO questionSubmitVO = getQuestionSubmitVO(questionSubmit,loginUser);
            Long userId = questionSubmit.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            Question question = null;
            Long questionId = questionSubmit.getQuestionId();
            if (questionIdList.contains(questionId)) {
                question = questionIdQuestionMap.get(questionId).get(0);
            }
            questionSubmitVO.setUserVO(userService.getUserVO(user));
            questionSubmitVO.setQuestionVO(questionService.getQuestionVO(question, request));
            return questionSubmitVO;
        }).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }
}




