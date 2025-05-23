package org.sopt.post.dto.response;

import org.sopt.global.result.PagedResponse;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;

public record PostInfoListResponse(
        PagedResponse<PostInfoResponse> data) {

    public static PostInfoListResponse from(Page<Post> posts){
        return new PostInfoListResponse(PagedResponse.from(posts.map(PostInfoResponse::from)));
    }
}
