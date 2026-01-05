package com.aston.core;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCreatedDeletedEvent {

    private String type;

    private String name;

    private String email;

}
