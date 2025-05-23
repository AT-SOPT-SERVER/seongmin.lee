package org.sopt.post.dto.request;

import java.util.List;

public record PostCreateRequest(String title, String content, List<String> tags){

}
