package com.wei.weioj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wei.weioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.wei.weioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.wei.weioj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.weioj.model.entity.User;
import com.wei.weioj.model.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 99301
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-04-13 13:25:43
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit post, User loginUser);

    /**
     * 分页获取帖子封装
     *
     * @param postPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> postPage, HttpServletRequest request);
}
