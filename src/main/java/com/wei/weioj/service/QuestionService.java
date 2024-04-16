package com.wei.weioj.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.weioj.model.dto.question.QuestionAddRequest;
import com.wei.weioj.model.dto.question.QuestionEditRequest;
import com.wei.weioj.model.dto.question.QuestionQueryRequest;
import com.wei.weioj.model.dto.question.QuestionUpdateRequest;
import com.wei.weioj.model.entity.Question;
import com.wei.weioj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 99301
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-04-13 13:24:52
*/
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);


    /**
     * 获取帖子封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

    /**
     * 新增题目
     * @param questionAddRequest
     * @param request
     * @return
     */
    long addQuestion(QuestionAddRequest questionAddRequest, HttpServletRequest request);

    /**
     * 更新题目信息
     * @param questionUpdateRequest
     */
    boolean updateQuestion(QuestionUpdateRequest questionUpdateRequest);

    /**
     * 编辑题目信息
     * @param questionEditRequest
     * @return
     */
    boolean editQuestion(QuestionEditRequest questionEditRequest,HttpServletRequest request);
}
