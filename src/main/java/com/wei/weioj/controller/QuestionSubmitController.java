package com.wei.weioj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wei.weioj.common.BaseResponse;
import com.wei.weioj.common.ErrorCode;
import com.wei.weioj.common.ResultUtils;
import com.wei.weioj.exception.BusinessException;
import com.wei.weioj.exception.ThrowUtils;
import com.wei.weioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.wei.weioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.wei.weioj.model.entity.QuestionSubmit;
import com.wei.weioj.model.entity.User;
import com.wei.weioj.model.vo.QuestionSubmitVO;
import com.wei.weioj.service.QuestionSubmitService;
import com.wei.weioj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/7Ailun">艾伦</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交问题信息
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return 提交后的id
     */
    @PostMapping("/submit")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                               HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //
        final User loginUser = userService.getLoginUser(request);
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionSubmitVO>> listPostVOByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                       HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> questionSubmitVOPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitVOPage, request));
    }
}
