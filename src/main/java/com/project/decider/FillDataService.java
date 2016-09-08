package com.project.decider;

import com.project.decider.controllers.MainController;
import com.project.decider.record.*;
import com.project.decider.user.User;
import com.project.decider.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

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

    static public String album = "images/";

    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;
    @Autowired
    QuizService quizService;
    @Autowired
    VoteService voteService;

    @PostConstruct
    public void fillDataBase(){
        User userEntity = new User();
        userEntity.setVerified(true);
        userEntity.setName(userName);
        userEntity.setPassword(userPassword);
        userEntity.setBackground("images/background.jpg");
        userEntity.setAvatar("images/ic_launcher.png");
        userService.saveUser(userEntity);

        userEntity.setName(userTVBB);
        userEntity.setPassword(userTVBBPassword);
        userEntity.setBackground(album+"breaking_bad_background.jpg");
        userEntity.setAvatar(album+"breaking_bad_avatar.jpg");
        userService.saveUser(userEntity);

        userEntity.setName(userTVGT);
        userEntity.setPassword(userTVFTPassword);
        userEntity.setBackground(album+"game_of_thrones_background.jpg");
        userEntity.setAvatar(album+"game_of_thrones_avatar.jpg");
        userService.saveUser(userEntity);

        userEntity.setName(userSF);
        userEntity.setPassword(userSFPassword);
        userEntity.setBackground(album+"sanfran_background.jpg");
        userEntity.setAvatar(album+"sanfran_avatar.jpg");
        userService.saveUser(userEntity);

        Record record;
        Quiz quiz;
        Long recordId = 1L;

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_1.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_2.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_3.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_4.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("first");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("second");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("nothing");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_5.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_6.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("first");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("second");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_7.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_8.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_9.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("first");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("second and third");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_10.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_11.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userName);
        record.setImage("images/res_image_12.png");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("first");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("second");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userName);
        quiz.setName("third");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userTVBB);
        record.setImage(album+"breaking_bad_1.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVBB);
        record.setImage(album+"breaking_bad_2.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("first");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("second");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userTVBB);
        record.setImage(album+"breaking_bad_3.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVBB);
        record.setImage(album+"breaking_bad_4.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVBB);
        record.setImage(album+"breaking_bad_5.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("1");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("2");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("3");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userTVBB);
        record.setImage(album+"breaking_bad_6.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("+");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("-");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userTVBB);
        record.setImage(album+"breaking_bad_7.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVBB);
        record.setImage(album+"breaking_bad_8.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("yes");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("no");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("maybe");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVBB);
        quiz.setName("see result");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_1.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_2.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_3.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_4.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("1");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("2");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("3");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("4");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_5.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_6.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_7.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("yes");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("no");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("+/-");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_8.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_9.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userTVGT);
        record.setImage(album+"game_of_thrones_10.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("1");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("2");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userTVGT);
        quiz.setName("1 and 3");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //----------------------------

        recordId++;
        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_1.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_2.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("1");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("2");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_3.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_4.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("1");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("2");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_5.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_6.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("1");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("2");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_7.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("+");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("-");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_8.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("+");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("-");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        //-------------------------

        recordId++;
        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_9.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        record = new Record();
        record.setUserName(userSF);
        record.setImage(album+"sanfran_10.jpg");
        record.setRecordId(recordId);
        recordService.saveRecord(record);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("1");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);

        quiz = new Quiz();
        quiz.setUserName(userSF);
        quiz.setName("2");
        quiz.setRecordId(recordId);
        quizService.saveQuiz(quiz);
    }
}
