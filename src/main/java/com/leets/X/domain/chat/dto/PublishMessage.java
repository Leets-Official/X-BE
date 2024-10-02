package com.leets.X.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/*
* PublishMessage
* Redis의 pub/sub 과정 데이터를 주고 받을 때, 사용할 직렬화 클래스
* 24.09.30 - 역직렬화 에러를 해결하지 못하고 있음
* */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublishMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 2082503192322391880L;

    private Long roomId;

    private Long senderId;

    private String content;

//
//    LocalDateTime 역직렬화 에러
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createAt;
//
//
}
