package com.ecommerce.restcontroller;

import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.common.response.ResponseBuilder;
import com.ecommerce.common.security.AuthenticationValidator;
import com.ecommerce.servercommon.dto.ReviewDto;
import com.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(value = "/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final AuthenticationValidator authenticationValidator;
    private final ReviewService reviewService;
    private final ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<HttpResponseDto> review(
            Authentication authentication,
            @RequestBody ReviewDto reviewDto
            ) throws AuthenticationException {

        this.reviewService.sendReviewMessage(
                reviewDto,
                this.authenticationValidator.validateAndGetName(authentication)
        );

        return this.responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "리뷰 요청 성공",
                null
        );
    }

}
