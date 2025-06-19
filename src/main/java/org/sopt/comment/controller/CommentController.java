package org.sopt.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.facade.CommentManagementFacade;
import org.sopt.global.auth.security.CustomUserDetails;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentManagementFacade commentManagementFacade;

    @PostMapping
    public ResponseEntity<ResultResponse<Long>> postComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            CommentCreateRequest request
    ) {
        URI location = URI.create("/comments/" + commentManagementFacade.createComment(userDetails.getId(), request));
        return ResponseEntity.created(location)
                .body(ResultResponse.of(ResultCode.CREATED, null));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ResultResponse<Void>> updateComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long commentId,
            CommentUpdateRequest request
    ){
        commentManagementFacade.updateComment(userDetails.getId(), commentId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResultResponse<Void>> deleteComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long commentId
    ){
        commentManagementFacade.removeComment(userDetails.getId(), commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }
}
