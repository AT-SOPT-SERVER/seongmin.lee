package org.sopt.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.facade.CommentManagementFacade;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentManagementFacade commentManagementFacade;

    @PostMapping
    public ResponseEntity<ResultResponse<Long>> postComment(@RequestHeader(name = "Authorization") Long userId, CommentCreateRequest request){
        URI location = URI.create("/comment/" + commentManagementFacade.createComment(userId, request));
        return ResponseEntity.created(location)
                .body(ResultResponse.of(ResultCode.CREATED, null));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ResultResponse<Void>> updateComment(@RequestHeader(name = "Authorization") Long userId, @PathVariable Long commentId, CommentUpdateRequest request){
        commentManagementFacade.updateComment(userId, commentId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResultResponse<Void>> deleteComment(@RequestHeader(name = "Authorization") Long userId, @PathVariable Long commentId){
        commentManagementFacade.removeComment(userId, commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }
}
