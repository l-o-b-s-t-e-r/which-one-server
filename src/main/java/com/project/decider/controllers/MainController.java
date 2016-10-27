package com.project.decider.controllers;

import com.project.decider.dto.FullRecordDto;
import com.project.decider.record.*;
import com.project.decider.user.User;
import com.project.decider.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lobster on 30.04.16.
 */

@Controller
@RequestMapping("/")
public class MainController {

    public static final String IMAGE_FOLDER = "/images";
    private final String SERVER_EMAIL = "vla.loboda@gmail.com";
    private final String VERIFY_EMAIL = "/verify_email";
    private final String FILE_PREFIX = "IMG_";
    private final String FILE_SUFFIX = ".jpg";
    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;
    @Autowired
    QuizService quizService;
    @Autowired
    VoteService voteService;
    @Autowired
    private HttpServletRequest request;
    private Session session;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showWelcomePage() {
        return "index";
    }

    @RequestMapping(value = "/get_user", method = RequestMethod.GET)
    @ResponseBody
    public String getUser(@RequestParam String name, @RequestParam String password) {
        return "index";
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Void> signUp(@RequestParam String name, @RequestParam String password, @RequestParam String email){
        User user = userService.getUserByName(name);
        if (user != null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            user = userService.getUserByEmail(email);
            if (user != null){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            } else {
                user = new User();
                user.setName(name);
                user.setPassword(password);
                user.setEmail(email);
                user.setVerificationCode(user.hashCode());
                userService.saveUser(user);

                sendVerifyingEmail(email, user);

                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/sign_in", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Void> signIn(@RequestParam String name, @RequestParam String password){
        User user = userService.getUser(name, password);
        if (user != null && user.isVerified()) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = VERIFY_EMAIL+"/{verificationCode}", method = RequestMethod.GET)
    @ResponseBody
    public String verifyEmail(@PathVariable(value="verificationCode") Integer verificationCode){
        User user = userService.getUserByVerificationCode(verificationCode);
        if (user != null) {
            user.setVerified(true);
            userService.saveUser(user);
            return "verify_success";
        } else {
            return "verify_error";
        }
    }

    @RequestMapping(value = "/check_name", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> checkUser(@RequestParam String name) {
        User user = userService.getUserByName(name);
        if (user == null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/check_email", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> checkEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/remind_info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> remindInfo(@RequestParam String email) {
        System.out.println("GET EMAIL");
        User user = userService.getUserByEmail(email);
        if (user != null) {
            if (sendRemindEmail(email, user)) {
                System.out.println("EMAIL SENT");
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
        }
        System.out.println("EMAIL NOT SENT");
        return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/get_user_info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserInfo(@RequestParam String name) {
        System.out.println("GET USER INFO");
        User user = userService.getUserByName(name);
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
    }

    //localhost:8080/project/get_last_records?name=user
    @RequestMapping(value = "/get_last_user_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FullRecordDto>> getLastRecordsByName(@RequestParam String name) {
        User user = userService.getUserByName(name);

        Map<Quiz, List<Vote>> quiz = new LinkedHashMap<>();
        List<FullRecordDto> lastRecordsDto = new ArrayList<>();

        Long recordId = recordService.getLastRecordByName(name);
        if (recordId==null){
            return new ResponseEntity<List<FullRecordDto>>(lastRecordsDto, HttpStatus.OK);
        }

        List<Record> recordImages = recordService.getRecordByNameRecordId(name, recordId);
        List<Quiz> quizOptions = quizService.getQuizByNameRecordId(name, recordId);
        for (Quiz q: quizOptions){
            quiz.put(q, voteService.getAllRecordsByRecordIdOption(q.getRecordId(), q.getName()));
        }

        String description = recordImages.get(0).getDescription();
        lastRecordsDto.add(new FullRecordDto(user.getName(), description, user.getAvatar(), recordId, recordImages, quiz));
        for (int i=1; i<9; i++){
            quiz.clear();
            System.out.println(recordId);
            recordId = recordService.getNextRecordId(name, recordId);
            if (recordId==null){
                return new ResponseEntity<List<FullRecordDto>>(lastRecordsDto, HttpStatus.OK);
            }

            recordImages = recordService.getRecordByNameRecordId(name, recordId);
            quizOptions = quizService.getQuizByNameRecordId(name, recordId);

            for (Quiz q: quizOptions){
                quiz.put(q, voteService.getAllRecordsByRecordIdOption(q.getRecordId(), q.getName()));
            }

            description = recordImages.get(0).getDescription();
            lastRecordsDto.add(new FullRecordDto(user.getName(), description, user.getAvatar(), recordId, recordImages, quiz));
        }

        return new ResponseEntity<List<FullRecordDto>>(lastRecordsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_next_user_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FullRecordDto>> getNextRecordsByName(@RequestParam String name, @RequestParam Long recordId) {
        User user = userService.getUserByName(name);

        String description;
        List<Record> recordImages;
        List<Quiz> quizOptions;
        Map<Quiz, List<Vote>> quiz = new LinkedHashMap<>();
        List<FullRecordDto> lastRecordsDto = new ArrayList<>();

        for (int i=1; i<10; i++){
            quiz.clear();
            recordId = recordService.getNextRecordId(name, recordId);
            if (recordId==null){
                return new ResponseEntity<List<FullRecordDto>>(lastRecordsDto, HttpStatus.OK);
            }

            recordImages = recordService.getRecordByNameRecordId(name, recordId);
            quizOptions = quizService.getQuizByNameRecordId(name, recordId);

            for (Quiz q: quizOptions){
                quiz.put(q, voteService.getAllRecordsByRecordIdOption(q.getRecordId(), q.getName()));
            }

            description = recordImages.get(0).getDescription();
            lastRecordsDto.add(new FullRecordDto(user.getName(), description, user.getAvatar(), recordId, recordImages, quiz));
        }

        return new ResponseEntity<List<FullRecordDto>>(lastRecordsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_last_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FullRecordDto>> getLastRecords() {
        Long lastRecordId = recordService.getLastRecordId();
        List<Record> lastRecords = recordService.getRecordsRange(lastRecordId, lastRecordId - 5);
        List<Quiz> lastQuizzes = quizService.getQuizzesRange(lastRecordId, lastRecordId - 5);

        List<FullRecordDto> lastRecordsDto = buildFullRecordDto(lastRecords, lastQuizzes, lastRecordId, null);

        return new ResponseEntity<List<FullRecordDto>>(lastRecordsDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/get_next_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FullRecordDto>> getNextRecords(@RequestParam Long recordId) {
        Long lastRecordId = recordId-1;
        List<Record> lastRecords = recordService.getRecordsRange(lastRecordId, lastRecordId - 5);
        List<Quiz> lastQuizzes = quizService.getQuizzesRange(lastRecordId, lastRecordId - 5);

        if (!lastRecords.isEmpty()) {
            List<FullRecordDto> lastRecordsDto = buildFullRecordDto(lastRecords, lastQuizzes, lastRecordId, null);
            return new ResponseEntity<List<FullRecordDto>>(lastRecordsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<FullRecordDto>>(new ArrayList<FullRecordDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get_record_images", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Record>> getRecordImages(@RequestParam Long recordId) {
        return new ResponseEntity<List<Record>>(recordService.getRecordById(recordId), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_record", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FullRecordDto> getRecord(@RequestParam Long recordId) {
        List<Record> recordList = recordService.getRecordById(recordId);

        Map<Quiz, List<Vote>> fullQuiz = new LinkedHashMap<>();
        List<Quiz> quizList = quizService.getQuizByRecordId(recordId);

        for (Quiz q: quizList){
            fullQuiz.put(q, voteService.getAllRecordsByRecordIdOption(q.getRecordId(), q.getName()));
        }

        String userName = recordList.get(0).getUserName();
        String description = recordList.get(0).getDescription();
        User user = userService.getUserByName(userName);
        FullRecordDto record = new FullRecordDto(userName, description, user.getAvatar(), recordId, recordList, fullQuiz);

        return new ResponseEntity<FullRecordDto>(record, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_all_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Record>> getAllRecords(@RequestParam String name) {
        return new ResponseEntity<List<Record>>(recordService.getAllRecordsByUserName(name), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_all_quizzes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Quiz>> getAllQuizzes(@RequestParam String name) {
        return new ResponseEntity<List<Quiz>>(quizService.getAllQuizzesByUserName(name), HttpStatus.OK);
    }

    @RequestMapping(value = "/add_record", method = RequestMethod.POST)
    @ResponseBody
    public void addRecord(@RequestParam("files") List<MultipartFile> files,
                          @RequestParam("options") List<String> options,
                          @RequestParam("name") String name,
                          @RequestParam("description") String description) {
        System.out.println("SUCCESS");

        Long newRecordId = recordService.getLastRecordId()+1L;

        for (MultipartFile file: files){
            if (!file.isEmpty()) {
                try {
                    String uploadsDir = "/images";
                    String dirPath = request.getSession().getServletContext().getRealPath(uploadsDir);
                    File dir = new File(dirPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
                    File newFile = File.createTempFile(FILE_PREFIX+timeStamp+"_", FILE_SUFFIX, dir);
                    file.transferTo(newFile);

                    Record newRecord = new Record();
                    newRecord.setUserName(name);
                    newRecord.setDescription(description);
                    newRecord.setImage(newFile.getName());
                    newRecord.setRecordId(newRecordId);
                    recordService.saveRecord(newRecord);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (String s: options){
            Quiz newQuiz = new Quiz();
            newQuiz.setUserName(name);
            newQuiz.setName(s);
            newQuiz.setRecordId(newRecordId);
            quizService.saveQuiz(newQuiz);
        }

    }

    @RequestMapping(value = "/update_background", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> getBackground(@RequestParam("background") MultipartFile file, @RequestParam("name") String name) {
        System.out.println("NAME: "+name);
        System.out.println("LENGTH: "+name.length());
        User user = null;
        if (!file.isEmpty()) {
            try {
                String uploadsDir = "/images";
                String dirPath = request.getSession().getServletContext().getRealPath(uploadsDir);
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
                File newFile = File.createTempFile(FILE_PREFIX+timeStamp+"_", FILE_SUFFIX, dir);
                file.transferTo(newFile);
                user = userService.setBackground(name, newFile.getName());
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/update_avatar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> getAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("name") String name) {
        User user = null;
        if (!file.isEmpty()) {
            try {
                String uploadsDir = "/images";
                String dirPath = request.getSession().getServletContext().getRealPath(uploadsDir);
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
                File newFile = File.createTempFile(FILE_PREFIX+timeStamp+"_", FILE_SUFFIX, dir);
                file.transferTo(newFile);
                user = userService.setAvatar(name, newFile.getName());
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/get_users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(@RequestParam String searchQuery) {
        return new ResponseEntity<List<User>>(userService.getUsersByTemplate(searchQuery, 10), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_users_from_id", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUserFromId(@RequestParam String searchQuery, @RequestParam Long userId) {
        System.out.println("LOAD MORE USERS");
        return new ResponseEntity<List<User>>(userService.getUsersByTemplateFromId(searchQuery, userId, 10), HttpStatus.OK);
    }

    @RequestMapping(value = "/save_vote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> saveVote(@RequestParam String userName, @RequestParam Long recordId, @RequestParam String option) {

        Vote vote = new Vote();
        vote.setUserName(userName);
        vote.setRecordId(recordId);
        vote.setPointName(option);
        voteService.createRecord(vote);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    private List<FullRecordDto> buildFullRecordDto(List<Record> lastRecords, List<Quiz> lastQuizzes, Long recordId, User user){
        if (lastRecords.isEmpty() || lastQuizzes.isEmpty()){
            return null;
        }
        boolean buildForUser = (user == null) ? false : true;

        List<FullRecordDto> lastRecordsDto = new ArrayList<>();
        List<Vote> votes;
        String description;

        Long currentRecordId = recordId;
        boolean finishFlag = false;
        while (!finishFlag) {
            List<Record> recordsWithSameId = new ArrayList<>();
            Map<Quiz, List<Vote>> quizzesWithSameId = new LinkedHashMap<>();

            Iterator<Record> recordIterator = lastRecords.iterator();
            Record r = null;
            while (recordIterator.hasNext()) {
                r = recordIterator.next();
                if (r.getRecordId() == currentRecordId) {
                    recordsWithSameId.add(r);
                    recordIterator.remove();
                }
            }
            if (!buildForUser) {
                user = userService.getUserByName(r.getUserName());
            }

            Iterator<Quiz> quitIterator = lastQuizzes.iterator();
            while (quitIterator.hasNext()) {
                Quiz q = quitIterator.next();
                if (q.getRecordId() == currentRecordId) {
                    votes = voteService.getAllRecordsByRecordIdOption(q.getRecordId(), q.getName());
                    quizzesWithSameId.put(q, votes);
                    quitIterator.remove();
                }
            }

            description = recordsWithSameId.get(0).getDescription();
            lastRecordsDto.add(new FullRecordDto(user.getName(), description, user.getAvatar(), currentRecordId, recordsWithSameId, quizzesWithSameId));
            currentRecordId--;

            if (lastRecords.isEmpty() && lastQuizzes.isEmpty()) {
                finishFlag = true;
            }
        }

        return lastRecordsDto;
    }

    @PostConstruct
    private void initEmail(){
        Properties properties = new Properties();
        properties.put("mail.smtp.user", SERVER_EMAIL);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SERVER_EMAIL, "09v67l85a29d01");
            }
        });
    }

    private boolean sendRemindEmail(String toEmail, User user){
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SERVER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("WhichOne App");
            message.setText("Name: " + user.getName() + '\n' + "Password: " + user.getPassword());

            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, "username", "password");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

        return true;
    }

    private String buildUri(){
        try {
            URI uri = new URI(request.getScheme(), null, request.getLocalName(), request.getLocalPort(), request.getContextPath(), null, null);
            return uri.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean sendVerifyingEmail(String toEmail, User user){
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SERVER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("WhichOne App");
            message.setText("Please click the link to confirm your email address: " + buildUri() + VERIFY_EMAIL + "/"+user.getVerificationCode());

            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, "username", "password");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

        return true;
    }
}