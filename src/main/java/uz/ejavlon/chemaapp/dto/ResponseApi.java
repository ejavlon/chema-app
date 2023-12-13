package uz.ejavlon.chemaapp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseApi {

    String message;

    Boolean success;

    Object data;
}
