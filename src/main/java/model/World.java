package model;

import javafx.scene.image.Image;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class World {

    private List<BuildingBlock> buildingBlocks = new ArrayList<>();

    public World() {

        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 001 - Wall.png"))
                        .position(Position.builder().horizontal(0).vertical(0).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 002 - Wall.png"))
                        .position(Position.builder().horizontal(1).vertical(0).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 003 - Wall.png"))
                        .position(Position.builder().horizontal(2).vertical(0).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 004 - Wall.png"))
                        .position(Position.builder().horizontal(3).vertical(0).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 005 - Wall.png"))
                        .position(Position.builder().horizontal(4).vertical(0).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 006 - Wall.png"))
                        .position(Position.builder().horizontal(5).vertical(0).build())
                        .position(Position.builder().horizontal(7).vertical(0).build())
                        .position(Position.builder().horizontal(9).vertical(0).build())
                        .position(Position.builder().horizontal(11).vertical(0).build())
                        .position(Position.builder().horizontal(13).vertical(0).build())
                        .position(Position.builder().horizontal(15).vertical(0).build())
                        .position(Position.builder().horizontal(17).vertical(0).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 007 - Wall.png"))
                        .position(Position.builder().horizontal(6).vertical(0).build())
                        .position(Position.builder().horizontal(8).vertical(0).build())
                        .position(Position.builder().horizontal(10).vertical(0).build())
                        .position(Position.builder().horizontal(12).vertical(0).build())
                        .position(Position.builder().horizontal(14).vertical(0).build())
                        .position(Position.builder().horizontal(16).vertical(0).build())
                        .position(Position.builder().horizontal(18).vertical(0).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 008 - Wall.png"))
                        .position(Position.builder().horizontal(19).vertical(0).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 009 - Wall.png"))
                        .position(Position.builder().horizontal(0).vertical(1).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.SpiderWeb)
                        .image(new Image("images/Sprite 010 - Web.png"))
                        .position(Position.builder().horizontal(1).vertical(1).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.SpiderWeb)
                        .image(new Image("images/Sprite 011 - Web.png"))
                        .position(Position.builder().horizontal(2).vertical(1).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.SpiderWeb)
                        .image(new Image("images/Sprite 012 - Web.png"))
                        .position(Position.builder().horizontal(3).vertical(1).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 013 - Wall.png"))
                        .position(Position.builder().horizontal(4).vertical(1).build())
                        .position(Position.builder().horizontal(4).vertical(3).build())
                        .position(Position.builder().horizontal(2).vertical(4).build())
                        .position(Position.builder().horizontal(12).vertical(4).build())
                        .position(Position.builder().horizontal(2).vertical(6).build())
                        .position(Position.builder().horizontal(6).vertical(6).build())
                        .position(Position.builder().horizontal(4).vertical(8).build())
                        .position(Position.builder().horizontal(13).vertical(8).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 014 - Wall.png"))
                        .position(Position.builder().horizontal(4).vertical(1).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.SpiderWeb)
                        .image(new Image("images/Sprite 015 - Web.png"))
                        .position(Position.builder().horizontal(1).vertical(2).build())
                        .build()
        );
        buildingBlocks.add(
                BuildingBlock.builder()
                        .type(BuildingBlock.Type.Wall)
                        .image(new Image("images/Sprite 016 - Wall.png"))
                        .position(Position.builder().horizontal(2).vertical(2).build())
                        .build()
        );

    }
}
