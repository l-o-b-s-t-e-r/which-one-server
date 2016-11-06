package com.project.decider;

import com.project.decider.model.*;
import com.project.decider.service.RecordService;
import com.project.decider.service.UserService;
import com.project.decider.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by macos on 02.05.16.
 */

@Service
@Transactional
public class FillDataService {

    static public String userSF = "sanfran";
    static public String userSFPassword = "sanfran";

    static public String userTVBB = "tvShowBB";
    static public String userTVBBPassword = "tvShowBB";

    static public String userTVGT = "tvShowGT";
    static public String userTVFTPassword = "tvShowGT";

    static public String userName = "person";
    static public String userPassword = "person";

    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;
    @Autowired
    VoteService voteService;

    private void createUsers() {
        User userEntity = new User();
        userEntity.setVerified(true);
        userEntity.setUsername(userName);
        userEntity.setPassword(userPassword);
        userEntity.setEmail("vla-lobster@yandex.ru");
        userEntity.setBackground("background.jpg");
        userEntity.setAvatar("ic_launcher.png");
        userService.saveUser(userEntity);
        userEntity.setEmail("");

        userEntity.setUsername(userTVBB);
        userEntity.setPassword(userTVBBPassword);
        userEntity.setBackground("breaking_bad_background.jpg");
        userEntity.setAvatar("breaking_bad_avatar.jpg");
        userService.saveUser(userEntity);

        userEntity.setUsername(userTVGT);
        userEntity.setPassword(userTVFTPassword);
        userEntity.setBackground("game_of_thrones_background.jpg");
        userEntity.setAvatar("game_of_thrones_avatar.jpg");
        userService.saveUser(userEntity);

        userEntity.setUsername(userSF);
        userEntity.setPassword(userSFPassword);
        userEntity.setBackground("sanfran_background.jpg");
        userEntity.setAvatar("sanfran_avatar.jpg");
        userService.saveUser(userEntity);
    }

    @PostConstruct
    public void fillDataBase() {
        createUsers();

        Record record;
        List<Image> images;
        List<Option> options;

        record = new Record();
        record.setUsername(userName);
        record.setDescription("Description of record");
        record.setAvatar("ic_launcher.png");

        recordService.saveRecord(record);

        images = Arrays.asList(
                new Image("res_image_1.png", record),
                new Image("res_image_2.png", record),
                new Image("res_image_3.png", record),
                new Image("res_image_4.png", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record)),
                new Option(new OptionId("nothing", record))
        );

        record.setOptions(options);
        record.setImages(images);

        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userTVBB);
        record.setDescription("Description of record");
        record.setAvatar("breaking_bad_avatar.jpg");

        images = Arrays.asList(
                new Image("breaking_bad_1.jpg", record),
                new Image("breaking_bad_2.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userTVGT);
        record.setDescription("Description of record");
        record.setAvatar("game_of_thrones_avatar.jpg");

        images = Arrays.asList(
                new Image("game_of_thrones_1.jpg", record),
                new Image("game_of_thrones_2.jpg", record),
                new Image("game_of_thrones_3.jpg", record),
                new Image("game_of_thrones_4.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record)),
                new Option(new OptionId("third", record)),
                new Option(new OptionId("all", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userSF);
        record.setDescription("Description of record");
        record.setAvatar("sanfran_avatar.jpg");

        images = Arrays.asList(
                new Image("sanfran_1.jpg", record),
                new Image("sanfran_2.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userName);
        record.setDescription("Description of record");
        record.setAvatar("ic_launcher.png");

        images = Arrays.asList(
                new Image("res_image_5.png", record),
                new Image("res_image_6.png", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userTVBB);
        record.setDescription("Description of record");
        record.setAvatar("breaking_bad_avatar.jpg");

        images = Arrays.asList(
                new Image("breaking_bad_3.jpg", record),
                new Image("breaking_bad_4.jpg", record),
                new Image("breaking_bad_5.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record)),
                new Option(new OptionId("third", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userTVGT);
        record.setDescription("Description of record");
        record.setAvatar("game_of_thrones_avatar.jpg");

        images = Arrays.asList(
                new Image("game_of_thrones_5.jpg", record),
                new Image("game_of_thrones_6.jpg", record),
                new Image("game_of_thrones_7.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record)),
                new Option(new OptionId("third", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userSF);
        record.setDescription("Description of record");
        record.setAvatar("sanfran_avatar.jpg");

        images = Arrays.asList(
                new Image("sanfran_3.jpg", record),
                new Image("sanfran_4.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userName);
        record.setDescription("Description of record");
        record.setAvatar("ic_launcher.png");

        images = Arrays.asList(
                new Image("res_image_7.png", record),
                new Image("res_image_8.png", record),
                new Image("res_image_9.png", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second or third", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userTVBB);
        record.setDescription("Description of record");
        record.setAvatar("breaking_bad_avatar.jpg");

        images = Arrays.asList(
                new Image("breaking_bad_6.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userSF);
        record.setDescription("Description of record");
        record.setAvatar("sanfran_avatar.jpg");

        images = Arrays.asList(
                new Image("sanfran_5.jpg", record),
                new Image("sanfran_6.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userTVGT);
        record.setDescription("Description of record");
        record.setAvatar("game_of_thrones_avatar.jpg");

        images = Arrays.asList(
                new Image("game_of_thrones_8.jpg", record),
                new Image("game_of_thrones_9.jpg", record),
                new Image("game_of_thrones_10.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record)),
                new Option(new OptionId("see result", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userName);
        record.setDescription("Description of record");
        record.setAvatar("ic_launcher.png");

        images = Arrays.asList(
                new Image("res_image_10.png", record),
                new Image("res_image_11.png", record),
                new Image("res_image_12.png", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record)),
                new Option(new OptionId("third", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userTVBB);
        record.setDescription("Description of record");
        record.setAvatar("breaking_bad_avatar.jpg");

        images = Arrays.asList(
                new Image("breaking_bad_7.jpg", record),
                new Image("breaking_bad_8.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record)),
                new Option(new OptionId("third", record)),
                new Option(new OptionId("all", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userSF);
        record.setDescription("Description of record");
        record.setAvatar("sanfran_avatar.jpg");

        images = Arrays.asList(
                new Image("sanfran_7.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("yes", record)),
                new Option(new OptionId("no", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userSF);
        record.setDescription("Description of record");
        record.setAvatar("sanfran_avatar.jpg");

        images = Arrays.asList(
                new Image("sanfran_8.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("plus", record)),
                new Option(new OptionId("minus", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userName);
        record.setDescription("Description of record");
        record.setAvatar("ic_launcher.png");

        images = Arrays.asList(
                new Image("res_image_13.png", record),
                new Image("res_image_14.png", record),
                new Image("res_image_15.png", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record)),
                new Option(new OptionId("third", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }

        //-------------------------------//

        record = new Record();
        record.setUsername(userSF);
        record.setDescription("Description of record");
        record.setAvatar("sanfran_avatar.jpg");

        images = Arrays.asList(
                new Image("sanfran_9.jpg", record),
                new Image("sanfran_10.jpg", record)
        );

        options = Arrays.asList(
                new Option(new OptionId("first", record)),
                new Option(new OptionId("second", record))
        );

        record.setImages(images);
        record.setOptions(options);
        recordService.saveRecord(record);
        for (Option option : options) {
            voteService.saveVoteCount(new VoteCount(option));
        }
    }
}
