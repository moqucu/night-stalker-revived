# night-stalker-revived

The great Intellivision game revived as a Java game

## 2018-03-16: Night Stalker Kick Off

### **Objectives**

"Let's start working on our road map to get this project lifted off the floor"

### **Outcomes**

-   Project Goals:

    -   Resurrect Intellivision’s Night Stalker game in Java

    -   Teach GISSV 5th graders in approx. fifteen 45 minutes lessons how to
        develop this game themselves

-   Resources:

    -   https://youtu.be/cTVCjbO46CU

    -   https://www.safaribooksonline.com/library/view/beginning-java-8/9781484204153/

    -   https://www.giantbomb.com/night-stalker/3030-4094/

### **Action Items**

-   Everyone to read chapters 1,2, and 3 of "Beginning Java 8 Games Development”

-   Everyone to install the recommended baseline apps (replace NetBeans with
    IntelliJ) from chapter 1

-   Everyone to brainstorm the properties and behaviors (aka required functions)
    for the following objects:

    -   World, NightStalker -\> Eileen

    -   Weapon, Robot -\> Neal

    -   Spider, SpiderWeb, Bunker -\> Wen-Yu

    -   Bullet, Bat -\> Sean

## 2018-03-30: Night Stalker: Objects

### **Objectives**

-   Q&A regarding chapters 1, 2, and 3

-   Review object proposals

### **Outcomes**

-   **World**

-   **Maze**: 2D Array [12][24]

    -   0 or 1 (or an object) to denote a maze block

-   **NightStalker**

    -   Attribute:

        -   Weapon

        -   Location

        -   Current direction (Integer)

        -   Number of lives

        -   In bunker

        -   Is paralyzed

        -   Method

        -   Draw night stalker

        -   Control night stalker

        -   was_hit

        -   Revive or die

        -   Getter/Setter for attributes

        -   Exercise weapon

-   **Robot**

    -   Attribute

        -   Number of shields

        -   Level

    -   Method

        -   Was hit

        -   Fire

        -   Move

        -   Change direction

        -   Find target

-   **Weapon**

    -   Attribute

        -   bullets

        -   Location: x,y based on the matrix

        -   State of the weapon (not visible, not picked up, picked up….)

    -   Method

        -   Draw weapon

        -   Fire

            -   Remove items from the items array

            -   Make bullet move

        -   Getter/setter for attributes

-   **Bullet**

    -   Attribute

        -   Type

        -   Can absorb bullet

        -   Can destroy bunker

    -   Method

    -   ...

-   **Spider**

    -   Attribute

        -   Location

        -   Number of lives

    -   Method

        -   Next move

        -   Paralyze target

-   **Spider Web**

    -   Non-blocking background

-   **Bunker**

    -   Attribute

        -   Bunker cell [ ][ ]

    -   Method

        -   ...

-   **Bat**

    -   Attribute

        -   Location

        -   Number of lives

    -   Method

        -   Next move

        -   Paralyze target

        -   Respawn

### **Action Items**

-   Sean to transform object notes into GitHub Java classes

-   Everyone to read chapter 4 of Java Games book

## 2017-04-27: Night Stalker - Game Engine

### **Objectives**

-   Sean to present consolidated GitHub repository

-   Wen-Yu to facilitate discussion on game engine and related requirements

### **Outcomes**

-   https://github.com/moqucu/night-stalker-revived/commit/9ceee7f49809b2d348009ac7ab587b0c57b168ee

-   High-level discussion on game engine alternatives

    -   Game engine controls everything

    -   Objects control everything, bound together by game engine

### **Action Items**

-   Sean: <blockquote class="trello-card"><a href="https://trello.com/c/hDLNYI6Z/1-make-the-code-runable-again">Make the code runable again</a></blockquote><script src="https://p.trellocdn.com/embed.min.js"></script>

-   Neal: <blockquote class="trello-card"><a href="https://trello.com/c/5tGrwmvc/2-set-up-task-manager">Set up task manager</a></blockquote><script src="https://p.trellocdn.com/embed.min.js"></script>

-   Sean: <blockquote class="trello-card"><a href="https://trello.com/c/SvdBLVQU/3-summarize-team-meetings-in-readmemd">Summarize team meetings in Readme.md</a></blockquote><script src="https://p.trellocdn.com/embed.min.js"></script>

-   Eileen: <blockquote class="trello-card"><a href="https://trello.com/c/C4Tsc5gn/4-split-respawnableobject-class">Split RespawnableObject class</a></blockquote><script src="https://p.trellocdn.com/embed.min.js"></script>

-   Wen-Yu: <blockquote class="trello-card"><a href="https://trello.com/c/OHS8iDhz/5-propose-centralized-engine-governance">Propose centralized engine governance</a></blockquote><script src="https://p.trellocdn.com/embed.min.js"></script>

-   Neal: <blockquote class="trello-card"><a href="https://trello.com/c/qoXCyDrg/6-propose-decentralized-object-governance">Propose decentralized object governance</a></blockquote><script src="https://p.trellocdn.com/embed.min.js"></script>
