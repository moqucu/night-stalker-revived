package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Position {

    private Integer horizontal;
    private Integer vertical;
}
