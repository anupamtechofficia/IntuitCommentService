Overview

Design a comments service for a social media website which can support scalable levels
of nesting. The service should return n first level comments. On clicking on view replies,
the next level of comments should be fetched. Make reasonable assumptions with
reasonable options for extensibility open. All the comments have associated likes and
dislikes. One clicking on the likes or dislikes the list of the users participating in the
like/dislike shall be displayed

Deliverables:
a) Design a basic service which can add comments, likes, dislikes to a social media
post, replies to a comment, replies to replies and associated likes or dislikes.
There should be a get api to satisfy the requirements.

b) The API shal be scalable for a service where there can be 1000s of comments
and each of the comment having 100s of levels.

c) API design, Database design, relationship between entities, class design,
pagination concepts

d) If a craft demo is given a day in advance , brownie points for a test suite, and a
working API.


e) Basic UI is not mandatory yet would be nice to have in view of UX such as a
hardcoded post content.


Assumptions
1. No User Authentication Is Done
2. No Hashing on User Name, Comment etc is done for security
3. No Authentication is done for User 
4. No operation such as delete or edit comment is supported for now.
5. One Unique user can either like/dislike a comment
6. Basic Validation on Request Body/Param is added.
7. MYSQL is used to store Data having three table: Comment, Reaction, User
8. Second name for a user can be optional.
9. To add comment or reaction on a comment/post user should exist in the system
10. Sharding is not being used for databases currently.
11. Using Spring boot To Make Rest Endpoint.
12. Using Spring JPA to connect With Mysql Database
13. Logging is not being used currently


Table: Comment

    @Table(name = "comment")
    public class Comment {
    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "message", nullable = false)
    private String message;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
    }


Table: Reaction


    public class Reaction {

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reactionId;

    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private ReactionType reactionType;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reaction_time")
    private Date reactionTime;
    }

Table: User

    @Table(name = "user")
    public class User {
    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
    }

DATABASE SETUP

    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    brew install mysql
    brew install mysql
    mysql_secure_installation

    mysql> create database comments; -- Creates the new database
    mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
    mysql> grant all on db_example.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database





