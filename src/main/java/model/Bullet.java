package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
class Bullet extends MazeObject {

    enum Type {
        Standard,
        BulletAbsorber,
        BunkerBuster
    }
}
